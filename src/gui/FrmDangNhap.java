package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// ---> SỬA LẠI IMPORT THÀNH TAIKHOAN VÀ TAIKHOANDAO <---
import dao.TaiKhoanDAO;
import model.TaiKhoan;

public class FrmDangNhap extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;

    // ---> KHAI BÁO DAO Ở ĐÂY <---
    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmDangNhap frame = new FrmDangNhap();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmDangNhap() {
        setTitle("Đăng Nhập Hệ Thống - Quản Lý Công Tác Giảng Dạy");
        setBackground(new Color(240, 240, 240));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 260);
        setLocationRelativeTo(null); // Code tự động đưa form ra giữa màn hình cho đẹp
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Tài Khoản");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(40, 74, 106, 26);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Mật Khẩu");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(40, 110, 88, 26);
        contentPane.add(lblNewLabel_1);
        
        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(140, 80, 180, 22);
        contentPane.add(txtTaiKhoan);
        txtTaiKhoan.setColumns(10);
        
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(140, 115, 180, 22);
        contentPane.add(txtMatKhau);
        
        JLabel lblNewLabel_2 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lblNewLabel_2.setForeground(new Color(0, 51, 153));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_2.setBounds(80, 20, 280, 39);
        contentPane.add(lblNewLabel_2);
        
        JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDangNhap.setBounds(80, 160, 120, 30);
        contentPane.add(btnDangNhap);
        
        JButton btnThoat = new JButton("THOÁT");
        btnThoat.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThoat.setBounds(220, 160, 100, 30);
        contentPane.add(btnThoat);
        
        // 1. SỰ KIỆN NÚT ĐĂNG NHẬP (Đã dọn dẹp ký tự lỗi và gắn logic DAO chuẩn)
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String taiKhoan = txtTaiKhoan.getText();
                String matKhau = new String(txtMatKhau.getPassword()); 

                if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                    return;
                }

                // Gọi DAO kiểm tra
                TaiKhoan tk = tkDAO.kiemTraDangNhap(taiKhoan, matKhau);

                if (tk != null) {
                    JOptionPane.showMessageDialog(null, "Chào mừng " + tk.getTenDangNhap() + "!");
                    dispose(); // Đóng form Đăng Nhập
                    
                    // Truyền tham số Tên và Quyền (Mã Loại) sang Trang Chủ
                    FrmTrangChu frmTrangChu = new FrmTrangChu(tk.getTenDangNhap(), tk.getMaLoai());
                    frmTrangChu.setVisible(true); 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        });

        // 2. SỰ KIỆN NÚT THOÁT
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (xacNhan == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}