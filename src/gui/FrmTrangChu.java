package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FrmTrangChu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String tenUser;
    private String quyen;
    private JLabel lblDongHo;

    public FrmTrangChu(String user, String role) {
        this.tenUser = user;
        this.quyen = role;

        setTitle("Phần Mềm Quản Lý Công Tác Giảng Dạy - Nam Cần Thơ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 650);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tự động phóng to toàn màn hình

        // ================= THANH MENU BAR =================
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // 1. CỤC MENU "HỆ THỐNG"
        JMenu mnHeThong = new JMenu("Hệ thống");
        mnHeThong.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnHeThong);

        JMenuItem mniDoiMatKhau = new JMenuItem("Đổi mật khẩu");
        JMenuItem mniTaiKhoan = new JMenuItem("Quản lý Tài khoản");
        JMenuItem mniDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mniThoat = new JMenuItem("Thoát");

        mnHeThong.add(mniDoiMatKhau);
        mnHeThong.add(mniTaiKhoan);
        mnHeThong.addSeparator();
        mnHeThong.add(mniDangXuat);
        mnHeThong.add(mniThoat);

        // 2. CỤC MENU "QUẢN LÝ DANH MỤC"
        JMenu mnQuanLy = new JMenu("Quản lý Danh mục");
        mnQuanLy.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnQuanLy);

        JMenuItem mniKhoa = new JMenuItem("Quản lý Khoa");
        JMenuItem mniHocKy = new JMenuItem("Quản lý Học kỳ");
        JMenuItem mniMonHoc = new JMenuItem("Quản lý Môn học");
        JMenuItem mniPhongHoc = new JMenuItem("Quản lý Phòng học");
        JMenuItem mniSinhVien = new JMenuItem("Quản lý Sinh viên");
        JMenuItem mniGiangVien = new JMenuItem("Quản lý Giảng viên");
        JMenuItem mniLopChuyenNganh = new JMenuItem("Quản lý Lớp chuyên ngành");

        mnQuanLy.add(mniKhoa);
        mnQuanLy.add(mniHocKy);
        mnQuanLy.add(mniMonHoc);
        mnQuanLy.add(mniPhongHoc);
        mnQuanLy.add(mniSinhVien);
        mnQuanLy.add(mniGiangVien);
        mnQuanLy.add(mniLopChuyenNganh);

        // 3. CỤC MENU "NGHIỆP VỤ - LỊCH DẠY"
        JMenu mnNghiepVu = new JMenu("Nghiệp vụ - Lịch dạy");
        mnNghiepVu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnNghiepVu);

        JMenuItem mniPhanCong = new JMenuItem("Phân công giảng dạy");
        JMenuItem mniXemLich = new JMenuItem("Xem lịch dạy");

        mnNghiepVu.add(mniPhanCong);
        mnNghiepVu.addSeparator();
        mnNghiepVu.add(mniXemLich);

        // 4. CỤC MENU "TÍNH LƯƠNG & BÁO CÁO"
        JMenu mnTinhLuong = new JMenu("Tính lương & Báo cáo");
        mnTinhLuong.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(mnTinhLuong);

        JMenuItem mniBangLuong = new JMenuItem("Tính bảng lương");
        JMenuItem mniThongKe = new JMenuItem("Thống kê công tác giảng dạy");

        mnTinhLuong.add(mniBangLuong);
        mnTinhLuong.add(mniThongKe);

        // ================= GIAO DIỆN HIỂN THỊ CHÍNH =================
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Header chứa Lời chào và Đồng hồ
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(0, 102, 204));
        contentPane.add(pnlHeader, BorderLayout.NORTH);
        pnlHeader.setLayout(new BorderLayout(0, 0));

        JLabel lblChao = new JLabel("   Xin chào, " + tenUser.toUpperCase() + " | Quyền: " + quyen.toUpperCase());
        lblChao.setForeground(Color.WHITE);
        lblChao.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblChao.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlHeader.add(lblChao, BorderLayout.WEST);

        lblDongHo = new JLabel("00:00:00 AM   ");
        lblDongHo.setForeground(Color.YELLOW);
        lblDongHo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDongHo.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlHeader.add(lblDongHo, BorderLayout.EAST);
        chayDongHo();

        // Banner chính giữa
        JLabel lblBanner = new JLabel("PHẦN MỀM QUẢN LÝ CÔNG TÁC GIẢNG DẠY");
        lblBanner.setForeground(new Color(0, 51, 153));
        lblBanner.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblBanner, BorderLayout.CENTER);

        // ================= CƠ CHẾ PHÂN QUYỀN =================
        // Mặc định Admin toàn quyền, không cần xử lý gì thêm.
        
        if (quyen.equalsIgnoreCase("giaovu")) {
            // Quyền Giáo vụ: Chỉ xài bảng lương và thống kê
            mniTaiKhoan.setVisible(false);
            mnQuanLy.setVisible(false); // Ẩn nguyên cụm menu Danh mục
            mnNghiepVu.setVisible(false); // Ẩn nguyên cụm menu Nghiệp vụ
        } 
        else if (quyen.equalsIgnoreCase("canbo") || quyen.equalsIgnoreCase("giangvien")) {
            // Quyền Cán bộ / Giảng viên: Chỉ xem lịch dạy
            mniTaiKhoan.setVisible(false);
            mnQuanLy.setVisible(false); 
            mnTinhLuong.setVisible(false); // Ẩn nguyên cụm menu Tính lương
            mniPhanCong.setVisible(false); // Ẩn phân công giảng dạy, chỉ để lại Xem lịch dạy
        }

        // ================= GẮN SỰ KIỆN NÚT BẤM =================
        
        // 1. Hệ Thống 
        mniDoiMatKhau.addActionListener(e -> new gui.FrmDoiMatKhau(tenUser).setVisible(true));
        mniTaiKhoan.addActionListener(e -> new gui.FrmQuanLyTaiKhoan().setVisible(true));
        mniDangXuat.addActionListener(e -> {
            this.dispose();
            new gui.FrmDangNhap().setVisible(true);
        });
        mniThoat.addActionListener(e -> System.exit(0));

        // 2. Quản Lý Danh Mục (Phần của Ân - package gd)
        mniKhoa.addActionListener(e -> new gd.QLKhoa().setVisible(true));
        mniHocKy.addActionListener(e -> new gd.QuanLyHocKy().setVisible(true));
        mniMonHoc.addActionListener(e -> new gd.QuanLyMonHoc().setVisible(true));
        mniPhongHoc.addActionListener(e -> new gd.QuanLyPhongHoc().setVisible(true));
        mniGiangVien.addActionListener(e -> new gd.QuanLyGiangVien().setVisible(true));
        mniLopChuyenNganh.addActionListener(e -> new gd.QuanLyLopChuyenNganh().setVisible(true));
        mniSinhVien.addActionListener(e -> new gd.QuanLySinhVien().setVisible(true));

        // 3. Nghiệp Vụ - Lịch Dạy (Phần của Duy - package gui)
        mniPhanCong.addActionListener(e -> new gui.FrmPhanCong().setVisible(true));
        mniXemLich.addActionListener(e -> new gui.FrmXemLich(tenUser, quyen).setVisible(true));

        // 4. Tính Lương & Báo Cáo (Phần của Duy - package gui)
        mniBangLuong.addActionListener(e -> new gui.FrmTinhLuong().setVisible(true));
        mniThongKe.addActionListener(e -> new gui.FrmThongKe().setVisible(true));
    }

    // Luồng chạy đồng hồ thời gian thực
    private void chayDongHo() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Date t = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
                        lblDongHo.setText(sdf.format(t) + "   ");
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {}
            }
        };
        thread.start();
    }
}