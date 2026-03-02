package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.NguoiDung;

public class NguoiDungDAO {

    public static NguoiDung kiemTraDangNhap(String taiKhoan, String matKhau) {
        NguoiDung nd = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            String sql = "SELECT * FROM NGUOIDUNG WHERE MaND = ? AND MatKhau = ? AND TinhTrang = 'Đang sử dụng'";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, taiKhoan);
            ps.setString(2, matKhau);
            
            ResultSet rs = ps.executeQuery();
            
            // Nếu có dữ liệu trả về nghĩa là đăng nhập thành công
            if (rs.next()) {
                nd = new NguoiDung();
                nd.setMaND(rs.getString("MaND"));
                nd.setMaLoai(rs.getString("MaLoai"));
                nd.setTinhTrang(rs.getString("TinhTrang"));
                nd.setMatKhau(rs.getString("MatKhau"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nd;
    }
}