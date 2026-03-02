package dao;

import java.sql.*;
import java.util.*;
import model.LopChuyenNganh;

public class LopChuyenNganhDAO {

    public List<LopChuyenNganh> getAll() {

        List<LopChuyenNganh> list = new ArrayList<>();

        String sql = """
            SELECT l.*, dv.TENDONVI, h.TENHE,
                   cb.HOTEN, k.TENKHOA
            FROM LOPCHUYENNGANH l
            JOIN DONVIDAOTAO dv ON l.MADONVI = dv.MADONVI
            JOIN HEDAOTAO h ON l.MAHE = h.MAHE
            JOIN CANBOGIANGDAY cb ON l.MSCB = cb.MSCB
            JOIN KHOA k ON l.MAKHOA = k.MAKHOA
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                LopChuyenNganh l = new LopChuyenNganh(
                        rs.getString("MALOP"),
                        rs.getString("TENLOP"),
                        rs.getString("NGANH"),
                        rs.getString("TINHTRANG"),
                        rs.getString("MADONVI"),
                        rs.getString("MAHE"),
                        rs.getString("MSCB"),
                        rs.getString("MAKHOA"),
                        rs.getInt("SOLUONGSV")
                );

                l.setTenDonVi(rs.getString("TENDONVI"));
                l.setTenHe(rs.getString("TENHE"));
                l.setTenCB(rs.getString("HOTEN"));
                l.setTenKhoa(rs.getString("TENKHOA"));

                list.add(l);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public boolean insert(LopChuyenNganh l) {

        String sql = "INSERT INTO LOPCHUYENNGANH VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getMaLop());
            ps.setString(2, l.getTenLop());
            ps.setString(3, l.getNganh());
            ps.setString(4, l.getTinhTrang());
            ps.setString(5, l.getMaDonVi());
            ps.setString(6, l.getMaHe());
            ps.setString(7, l.getMaCB());
            ps.setString(8, l.getMaKhoa());
            ps.setInt(9, l.getSoLuongSV());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean update(LopChuyenNganh l) {

        String sql = """
            UPDATE LOPCHUYENNGANH
            SET TENLOP=?, NGANH=?, TINHTRANG=?,
                MADONVI=?, MAHE=?, MSCB=?,
                MAKHOA=?, SOLUONGSV=?
            WHERE MALOP=?
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTenLop());
            ps.setString(2, l.getNganh());
            ps.setString(3, l.getTinhTrang());
            ps.setString(4, l.getMaDonVi());
            ps.setString(5, l.getMaHe());
            ps.setString(6, l.getMaCB());
            ps.setString(7, l.getMaKhoa());
            ps.setInt(8, l.getSoLuongSV());
            ps.setString(9, l.getMaLop());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean delete(String maLop) {

        String sql = "DELETE FROM LOPCHUYENNGANH WHERE MALOP=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLop);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
}