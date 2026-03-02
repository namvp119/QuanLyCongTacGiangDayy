package gui;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrmPhanCong extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // 1. CÁC BIẾN NHẬP LIỆU
    private JTextField txtMaLHP;
    private JTextField txtTenLHP;
    private JTextField txtSiSo;
    private JTextField txtNgayBD;
    private JComboBox<String> cboTinhTrang;
    private JComboBox<String> cboGiangVien;
    private JComboBox<String> cboMonHoc;
    private JComboBox<String> cboPhongHoc;
    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboLop;

    // 2. CÁC NÚT BẤM VÀ BẢNG
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JTable tblPhanCong;
    private DefaultTableModel tableModel;

    /**
     * Launch the application (Dùng để chạy test form này độc lập)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmPhanCong frame = new FrmPhanCong();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FrmPhanCong() {
        setTitle("Quản Lý Phân Công Giảng Dạy (Lớp Học Phần)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng form này, không tắt cả chương trình
        setBounds(100, 100, 950, 650);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Sử dụng tọa độ tuyệt đối giống WindowBuilder

        // ================= PHẦN 1: KHU VỰC NHẬP LIỆU =================
        JLabel lblTieuDe = new JLabel("PHÂN CÔNG GIẢNG DẠY");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTieuDe.setBounds(350, 10, 250, 30);
        contentPane.add(lblTieuDe);

        // Cột 1
        JLabel lblMaLHP = new JLabel("Mã LHP:");
        lblMaLHP.setBounds(30, 60, 80, 20);
        contentPane.add(lblMaLHP);
        txtMaLHP = new JTextField();
        txtMaLHP.setBounds(120, 60, 150, 25);
        contentPane.add(txtMaLHP);

        JLabel lblTenLHP = new JLabel("Tên LHP:");
        lblTenLHP.setBounds(30, 100, 80, 20);
        contentPane.add(lblTenLHP);
        txtTenLHP = new JTextField();
        txtTenLHP.setBounds(120, 100, 150, 25);
        contentPane.add(txtTenLHP);

        JLabel lblSiSo = new JLabel("Sĩ số:");
        lblSiSo.setBounds(30, 140, 80, 20);
        contentPane.add(lblSiSo);
        txtSiSo = new JTextField();
        txtSiSo.setBounds(120, 140, 150, 25);
        contentPane.add(txtSiSo);
        
        JLabel lblNgay = new JLabel("Ngày BĐ:");
        lblNgay.setBounds(30, 180, 80, 20);
        contentPane.add(lblNgay);
        txtNgayBD = new JTextField("YYYY-MM-DD");
        txtNgayBD.setBounds(120, 180, 150, 25);
        contentPane.add(txtNgayBD);

        // Cột 2 (Các JComboBox cho Khóa Ngoại)
        JLabel lblGiangVien = new JLabel("Giảng viên:");
        lblGiangVien.setBounds(320, 60, 80, 20);
        contentPane.add(lblGiangVien);
        cboGiangVien = new JComboBox<>();
        cboGiangVien.setBounds(400, 60, 180, 25);
        contentPane.add(cboGiangVien);

        JLabel lblMonHoc = new JLabel("Môn học:");
        lblMonHoc.setBounds(320, 100, 80, 20);
        contentPane.add(lblMonHoc);
        cboMonHoc = new JComboBox<>();
        cboMonHoc.setBounds(400, 100, 180, 25);
        contentPane.add(cboMonHoc);

        JLabel lblPhong = new JLabel("Phòng học:");
        lblPhong.setBounds(320, 140, 80, 20);
        contentPane.add(lblPhong);
        cboPhongHoc = new JComboBox<>();
        cboPhongHoc.setBounds(400, 140, 180, 25);
        contentPane.add(cboPhongHoc);

        JLabel lblHocKy = new JLabel("Học kỳ:");
        lblHocKy.setBounds(320, 180, 80, 20);
        contentPane.add(lblHocKy);
        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(400, 180, 180, 25);
        contentPane.add(cboHocKy);

        // Cột 3
        JLabel lblLop = new JLabel("Lớp CN:");
        lblLop.setBounds(630, 60, 70, 20);
        contentPane.add(lblLop);
        cboLop = new JComboBox<>();
        cboLop.setBounds(700, 60, 180, 25);
        contentPane.add(cboLop);
        
        JLabel lblTinhTrang = new JLabel("Tình trạng:");
        lblTinhTrang.setBounds(630, 100, 70, 20);
        contentPane.add(lblTinhTrang);
        cboTinhTrang = new JComboBox<>();
        cboTinhTrang.addItem("Đang mở");
        cboTinhTrang.addItem("Đã khóa");
        cboTinhTrang.setBounds(700, 100, 180, 25);
        contentPane.add(cboTinhTrang);

        // ================= PHẦN 2: KHU VỰC NÚT BẤM =================
        btnThem = new JButton("Thêm Phân Công");
        btnThem.setBounds(200, 230, 130, 35);
        contentPane.add(btnThem);

        btnSua = new JButton("Cập Nhật");
        btnSua.setBounds(350, 230, 100, 35);
        contentPane.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(470, 230, 100, 35);
        contentPane.add(btnXoa);

        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setBounds(590, 230, 100, 35);
        contentPane.add(btnLamMoi);

        // ================= PHẦN 3: BẢNG HIỂN THỊ DỮ LIỆU =================
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 290, 870, 300);
        contentPane.add(scrollPane);

        // Khởi tạo các cột cho bảng
        String[] columnNames = {"Mã LHP", "Tên Lớp HP", "Môn Học", "Giảng Viên", "Phòng", "Sĩ Số", "Ngày BĐ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblPhanCong = new JTable(tableModel);
        scrollPane.setViewportView(tblPhanCong);
        
        docDuLieuVaoBang();
        loadTatCaComboBox();
     // =========================================================
        // 1. NÚT LÀM MỚI (Xóa trắng các ô nhập liệu để nhập mới)
        // =========================================================
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtMaLHP.setText("");
                txtTenLHP.setText("");
                txtSiSo.setText("");
                txtNgayBD.setText("YYYY-MM-DD");
                
                // Trả các ComboBox về lựa chọn đầu tiên
                if(cboGiangVien.getItemCount() > 0) cboGiangVien.setSelectedIndex(0);
                if(cboMonHoc.getItemCount() > 0) cboMonHoc.setSelectedIndex(0);
                if(cboPhongHoc.getItemCount() > 0) cboPhongHoc.setSelectedIndex(0);
                if(cboHocKy.getItemCount() > 0) cboHocKy.setSelectedIndex(0);
                if(cboLop.getItemCount() > 0) cboLop.setSelectedIndex(0);
                cboTinhTrang.setSelectedIndex(0);
                
                txtMaLHP.requestFocus(); // Nhảy con trỏ chuột về ô Mã LHP
            }
        });

        // =========================================================
        // 2. NÚT THÊM PHÂN CÔNG
        // =========================================================
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // Gom dữ liệu từ trên giao diện nhét vào hộp Model
                    model.LopHocPhan lhp = new model.LopHocPhan();
                    lhp.setMaLHP(txtMaLHP.getText());
                    lhp.setTenLHP(txtTenLHP.getText());
                    lhp.setSiSo(Integer.parseInt(txtSiSo.getText()));
                    lhp.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                    lhp.setMscb(cboGiangVien.getSelectedItem().toString());
                    lhp.setMaMH(cboMonHoc.getSelectedItem().toString());
                    lhp.setMaPhong(cboPhongHoc.getSelectedItem().toString());
                    lhp.setMaHocKy(cboHocKy.getSelectedItem().toString());
                    lhp.setMaLop(cboLop.getSelectedItem().toString());
                    
                    // Ép kiểu chuỗi ngày tháng sang dạng Date của SQL (Bắt buộc phải gõ đúng YYYY-MM-DD)
                    lhp.setNgay(java.sql.Date.valueOf(txtNgayBD.getText())); 

                    // Gọi DAO đi giao hàng cho Database
                    if (dao.LopHocPhanDAO.themLopHocPhan(lhp)) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Thêm phân công thành công!");
                        docDuLieuVaoBang(); // F5 lại bảng dữ liệu
                        btnLamMoi.doClick(); // Gọi nút Làm mới để xóa trắng ô chữ
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Thêm thất bại. Có thể trùng Mã LHP!");
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Lỗi dữ liệu! Sĩ số phải là số và Ngày phải đúng chuẩn YYYY-MM-DD.");
                }
            }
        });

        // =========================================================
        // 3. NÚT SỬA (CẬP NHẬT)
        // =========================================================
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = tblPhanCong.getSelectedRow();
                if (row < 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng click chọn 1 lớp trên bảng để Sửa!");
                    return;
                }
                
                try {
                    model.LopHocPhan lhp = new model.LopHocPhan();
                    lhp.setMaLHP(txtMaLHP.getText()); // Mã không được đổi, dùng làm chìa khóa tìm kiếm
                    lhp.setTenLHP(txtTenLHP.getText());
                    lhp.setSiSo(Integer.parseInt(txtSiSo.getText()));
                    lhp.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                    lhp.setMscb(cboGiangVien.getSelectedItem().toString());
                    lhp.setMaMH(cboMonHoc.getSelectedItem().toString());
                    lhp.setMaPhong(cboPhongHoc.getSelectedItem().toString());
                    lhp.setMaHocKy(cboHocKy.getSelectedItem().toString());
                    lhp.setMaLop(cboLop.getSelectedItem().toString());
                    lhp.setNgay(java.sql.Date.valueOf(txtNgayBD.getText())); 

                    if (dao.LopHocPhanDAO.capNhatLopHocPhan(lhp)) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                        docDuLieuVaoBang(); 
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Lỗi dữ liệu nhập vào!");
                }
            }
        });

        // =========================================================
        // 4. NÚT XÓA
        // =========================================================
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = tblPhanCong.getSelectedRow();
                if (row < 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng click chọn dòng cần Xóa trên bảng!");
                    return;
                }
                
                String maLHP = txtMaLHP.getText();
                // Bật hộp thoại hỏi cho chắc chắn
                int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
                    "Bạn có chắc chắn muốn xóa lớp " + maLHP + " không?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
                
                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    if (dao.LopHocPhanDAO.xoaLopHocPhan(maLHP)) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        docDuLieuVaoBang();
                        btnLamMoi.doClick();
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            }
        });
        tblPhanCong.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Lấy ra vị trí dòng mà người dùng vừa click
                int row = tblPhanCong.getSelectedRow();
                
                if (row >= 0) {
                    // Lấy dữ liệu từng cột ở dòng đó và ném lên các ô chữ
                    txtMaLHP.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenLHP.setText(tableModel.getValueAt(row, 1).toString());
                    
                    // Ném lên JComboBox (Ghi chú: Lát nữa mình sẽ làm cho nó xịn hơn)
                    if(tableModel.getValueAt(row, 2) != null) {
                        cboMonHoc.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                    }
                    if(tableModel.getValueAt(row, 3) != null) {
                        cboGiangVien.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    }
                    if(tableModel.getValueAt(row, 4) != null) {
                        cboPhongHoc.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                    }
                    
                    txtSiSo.setText(tableModel.getValueAt(row, 5).toString());
                    
                    if(tableModel.getValueAt(row, 6) != null) {
                        txtNgayBD.setText(tableModel.getValueAt(row, 6).toString());
                    }
                }
            }
        });
        
    }
    private void docDuLieuVaoBang() {
        // 1. Xóa sạch dữ liệu cũ trên bảng (nếu có) để tránh bị nhân đôi
        tableModel.setRowCount(0);
        
        // 2. Gọi DAO lấy danh sách Lớp Học Phần từ Database
        java.util.ArrayList<model.LopHocPhan> dsLHP = dao.LopHocPhanDAO.layDanhSachLopHocPhan();
        
        // 3. Duyệt qua từng dòng dữ liệu và nạp lên Bảng
        for (model.LopHocPhan lhp : dsLHP) {
            Object[] row = {
                lhp.getMaLHP(),
                lhp.getTenLHP(),
                lhp.getMaMH(),
                lhp.getMscb(),   // Tạm thời hiển thị Mã Giảng viên
                lhp.getMaPhong(),
                lhp.getSiSo(),
                lhp.getNgay()
            };
            tableModel.addRow(row); // Thêm dòng vào bảng
        }
    }
 // Hàm siêu tốc: Quét 5 bảng trong CSDL và đổ mã vào 5 ô ComboBox
    private void loadTatCaComboBox() {
        try {
            // Mở kết nối Database
            java.sql.Connection conn = dao.DatabaseConnection.getConnection();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs;

            // 1. Đổ dữ liệu Giảng viên (MSCB)
            cboGiangVien.removeAllItems(); // Xóa rác cũ trước khi đổ
            rs = st.executeQuery("SELECT MSCB FROM CANBOGIANGDAY");
            while(rs.next()) {
                cboGiangVien.addItem(rs.getString("MSCB"));
            }

            // 2. Đổ dữ liệu Môn học (MAMH) - Lưu ý: Bảng của bạn tên là MONHC
            cboMonHoc.removeAllItems();
            rs = st.executeQuery("SELECT MAMH FROM MONHC");
            while(rs.next()) {
                cboMonHoc.addItem(rs.getString("MAMH"));
            }

            // 3. Đổ dữ liệu Phòng học (MAPHONG)
            cboPhongHoc.removeAllItems();
            rs = st.executeQuery("SELECT MAPHONG FROM PHONGHOC");
            while(rs.next()) {
                cboPhongHoc.addItem(rs.getString("MAPHONG"));
            }

            // 4. Đổ dữ liệu Học kỳ (MAHOCKY)
            cboHocKy.removeAllItems();
            rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs.next()) {
                cboHocKy.addItem(rs.getString("MAHOCKY"));
            }

            // 5. Đổ dữ liệu Lớp chuyên ngành (MALOP)
            cboLop.removeAllItems();
            rs = st.executeQuery("SELECT MALOP FROM LOPCHUYENNGANH");
            while(rs.next()) {
                cboLop.addItem(rs.getString("MALOP"));
            }

            // Đóng kết nối
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu ComboBox: " + e.getMessage());
        }
    }
}