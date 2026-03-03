package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class FrmXemLich extends JFrame {

    private JPanel contentPane;
    private JTable tblLichDay;
    private DefaultTableModel model;
    private JComboBox<String> cboHocKy;
    private JTextField txtMaCB;

    private String tenDangNhap;
    private String quyen;

    public FrmXemLich(String tenDangNhap, String quyen) {

        this.tenDangNhap = tenDangNhap;
        this.quyen = quyen;

        setTitle("Lịch Giảng Dạy Cá Nhân");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 520);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTieuDe = new JLabel("XEM LỊCH GIẢNG DẠY CÁ NHÂN");
        lblTieuDe.setForeground(new Color(0, 102, 204));
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTieuDe.setBounds(230, 15, 450, 30);
        contentPane.add(lblTieuDe);

        // ================= BỘ LỌC =================
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBorder(new TitledBorder("Bộ lọc lịch dạy"));
        pnlFilter.setBounds(30, 60, 820, 70);
        pnlFilter.setLayout(null);
        contentPane.add(pnlFilter);

        JLabel lblMaCB = new JLabel("Mã Giảng Viên:");
        lblMaCB.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMaCB.setBounds(30, 25, 110, 25);
        pnlFilter.add(lblMaCB);

        txtMaCB = new JTextField(tenDangNhap);
        txtMaCB.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtMaCB.setBounds(150, 25, 150, 25);
        pnlFilter.add(txtMaCB);

        JLabel lblHocKy = new JLabel("Chọn Học Kỳ:");
        lblHocKy.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHocKy.setBounds(350, 25, 100, 25);
        pnlFilter.add(lblHocKy);

        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(460, 25, 200, 25);
        cboHocKy.addItem("Tất cả");
        pnlFilter.add(cboHocKy);

        loadHocKy();

        // ================= PHÂN QUYỀN =================
        if (quyen.equals("giangvien")) {
            txtMaCB.setEditable(false);
            txtMaCB.setForeground(Color.RED);
        } else {
            txtMaCB.setToolTipText("Admin/Giáo vụ có thể nhập mã giảng viên khác và Enter");
        }

        // ================= BẢNG =================
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 150, 820, 300);
        contentPane.add(scrollPane);

        String[] cols = {
                "Mã LHP",
                "Tên Lớp HP",
                "Phòng Học",
                "Học Kỳ",
                "Sĩ Số",
                "Ngày Bắt Đầu",
                "Ngày Kết Thúc"
        };

        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLichDay = new JTable(model);
        tblLichDay.setRowHeight(25);
        scrollPane.setViewportView(tblLichDay);

        // ================= SỰ KIỆN =================
        cboHocKy.addActionListener(e -> loadDuLieuLichDay());
        txtMaCB.addActionListener(e -> loadDuLieuLichDay());

        loadDuLieuLichDay();
    }

    // =========================================================
    // LOAD HỌC KỲ
    // =========================================================
    private void loadHocKy() {
        try {
            for (String hk : dao.LopHocPhanDAO.getDSHocKy()) {
                cboHocKy.addItem(hk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // LOAD DỮ LIỆU LỊCH DẠY
    // =========================================================
    private void loadDuLieuLichDay() {

        model.setRowCount(0);

        String mscb = txtMaCB.getText().trim();
        String hocKyDuocChon = cboHocKy.getSelectedItem().toString();

        List<model.LopHocPhan> dsLich =
                dao.LopHocPhanDAO.layLichDayCuaGiangVien(mscb, hocKyDuocChon);

        if (dsLich != null) {
            for (model.LopHocPhan lhp : dsLich) {
                model.addRow(new Object[]{
                        lhp.getMaLHP(),
                        lhp.getTenLHP(),
                        lhp.getMaPhong(),
                        lhp.getMaHocKy(),
                        lhp.getSiSo(),
                        lhp.getNgayBatDau(),
                        lhp.getNgayKetThuc()
                });
            }
        }
    }
}