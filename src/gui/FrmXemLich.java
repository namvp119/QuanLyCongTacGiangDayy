package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class FrmXemLich extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tblLichDay;
    private DefaultTableModel model;
    private JComboBox<String> cboHocKy;
    private JTextField txtMaCB;
    
    // Nhận dữ liệu truyền từ Form Trang Chủ
    private String tenDangNhap; 
    private String quyen;

    public FrmXemLich(String tenDangNhap, String quyen) {
        this.tenDangNhap = tenDangNhap;
        this.quyen = quyen;

        setTitle("Lịch Giảng Dạy Cá Nhân");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTieuDe = new JLabel("XEM LỊCH GIẢNG DẠY CÁ NHÂN");
        lblTieuDe.setForeground(new Color(0, 102, 204));
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTieuDe.setBounds(230, 15, 400, 30);
        contentPane.add(lblTieuDe);

        // --- BỘ LỌC TÌM KIẾM ---
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBorder(new TitledBorder("Bộ lọc lịch dạy"));
        pnlFilter.setBounds(30, 60, 770, 70);
        pnlFilter.setLayout(null);
        contentPane.add(pnlFilter);

        JLabel lblMaCB = new JLabel("Mã Giảng Viên:");
        lblMaCB.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMaCB.setBounds(30, 25, 100, 25);
        pnlFilter.add(lblMaCB);

        txtMaCB = new JTextField(tenDangNhap); // Tự động điền mã người dùng vào ô
        txtMaCB.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtMaCB.setBounds(130, 25, 150, 25);
        pnlFilter.add(txtMaCB);

        JLabel lblHocKy = new JLabel("Chọn Học Kỳ:");
        lblHocKy.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHocKy.setBounds(350, 25, 90, 25);
        pnlFilter.add(lblHocKy);

        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(440, 25, 180, 25);
        cboHocKy.addItem("Tất cả"); // Mặc định là xem toàn bộ
        pnlFilter.add(cboHocKy);

        // Nạp dữ liệu Học Kỳ từ CSDL vào ComboBox
        loadHocKy();

        // --- CƠ CHẾ PHÂN QUYỀN THÔNG MINH ---
        if (quyen.equals("giangvien")) {
            // Khóa cứng ô Mã cán bộ, Giảng viên chỉ được xem của chính mình
            txtMaCB.setEditable(false);
            txtMaCB.setForeground(Color.RED);
        } else {
            // Admin hoặc Giáo vụ được phép gõ mã người khác vào để tra cứu hộ
            txtMaCB.setToolTipText("Admin/Giáo vụ có thể gõ Mã Cán Bộ khác vào đây và Enter để tra cứu chéo");
        }

        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 150, 770, 280);
        contentPane.add(scrollPane);

        String[] cols = {"Mã Lớp HP", "Tên Môn/Lớp HP", "Phòng Học", "Học Kỳ", "Sĩ Số", "Ngày Bắt Đầu"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLichDay = new JTable(model);
        tblLichDay.setRowHeight(25);
        scrollPane.setViewportView(tblLichDay);

        // --- SỰ KIỆN TỰ ĐỘNG LỌC DỮ LIỆU (REAL-TIME) ---
        // 1. Khi chọn Học kỳ trên ComboBox => Bảng tự nhảy dữ liệu
        cboHocKy.addActionListener(e -> loadDuLieuLichDay());

        // 2. Khi Admin gõ tên mã cán bộ khác và nhấn Enter => Bảng tự nhảy dữ liệu
        txtMaCB.addActionListener(e -> loadDuLieuLichDay());

        // Gọi load dữ liệu lần đầu tiên ngay khi mở form
        loadDuLieuLichDay();
    }

    // Hàm lấy danh sách học kỳ ném vào ComboBox
    private void loadHocKy() {
        try {
            for (String hk : dao.LopHocPhanDAO.getDSHocKy()) {
                cboHocKy.addItem(hk);
            }
        } catch (Exception e) { }
    }

    // Hàm cốt lõi: Đổ dữ liệu lịch dạy lên bảng
    private void loadDuLieuLichDay() {
        model.setRowCount(0); // Xóa sạch bảng cũ
        
        String mscb = txtMaCB.getText().trim();
        String hocKyDuocChon = cboHocKy.getSelectedItem().toString();
        
        // Gọi DAO
        List<model.LopHocPhan> dsLich = dao.LopHocPhanDAO.layLichDayCuaGiangVien(mscb, hocKyDuocChon);
        
        if (dsLich != null) {
            for (model.LopHocPhan lhp : dsLich) {
                model.addRow(new Object[]{
                    lhp.getMaLHP(),
                    lhp.getTenLHP(),
                    lhp.getMaPhong(),
                    lhp.getMaHocKy(),
                    lhp.getSiSo(),
                    lhp.getNgay()
                });
            }
        }
    }
}