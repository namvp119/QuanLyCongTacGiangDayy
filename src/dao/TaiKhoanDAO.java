package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TaiKhoan;
import java.sql.*;
import java.util.*;
import model.TaiKhoan;

public class TaiKhoanDAO {

    // 1. Kiểm tra Đăng nhập (SỬA LẠI TÊN BẢNG VÀ CỘT)
    public TaiKhoan kiemTraDangNhap(String user, String pass) {
        TaiKhoan tk = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            // Đã đổi: TAIKHOAN -> nguoidung | TENDANGNHAP -> MaND | MATKHAU -> MatKhau
            String sql = "SELECT * FROM nguoidung WHERE MaND=? AND MatKhau=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                tk = new TaiKhoan(
                    rs.getString("MaND"), 
                    rs.getString("MatKhau"), 
                    rs.getString("MaLoai") // Đổi MALOAI thành MaLoai
                );
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    // 2. Đổi mật khẩu
    public boolean doiMatKhau(String user, String passCu, String passMoi) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE nguoidung SET MatKhau = ? WHERE MaND = ? AND MatKhau = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, passMoi);
            ps.setString(2, user);
            ps.setString(3, passCu);
            
            int check = ps.executeUpdate();
            conn.close();
            return check > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM nguoidung")) {
            while (rs.next()) {
                list.add(new TaiKhoan(
                    rs.getString("MaND"), 
                    rs.getString("MatKhau"), 
                    rs.getString("MaLoai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Xóa tài khoản (Sửa lỗi dòng 193)
    public boolean xoaTaiKhoan(String maND) {
        String sql = "DELETE FROM nguoidung WHERE MaND = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maND);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // Thêm tài khoản
    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO nguoidung (MaND, MatKhau, MaLoai, TinhTrang) VALUES (?, ?, ?, N'Đang sử dụng')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getMaLoai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }
}
    
