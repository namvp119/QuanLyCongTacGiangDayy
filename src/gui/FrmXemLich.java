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

public class FrmXemLich extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboGiangVien;
    private JComboBox<String> cboHocKy;
    private JTable tblLich;
    private DefaultTableModel tableModel;
    private JButton btnXemLich;

    public FrmXemLich() {
        setTitle("Tra Cứu Thời Khóa Biểu Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng form này
        setBounds(100, 100, 850, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTieuDe = new JLabel("LỊCH GIẢNG DẠY CÁN BỘ");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(Color.BLUE);
        lblTieuDe.setBounds(290, 10, 250, 30);
        contentPane.add(lblTieuDe);

        // --- KHU VỰC LỌC DỮ LIỆU ---
        JLabel lblGiangVien = new JLabel("Mã Giảng Viên:");
        lblGiangVien.setBounds(50, 60, 100, 25);
        contentPane.add(lblGiangVien);

        cboGiangVien = new JComboBox<>();
        cboGiangVien.setBounds(150, 60, 150, 25);
        contentPane.add(cboGiangVien);

        JLabel lblHocKy = new JLabel("Học Kỳ:");
        lblHocKy.setBounds(330, 60, 60, 25);
        contentPane.add(lblHocKy);

        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(390, 60, 120, 25);
        contentPane.add(cboHocKy);

        btnXemLich = new JButton("Tra Cứu");
        btnXemLich.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXemLich.setBounds(540, 55, 120, 35);
        contentPane.add(btnXemLich);

     // --- KHU VỰC BẢNG HIỂN THỊ ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 780, 320);
        contentPane.add(scrollPane);

        // ---> Đổi lại tiêu đề cột cho giống Thời Khóa Biểu thật
        String[] cols = {"Mã LHP", "Tên Môn", "Phòng", "Thứ", "Tiết BĐ", "Tiết KT", "Sĩ Số"};
        tableModel = new DefaultTableModel(cols, 0);
        tblLich = new JTable(tableModel);
        scrollPane.setViewportView(tblLich);

        // Tự động load mã vào ComboBox khi mở form
        loadComboBox();

        // BẮT SỰ KIỆN NÚT TRA CỨU
        btnXemLich.addActionListener(e -> traCuuLich());
    }

    // Hàm hút mã Giảng viên và Học kỳ từ CSDL
    private void loadComboBox() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            
            ResultSet rs1 = st.executeQuery("SELECT MSCB FROM CANBOGIANGDAY");
            while(rs1.next()) cboGiangVien.addItem(rs1.getString(1));
            
            ResultSet rs2 = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs2.next()) cboHocKy.addItem(rs2.getString(1));
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // Hàm xử lý logic khi bấm Tra Cứu (Đã nâng cấp JOIN 3 bảng)
    private void traCuuLich() {
        tableModel.setRowCount(0); // Làm sạch bảng trước khi in dữ liệu mới
        
        if(cboGiangVien.getSelectedItem() == null || cboHocKy.getSelectedItem() == null) return;
        
        String mscb = cboGiangVien.getSelectedItem().toString();
        String hk = cboHocKy.getSelectedItem().toString();

        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            
            // Lệnh SQL NÂNG CẤP: JOIN 3 bảng LOPHOCPHAN, MONHC và THOIKHOABIEU
            // Dùng LEFT JOIN để lỡ lớp nào chưa được xếp lịch thì vẫn hiện ra nhưng để trống Thứ, Tiết
            String sql = "SELECT l.MALHP, m.TENMH, l.MAPHONG, tkb.THU, tkb.TIETBATDAU, tkb.TIETKETTHUC, l.SISO " +
                         "FROM LOPHOCPHAN l " +
                         "JOIN MONHC m ON l.MAMH = m.MAMH " +
                         "LEFT JOIN THOIKHOABIEU tkb ON l.MALHP = tkb.MALHP " +
                         "WHERE l.MSCB = ? AND l.MAHOCKY = ?";
                         
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mscb);
            ps.setString(2, hk);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                // Xử lý một chút để lỡ giá trị null (chưa xếp lịch) thì hiện chữ trống cho đẹp
                String thu = rs.getString("THU") != null ? rs.getString("THU") : "Chưa xếp";
                String tietBD = rs.getString("TIETBATDAU") != null ? rs.getString("TIETBATDAU") : "-";
                String tietKT = rs.getString("TIETKETTHUC") != null ? rs.getString("TIETKETTHUC") : "-";

                tableModel.addRow(new Object[]{
                    rs.getString("MALHP"),
                    rs.getString("TENMH"),
                    rs.getString("MAPHONG"),
                    thu,
                    tietBD,
                    tietKT,
                    rs.getInt("SISO")
                });
            }
            conn.close();
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giảng viên này không có lịch dạy trong học kỳ đã chọn!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy xuất dữ liệu Thời khóa biểu!");
        }
    }

    // Hàm Main để bạn chạy thử form này độc lập
    public static void main(String[] args) {
        new FrmXemLich().setVisible(true);
    }
}