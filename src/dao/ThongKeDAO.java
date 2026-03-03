package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    // 1. Thống kê Giảng viên (Có tính Vượt giờ - Giả sử định mức là 135 tiết/học kỳ)
    public List<Object[]> getThongKeGiangVien(String hk, String khoa) {
        List<Object[]> ds = new ArrayList<>();
        int tietChuan = 135; // Định mức tiết chuẩn 1 học kỳ

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT c.MSCB, c.HOTEN, k.TENKHOA, " +
                         "COUNT(lhp.MALHP) AS TONG_LOP, " +
                         "SUM(m.SOTC * 15) AS TONG_TIET " +
                         "FROM CANBOGIANGDAY c " +
                         "JOIN KHOA k ON c.MAKHOA = k.MAKHOA " +
                         "LEFT JOIN LOPHOCPHAN lhp ON c.MSCB = lhp.MSCB AND lhp.MAHOCKY = ? " +
                         "LEFT JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "WHERE 1=1 ";
            
            if (!khoa.equals("--- Tất cả ---")) {
                sql += " AND k.MAKHOA = ? ";
            }
            sql += " GROUP BY c.MSCB, c.HOTEN, k.TENKHOA ORDER BY TONG_TIET DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk);
            if (!khoa.equals("--- Tất cả ---")) {
                ps.setString(2, khoa); // Lưu ý: ComboBox phải chứa MAKHOA hoặc chỉnh SQL theo TENKHOA
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int tongTiet = rs.getInt("TONG_TIET");
                int vuotGio = (tongTiet > tietChuan) ? (tongTiet - tietChuan) : 0; // Tính số tiết vượt
                
                ds.add(new Object[]{
                    rs.getString("MSCB"),
                    rs.getString("HOTEN"),
                    rs.getString("TENKHOA"),
                    rs.getInt("TONG_LOP"),
                    tongTiet,
                    tietChuan,
                    vuotGio
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ds;
    }

    // 2. Chi tiết các lớp giảng dạy của 1 Giảng viên (Dùng cho Click Đúp)
    public ResultSet getChiTietGiangDay(String mscb, String hk) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT lhp.MALHP, m.TENMH, m.SOTC, lhp.MAPHONG, lhp.SISO " +
                         "FROM LOPHOCPHAN lhp " +
                         "JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "WHERE lhp.MSCB = ? AND lhp.MAHOCKY = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mscb);
            ps.setString(2, hk);
            return ps.executeQuery(); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 3. Thống kê TỔNG QUÁT THEO KHOA 
    public List<Object[]> getThongKeKhoa(String hk) {
        List<Object[]> ds = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT k.MAKHOA, k.TENKHOA, " +
                         "COUNT(DISTINCT c.MSCB) AS SO_GV, " +
                         "COUNT(lhp.MALHP) AS SO_LOP, " +
                         "SUM(m.SOTC * 15) AS TONG_TIET " +
                         "FROM KHOA k " +
                         "LEFT JOIN CANBOGIANGDAY c ON k.MAKHOA = c.MAKHOA " +
                         "LEFT JOIN LOPHOCPHAN lhp ON c.MSCB = lhp.MSCB AND lhp.MAHOCKY = ? " +
                         "LEFT JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "GROUP BY k.MAKHOA, k.TENKHOA " +
                         "ORDER BY TONG_TIET DESC";
                         
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Object[]{
                    rs.getString("MAKHOA"),
                    rs.getString("TENKHOA"),
                    rs.getInt("SO_GV"),
                    rs.getInt("SO_LOP"),
                    rs.getInt("TONG_TIET")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ds;
    }
}