package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FrmTrangChu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmTrangChu frame = new FrmTrangChu();
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
	public FrmTrangChu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 379);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnHeThong = new JMenu("Hệ thống");
		menuBar.add(mnHeThong);
		
		JMenuItem mniDoiMatKhau = new JMenuItem("Đổi mật khẩu");
		mnHeThong.add(mniDoiMatKhau);
		
		JMenuItem mniTaiKhoan = new JMenuItem("Quản lý Tài khoản");
		mnHeThong.add(mniTaiKhoan);
		
		JMenuItem mniDangXuat = new JMenuItem("Đăng xuất");
		mnHeThong.add(mniDangXuat);
		
		JMenuItem mniThoat = new JMenuItem("Thoát");
		mnHeThong.add(mniThoat);
		
		JMenu mnQuanLy = new JMenu("Quản lí danh mục");
		menuBar.add(mnQuanLy);
		
		JMenuItem mniKhoa = new JMenuItem("Quản lý Khoa");
		mnQuanLy.add(mniKhoa);
		
		JMenuItem mniHocKy = new JMenuItem("Quản lý Học kỳ");
		mnQuanLy.add(mniHocKy);
		
		JMenuItem mniMonHoc = new JMenuItem("Quản lý Môn học");
		mnQuanLy.add(mniMonHoc);
		
		JMenuItem mniPhongHoc = new JMenuItem("Quản lý Phòng học");
		mnQuanLy.add(mniPhongHoc);
		
		JMenuItem mniSinhVien = new JMenuItem("Quản lý Sinh viên");
		mnQuanLy.add(mniSinhVien);
		
		JMenuItem mniGiangVien = new JMenuItem("Quản lý Giảng viên");
		mnQuanLy.add(mniGiangVien);
		
		JMenuItem mniLopChuyenNganh = new JMenuItem("Quản lý Lớp chuyên ngành");
		mnQuanLy.add(mniLopChuyenNganh);
		
		JMenuItem mniLopHocPhan = new JMenuItem("Quản lý Lớp học phần");
		mnQuanLy.add(mniLopHocPhan);
		
		JMenu mnNghiepVu = new JMenu("Nghiệp vụ - Lịch dạy");
		menuBar.add(mnNghiepVu);
		
		JMenuItem mniPhanCong = new JMenuItem("Phân công giảng dạy");
		mnNghiepVu.add(mniPhanCong);
		
		JMenuItem mniXemLich = new JMenuItem("Xem thời khóa biểu");
		mnNghiepVu.add(mniXemLich);
		
		JMenu mnTinhLuong = new JMenu("Tính lương & Báo cáo");
		menuBar.add(mnTinhLuong);
		
		JMenuItem mniCauHinhLuong = new JMenuItem("Cấu hình Đơn giá & Hệ số");
		mnTinhLuong.add(mniCauHinhLuong);
		
		JMenuItem mniBangLuong = new JMenuItem("Tính bảng lương");
		mnTinhLuong.add(mniBangLuong);
		
		JMenuItem mniThongKe = new JMenuItem("Thống kê công tác giảng dạy");
		mnTinhLuong.add(mniThongKe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
	// ---> 1. Khai báo thêm biến này ở dưới cùng danh sách các biến
    private model.NguoiDung nguoiDungDangNhap; 

    /**
     * Create the frame.
     */
    // ---> 2. Sửa lại hàm khởi tạo để nhận dữ liệu
    public FrmTrangChu(model.NguoiDung nd) {
        
        // ---> 3. Gán dữ liệu vào biến
        this.nguoiDungDangNhap = nd;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ... (Khu vực code tự sinh của WindowBuilder - BẠN GIỮ NGUYÊN) ...
        
        // ---> 4. Gọi hàm phân quyền ở ngay TRƯỚC dấu ngoặc nhọn kết thúc hàm này
        phanQuyen();
    }

    // ---> 5. Tự viết thêm hàm này nằm tách biệt ở bên dưới
    private void phanQuyen() {
        if (nguoiDungDangNhap != null) {
            this.setTitle("Hệ thống quản lý - Xin chào: " + nguoiDungDangNhap.getMaND());
            
            // Nếu là giảng viên bình thường thì tàng hình các menu quản lý
            if (nguoiDungDangNhap.getMaLoai().equals("canbo")) {
                // Tùy vào lúc nãy bạn đặt tên Variable cho các cục Menu lớn là gì thì thay vào đây nhé. 
                // Ví dụ: mnQuanLy.setVisible(false);
                // mnTinhLuong.setVisible(false); 
            }
        }
    }
	
}
