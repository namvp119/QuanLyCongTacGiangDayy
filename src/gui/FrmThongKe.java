package gui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrmThongKe extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboKhoa;
    private JTable tblThongKe;
    private DefaultTableModel tableModel;
    private JButton btnThongKe;

    public FrmThongKe() {
        setTitle("Thống Kê Chi Tiết & Cố Vấn - Project HaiSoPhuc");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 950, 500); // Tăng chiều rộng để hiện thêm cột
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTieuDe = new JLabel("BÁO CÁO CÔNG TÁC GIẢNG DẠY & CỐ VẤN");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(new Color(0, 102, 204)); 
        lblTieuDe.setBounds(300, 15, 450, 30);
        contentPane.add(lblTieuDe);

        // BỘ LỌC
        JLabel lblHocKy = new JLabel("Học Kỳ:");
        lblHocKy.setBounds(30, 70, 60, 25);
        contentPane.add(lblHocKy);
        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(90, 70, 100, 25);
        contentPane.add(cboHocKy);

        JLabel lblKhoa = new JLabel("Khoa:");
        lblKhoa.setBounds(210, 70, 50, 25);
        contentPane.add(lblKhoa);
        cboKhoa = new JComboBox<>();
        cboKhoa.addItem("--- Tất cả ---");
        cboKhoa.setBounds(260, 70, 180, 25);
        contentPane.add(cboKhoa);

        btnThongKe = new JButton("Lấy Dữ Liệu");
        btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThongKe.setBounds(460, 65, 130, 35);
        contentPane.add(btnThongKe);

        // BẢNG DỮ LIỆU
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 120, 880, 320);
        contentPane.add(scrollPane);

        // THÊM CỘT "LỚP CỐ VẤN"
        String[] cols = {"MSCB", "Họ Tên", "Số Lớp Dạy", "Tổng Tiết", "Tổng SV", "Lớp Cố Vấn"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblThongKe = new JTable(tableModel);
        scrollPane.setViewportView(tblThongKe);

        loadHocKy();
        loadDanhSachKhoa();
        btnThongKe.addActionListener(e -> xuLyThongKe());
    }

    private void loadHocKy() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs.next()) cboHocKy.addItem(rs.getString(1));
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadDanhSachKhoa() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAKHOA FROM KHOA");
            while(rs.next()) cboKhoa.addItem(rs.getString(1));
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void xuLyThongKe() {
        tableModel.setRowCount(0);
        if(cboHocKy.getSelectedItem() == null) return;
        
        String hk = cboHocKy.getSelectedItem().toString();
        String khoa = cboKhoa.getSelectedItem().toString();

        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            
            /* SQL NÂNG CAO: 
               Dùng GROUP_CONCAT (MySQL) hoặc STRING_AGG (SQL Server) 
               để gom danh sách các lớp cố vấn vào 1 dòng.
            */
            String sql = "SELECT c.MSCB, c.HOTEN, " +
                         "COUNT(lhp.MALHP) AS TONG_LOP, " +
                         "SUM(m.SOTC * 15) AS TONG_TIET, " +
                         "SUM(lhp.SISO) AS TONG_SV, " +
                         "(SELECT GROUP_CONCAT(MALOP SEPARATOR ', ') FROM LOPCHUYENNGANH WHERE MSCB = c.MSCB) AS LOP_CO_VAN " +
                         "FROM CANBOGIANGDAY c " +
                         "LEFT JOIN LOPHOCPHAN lhp ON c.MSCB = lhp.MSCB AND lhp.MAHOCKY = ? " +
                         "LEFT JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "WHERE 1=1 ";
            
            if (!khoa.equals("--- Tất cả ---")) {
                sql += " AND c.MAKHOA = ? ";
            }
            
            sql += " GROUP BY c.MSCB, c.HOTEN ORDER BY TONG_TIET DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk);
            if (!khoa.equals("--- Tất cả ---")) {
                ps.setString(2, khoa);
            }

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String lopCV = rs.getString("LOP_CO_VAN");
                tableModel.addRow(new Object[]{
                    rs.getString("MSCB"),
                    rs.getString("HOTEN"),
                    rs.getInt("TONG_LOP"),
                    rs.getInt("TONG_TIET"),
                    rs.getInt("TONG_SV"),
                    (lopCV != null ? lopCV : "Không có")
                });
            }	
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy xuất dữ liệu cố vấn!");
        }
    }

    public static void main(String[] args) {
        new FrmThongKe().setVisible(true);
    }
}