package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class FrmDoiMatKhau extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField txtMatKhauCu;
    private JPasswordField txtMatKhauMoi;
    private JPasswordField txtXacNhan;
    private String tenDangNhap; 
    
    // Gọi DAO đã tạo trước đó
    private dao.TaiKhoanDAO tkDAO = new dao.TaiKhoanDAO();

    public FrmDoiMatKhau(String user) {
        this.tenDangNhap = user;

        setTitle("Bảo Mật - Đổi Mật Khẩu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 320);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTitle = new JLabel("ĐỔI MẬT KHẨU TÀI KHOẢN");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setForeground(new Color(0, 51, 153));
        lblTitle.setBounds(110, 20, 250, 30);
        contentPane.add(lblTitle);
        
        JLabel lblOld = new JLabel("Mật khẩu cũ:");
        lblOld.setBounds(50, 80, 100, 25);
        contentPane.add(lblOld);
        
        txtMatKhauCu = new JPasswordField();
        txtMatKhauCu.setBounds(160, 80, 200, 25);
        contentPane.add(txtMatKhauCu);
        
        JLabel lblNew = new JLabel("Mật khẩu mới:");
        lblNew.setBounds(50, 120, 100, 25);
        contentPane.add(lblNew);
        
        txtMatKhauMoi = new JPasswordField();
        txtMatKhauMoi.setBounds(160, 120, 200, 25);
        contentPane.add(txtMatKhauMoi);
        
        JLabel lblConfirm = new JLabel("Xác nhận lại:");
        lblConfirm.setBounds(50, 160, 100, 25);
        contentPane.add(lblConfirm);
        
        txtXacNhan = new JPasswordField();
        txtXacNhan.setBounds(160, 160, 200, 25);
        contentPane.add(txtXacNhan);
        
        JButton btnLuu = new JButton("Cập Nhật");
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLuu.setBackground(new Color(0, 153, 51));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBounds(160, 210, 120, 35);
        contentPane.add(btnLuu);
        
        // Sự kiện đổi mật khẩu
        btnLuu.addActionListener(e -> xuLyDoiMatKhau());
    }

    private void xuLyDoiMatKhau() {
        String passCu = new String(txtMatKhauCu.getPassword());
        String passMoi = new String(txtMatKhauMoi.getPassword());
        String confirm = new String(txtXacNhan.getPassword());
        
        if (passCu.isEmpty() || passMoi.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường!");
            return;
        }
        
        if (!passMoi.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận không khớp!");
            return;
        }

        // ĐÃ ĐƯỢC LÀM SẠCH: Giao tiếp trực tiếp với DAO
        boolean isSuccess = tkDAO.doiMatKhau(tenDangNhap, passCu, passMoi);
        
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi! Mật khẩu cũ không chính xác hoặc lỗi kết nối DB.");
        }
    }
}