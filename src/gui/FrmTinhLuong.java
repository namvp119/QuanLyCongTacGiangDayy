package gui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
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

public class FrmTinhLuong extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboHocKy;
    private JTable tblLuong;
    private DefaultTableModel tableModel;
    private JButton btnTinhLuong;
    private JButton btnLuuDb;
    private JButton btnXuatExcel;
    public FrmTinhLuong() {
        setTitle("Tính Bảng Lương Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 850, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTieuDe = new JLabel("TỔNG HỢP & TÍNH LƯƠNG GIẢNG VIÊN");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(Color.RED);
        lblTieuDe.setBounds(230, 10, 400, 30);
        contentPane.add(lblTieuDe);

        // --- KHU VỰC ĐIỀU KHIỂN ---
        JLabel lblHocKy = new JLabel("Chọn Học Kỳ cần tính:");
        lblHocKy.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHocKy.setBounds(50, 60, 150, 25);
        contentPane.add(lblHocKy);

        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(200, 60, 150, 25);
        contentPane.add(cboHocKy);

        btnTinhLuong = new JButton("Tự Động Tính Lương");
        btnTinhLuong.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTinhLuong.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt
        btnTinhLuong.setBounds(380, 55, 180, 35);
        contentPane.add(btnTinhLuong);
     // ... (code các nút cũ) ...

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXuatExcel.setBackground(new Color(144, 238, 144)); // Màu xanh lá cây của Excel
        btnXuatExcel.setBounds(740, 55, 110, 35); // Đặt góc phải
        btnXuatExcel.setEnabled(false); // Ban đầu ẩn đi, tính lương xong mới cho xuất
        contentPane.add(btnXuatExcel);

        // Bắt sự kiện click
        btnXuatExcel.addActionListener(e -> xuatFileExcel());
        btnLuuDb = new JButton("Lưu Chốt Lương");
        btnLuuDb.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLuuDb.setBackground(new Color(255, 182, 193)); // Màu hồng nhạt
        btnLuuDb.setBounds(580, 55, 150, 35);
        btnLuuDb.setEnabled(false); // Ẩn đi, khi nào tính xong mới cho bấm lưu
        contentPane.add(btnLuuDb);

        // --- KHU VỰC BẢNG ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 780, 320);
        contentPane.add(scrollPane);

String[] cols = {"Mã Cán Bộ", "Họ Tên", "Tổng Tín Chỉ", "Tổng Lương (VNĐ)"};
        
        // ---> SỬA LẠI ĐOẠN KHỞI TẠO TABLE MODEL NHƯ SAU:
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khóa hoàn toàn, không cho gõ chữ thẳng vào bảng
            }
        };
        
        tblLuong = new JTable(tableModel);
        scrollPane.setViewportView(tblLuong);

        loadHocKy();
     // --- BẮT SỰ KIỆN CLICK ĐÚP CHUỘT VÀO BẢNG ĐỂ XEM CHI TIẾT ---
        tblLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // Bắt chính xác thao tác nhấp đúp 2 lần
                    int row = tblLuong.getSelectedRow();
                    if (row >= 0) {
                        String mscb = tblLuong.getValueAt(row, 0).toString();
                        String tenCB = tblLuong.getValueAt(row, 1).toString();
                        String hk = cboHocKy.getSelectedItem().toString();
                        
                        // Gọi hàm hiển thị popup chi tiết
                        xemChiTietLuong(mscb, tenCB, hk); 
                    }
                }
            }
        });
        // BẮT SỰ KIỆN NÚT BẤM
        btnTinhLuong.addActionListener(e -> thucHienTinhLuong());
        btnLuuDb.addActionListener(e -> luuVaoDatabase());
    }
 // === HÀM TRA CỨU CHI TIẾT TỪNG ĐỒNG LƯƠNG ===
    private void xemChiTietLuong(String mscb, String tenCB, String hk) {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            
            // Câu lệnh SQL bóc tách chi tiết từng lớp học phần
            String sql = "SELECT lhp.MALHP, m.TENMH, m.SOTC, cd.DONGIATIET, lm.HESO, " +
                         "(m.SOTC * 15 * cd.DONGIATIET * lm.HESO) AS THANHTIEN " +
                         "FROM LOPHOCPHAN lhp " +
                         "JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "JOIN LOAIMONHOC lm ON m.MALOAIMH = lm.MALOAIMH " +
                         "JOIN CANBOGIANGDAY c ON lhp.MSCB = c.MSCB " +
                         "JOIN CHUCDANH cd ON c.MACD = cd.MACD " +
                         "WHERE lhp.MSCB = ? AND lhp.MAHOCKY = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mscb);
            ps.setString(2, hk);
            ResultSet rs = ps.executeQuery();

            // Tạo một cái bảng Ảo để nhét vào Popup
            String[] cols = {"Mã Lớp HP", "Tên Môn", "Số TC", "Đơn Giá", "Hệ Số", "Thành Tiền"};
            DefaultTableModel modelChiTiet = new DefaultTableModel(cols, 0);
            DecimalFormat formatter = new DecimalFormat("###,###,###");

            while (rs.next()) {
                modelChiTiet.addRow(new Object[]{
                    rs.getString("MALHP"),
                    rs.getString("TENMH"),
                    rs.getInt("SOTC"),
                    formatter.format(rs.getDouble("DONGIATIET")),
                    rs.getDouble("HESO"),
                    formatter.format(rs.getDouble("THANHTIEN"))
                });
            }
            conn.close();

            // Đưa bảng ảo vào một khung cuộn (JScrollPane)
            JTable tblChiTiet = new JTable(modelChiTiet);
            JScrollPane scrollPane = new JScrollPane(tblChiTiet);
            scrollPane.setPreferredSize(new java.awt.Dimension(650, 200)); // Chỉnh kích thước khung popup

            // Bắn hộp thoại Popup lên giữa màn hình
            JOptionPane.showMessageDialog(this, scrollPane, "Chi tiết lương: " + tenCB + " (" + hk + ")", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy xuất chi tiết lương!");
        }
    }
 // === HÀM XUẤT DỮ LIỆU RA FILE CSV (MỞ BẰNG EXCEL) ===
    private void xuatFileExcel() {
        try {
            // Mở hộp thoại cho người dùng chọn nơi lưu file
            javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
            jfc.setDialogTitle("Chọn nơi lưu file Bảng Lương");
            int choose = jfc.showSaveDialog(this);
            
            if (choose == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = jfc.getSelectedFile();
                // Tự động gắn đuôi .csv nếu người dùng quên gõ
                if (!file.getName().toLowerCase().endsWith(".csv")) {
                    file = new java.io.File(file.getParentFile(), file.getName() + ".csv");
                }
                
                // Dùng luồng ghi file với chuẩn UTF-8 có BOM để Excel đọc tiếng Việt mượt mà
                java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
                fos.write(239); // EF
                fos.write(187); // BB
                fos.write(191); // BF
                
                java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(fos, "UTF-8");
                java.io.BufferedWriter bw = new java.io.BufferedWriter(osw);
                
                // 1. In dòng Tiêu đề cột
                for (int i = 0; i < tblLuong.getColumnCount(); i++) {
                    bw.write(tblLuong.getColumnName(i));
                    if (i < tblLuong.getColumnCount() - 1) bw.write(","); // Dấu phẩy ngăn cách cột
                }
                bw.newLine(); // Xuống dòng
                
                // 2. In từng dòng dữ liệu trong Bảng
                for (int i = 0; i < tblLuong.getRowCount(); i++) {
                    for (int j = 0; j < tblLuong.getColumnCount(); j++) {
                        String text = tblLuong.getValueAt(i, j) != null ? tblLuong.getValueAt(i, j).toString() : "";
                        
                        // Cột tiền tệ của bạn đang có dấu phẩy (VD: 4,500,000)
                        // Trong CSV, dấu phẩy là ngắt cột, nên phải bao chuỗi tiền vào trong ngoặc kép "" để không bị gãy form
                        if (text.contains(",")) {
                            text = "\"" + text + "\"";
                        }
                        
                        bw.write(text);
                        if (j < tblLuong.getColumnCount() - 1) bw.write(",");
                    }
                    bw.newLine();
                }
                
                bw.close();
                osw.close();
                fos.close();
                
                JOptionPane.showMessageDialog(this, "Xuất file thành công!\nĐã lưu tại: " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel!");
        }
    }
    // Load danh sách Học kỳ
    private void loadHocKy() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs.next()) cboHocKy.addItem(rs.getString(1));
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === HÀM PHÉP THUẬT: JOIN 5 BẢNG VÀ TÍNH TOÁN ===
    private void thucHienTinhLuong() {
        tableModel.setRowCount(0);
        if(cboHocKy.getSelectedItem() == null) return;
        String hk = cboHocKy.getSelectedItem().toString();

        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            
            // Siêu câu lệnh SQL gom nhóm và tính toán
            // Công thức: Tổng lương = Số TC * 15 tiết * Đơn giá * Hệ số môn học
            String sql = "SELECT c.MSCB, c.HOTEN, " +
                         "SUM(m.SOTC) AS TONG_TC, " +
                         "SUM(m.SOTC * 15 * cd.DONGIATIET * lm.HESO) AS TONG_LUONG " +
                         "FROM CANBOGIANGDAY c " +
                         "JOIN CHUCDANH cd ON c.MACD = cd.MACD " +
                         "JOIN LOPHOCPHAN lhp ON c.MSCB = lhp.MSCB " +
                         "JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "JOIN LOAIMONHOC lm ON m.MALOAIMH = lm.MALOAIMH " +
                         "WHERE lhp.MAHOCKY = ? " +
                         "GROUP BY c.MSCB, c.HOTEN";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk);
            ResultSet rs = ps.executeQuery();
            
            DecimalFormat formatter = new DecimalFormat("###,###,###"); // Format tiền tệ cho đẹp

            while(rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MSCB"),
                    rs.getString("HOTEN"),
                    rs.getInt("TONG_TC"),
                    formatter.format(rs.getDouble("TONG_LUONG")) // Hiển thị VD: 4,500,000
                });
            }
            conn.close();

            if (tableModel.getRowCount() > 0) {
                btnLuuDb.setEnabled(true); // Bật nút Lưu lên
                btnXuatExcel.setEnabled(true); // <--- THÊM DÒNG NÀY ĐỂ NÚT HẾT MỜ
                JOptionPane.showMessageDialog(this, "Đã tính xong lương cho học kỳ " + hk);
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu giảng dạy trong học kỳ này!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tính toán hệ thống!");
        }
    }

    // === LƯU CHỐT LƯƠNG XUỐNG BẢNG BANGLUONG ===
    private void luuVaoDatabase() {
        String hk = cboHocKy.getSelectedItem().toString();
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            
            // Xóa sạch bảng lương cũ của học kỳ này (để lỡ có chạy tính lại thì không bị trùng)
            PreparedStatement psDelete = conn.prepareStatement("DELETE FROM BANGLUONG WHERE MAHOCKY = ?");
            psDelete.setString(1, hk);
            psDelete.executeUpdate();

            // Insert toàn bộ dữ liệu từ JTable xuống CSDL
            String sqlInsert = "INSERT INTO BANGLUONG (MSCB, MAHOCKY, TONGTIEN, NGAYTAO) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);

            for (int i = 0; i < tblLuong.getRowCount(); i++) {
                psInsert.setString(1, tblLuong.getValueAt(i, 0).toString()); // Mã CB
                psInsert.setString(2, hk); // Học kỳ
                
                // Chuỗi tiền tệ đang có dấu phẩy (VD: 4,500,000), phải xóa dấu phẩy đi mới lưu SQL được
                String tienStr = tblLuong.getValueAt(i, 3).toString().replace(",", "");
                psInsert.setDouble(3, Double.parseDouble(tienStr));
                
                psInsert.executeUpdate();
            }
            conn.close();
            JOptionPane.showMessageDialog(this, "Đã chốt và lưu Bảng lương thành công vào hệ thống!");
            btnLuuDb.setEnabled(false); // Lưu xong thì khóa nút lại
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào Database!");
        }
    }

    public static void main(String[] args) {
        new FrmTinhLuong().setVisible(true);
    }
}