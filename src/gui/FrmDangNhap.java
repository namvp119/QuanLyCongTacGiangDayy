package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import dao.NguoiDungDAO;
import model.NguoiDung;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;

public class FrmDangNhap extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMatKhau;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public FrmDangNhap() {
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 408, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tài Khoản");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(39, 74, 106, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mật Khẩu");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(39, 110, 88, 12);
		contentPane.add(lblNewLabel_1);
		
		txtTaiKhoan = new JTextField();
		txtTaiKhoan.setBounds(119, 80, 144, 18);
		contentPane.add(txtTaiKhoan);
		txtTaiKhoan.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(90, 25, 258, 39);
		contentPane.add(lblNewLabel_2);
		
		JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.setBounds(60, 152, 106, 26);
		contentPane.add(btnDangNhap);
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.setBounds(206, 152, 106, 26);
		contentPane.add(btnThoat);
		
		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(119, 109, 144, 18);
		contentPane.add(txtMatKhau);
		
		// 1. CHỖ NÀY LÀ CỦA NÚT ĐĂNG NHẬP
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ---> QUĂNG CODE ĐĂNG NHẬP VÀO ĐÂY <---
                String taiKhoan = txtTaiKhoan.getText();
                String matKhau = new String(txtMatKhau.getPassword()); 

                if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                    return;
                }

                NguoiDung nd = NguoiDungDAO.kiemTraDangNhap(taiKhoan, matKhau);

             // ... code kiểm tra đăng nhập ...
                if (nd != null) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    dispose(); // Đóng cửa sổ đăng nhập
                    
                    // MỞ FORM TRANG CHỦ LÊN VÀ QUĂNG THÔNG TIN NGƯỜI DÙNG QUA ĐÓ
                    FrmTrangChu frmTrangChu = new FrmTrangChu(nd);
                    frmTrangChu.setVisible(true); 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        });

        // 2. CHỖ NÀY LÀ CỦA NÚT THOÁT
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ---> QUĂNG CODE THOÁT VÀO ĐÂY <---
                int xacNhan = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (xacNhan == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
	}
}
