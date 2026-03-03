package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ThongKeDAO;

public class FrmThongKe extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboKhoa;
    
    // Bảng Tab 1 (Giảng viên)
    private JTable tblGiangVien;
    private DefaultTableModel modelGV;
    
    // Bảng Tab 2 (Khoa)
    private JTable tblKhoa;
    private DefaultTableModel modelKhoa;

    private ThongKeDAO tkDAO = new ThongKeDAO();

    public FrmThongKe() {
        setTitle("Trung Tâm Phân Tích Dữ Liệu - Quản Lý Công Tác Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 950, 550); 
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTieuDe = new JLabel("BÁO CÁO PHÂN TÍCH CÔNG TÁC GIẢNG DẠY");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(new Color(0, 102, 204));
        lblTieuDe.setBounds(280, 10, 450, 30);
        contentPane.add(lblTieuDe);
        
        // BỘ LỌC CHUNG
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBounds(20, 50, 890, 60);
        pnlFilter.setLayout(null);
        contentPane.add(pnlFilter);

        JLabel lblHocKy = new JLabel("Học Kỳ:");
        lblHocKy.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHocKy.setBounds(20, 15, 60, 25);
        pnlFilter.add(lblHocKy);
        
        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(80, 15, 120, 25);
        pnlFilter.add(cboHocKy);
        
        JLabel lblKhoa = new JLabel("Khoa Quản Lý:");
        lblKhoa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblKhoa.setBounds(230, 15, 100, 25);
        pnlFilter.add(lblKhoa);
        
        cboKhoa = new JComboBox<>();
        cboKhoa.addItem("--- Tất cả ---");
        cboKhoa.setBounds(330, 15, 200, 25);
        pnlFilter.add(cboKhoa);
        
        JButton btnThongKe = new JButton("Trích Xuất Báo Cáo");
        btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThongKe.setBackground(new Color(46, 139, 87));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setBounds(560, 10, 180, 35);
        pnlFilter.add(btnThongKe);

        // --- KHU VỰC TABBED PANE ---
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(20, 120, 890, 370);
        contentPane.add(tabbedPane);

        // TAB 1: THỐNG KÊ GIẢNG VIÊN
        JPanel pnlGV = new JPanel();
        pnlGV.setLayout(new BorderLayout());
        tabbedPane.addTab("Báo Cáo Giảng Viên (Vượt Giờ)", null, pnlGV, null);

        String[] colsGV = {"Mã Cán Bộ", "Họ Tên", "Trực thuộc Khoa", "Số Lớp", "Tổng Tiết", "Tiết Chuẩn", "Vượt Giờ"};
        modelGV = new DefaultTableModel(colsGV, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblGiangVien = new JTable(modelGV);
        pnlGV.add(new JScrollPane(tblGiangVien), BorderLayout.CENTER);

        // TAB 2: THỐNG KÊ KHOA
        JPanel pnlKhoa = new JPanel();
        pnlKhoa.setLayout(new BorderLayout());
        tabbedPane.addTab("Thống Kê Tổng Lực Theo Khoa", null, pnlKhoa, null);

        String[] colsKhoa = {"Mã Khoa", "Tên Khoa", "Tổng Số Giảng Viên", "Tổng Lớp Đảm Nhận", "Tổng Tiết Dạy"};
        modelKhoa = new DefaultTableModel(colsKhoa, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblKhoa = new JTable(modelKhoa);
        pnlKhoa.add(new JScrollPane(tblKhoa), BorderLayout.CENTER);

        // --- NẠP DỮ LIỆU BAN ĐẦU ---
        loadHocKy();
        loadDanhSachKhoa();

        // --- BẮT SỰ KIỆN NÚT BẤM ---
        btnThongKe.addActionListener(e -> {
            xuLyThongKeGV();
            xuLyThongKeKhoa();
        });

        // --- SỰ KIỆN CLICK ĐÚP CHUỘT VÀO BẢNG GIẢNG VIÊN ---
        tblGiangVien.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tblGiangVien.getSelectedRow();
                    if (row >= 0) {
                        String mscb = tblGiangVien.getValueAt(row, 0).toString();
                        String tenCB = tblGiangVien.getValueAt(row, 1).toString();
                        String hk = cboHocKy.getSelectedItem().toString();
                        hienThiChiTiet(mscb, tenCB, hk);
                    }
                }
            }
        });
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
            ResultSet rs = st.executeQuery("SELECT MAKHOA FROM KHOA"); // Lưu ý: Lấy mã khoa cho chuẩn SQL
            while(rs.next()) cboKhoa.addItem(rs.getString(1));
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void xuLyThongKeGV() {
        modelGV.setRowCount(0);
        if(cboHocKy.getSelectedItem() == null) return;
        String hk = cboHocKy.getSelectedItem().toString();
        String khoa = cboKhoa.getSelectedItem().toString();

        List<Object[]> ds = tkDAO.getThongKeGiangVien(hk, khoa);
        for (Object[] row : ds) {
            modelGV.addRow(row);
        }
    }

    private void xuLyThongKeKhoa() {
        modelKhoa.setRowCount(0);
        if(cboHocKy.getSelectedItem() == null) return;
        String hk = cboHocKy.getSelectedItem().toString();

        List<Object[]> ds = tkDAO.getThongKeKhoa(hk);
        for (Object[] row : ds) {
            modelKhoa.addRow(row);
        }
    }

    // --- HÀM DRILL-DOWN: HIỂN THỊ POPUP CHI TIẾT KHI CLICK ĐÚP ---
    private void hienThiChiTiet(String mscb, String tenCB, String hk) {
        try {
            ResultSet rs = tkDAO.getChiTietGiangDay(mscb, hk);
            if (rs == null) return;

            String[] cols = {"Mã Lớp", "Tên Môn Học", "Số TC", "Phòng Học", "Sĩ Số"};
            DefaultTableModel modelChiTiet = new DefaultTableModel(cols, 0);
            
            while (rs.next()) {
                modelChiTiet.addRow(new Object[]{
                    rs.getString("MALHP"),
                    rs.getString("TENMH"),
                    rs.getInt("SOTC"),
                    rs.getString("MAPHONG"),
                    rs.getInt("SISO")
                });
            }
            
            JTable tblChiTiet = new JTable(modelChiTiet);
            JScrollPane scrollPane = new JScrollPane(tblChiTiet);
            scrollPane.setPreferredSize(new Dimension(600, 200)); 
            
            JOptionPane.showMessageDialog(this, scrollPane, "Chi tiết lớp dạy: " + tenCB + " (" + hk + ")", JOptionPane.INFORMATION_MESSAGE);
            
            rs.getStatement().getConnection().close(); // Đóng kết nối ResultSet
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy xuất chi tiết!");
        }
    }

    public static void main(String[] args) {
        new FrmThongKe().setVisible(true);
    }
}