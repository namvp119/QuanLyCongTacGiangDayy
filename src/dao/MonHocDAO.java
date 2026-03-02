package dao;

import java.sql.*;
import java.util.*;
import model.MonHoc;

public class MonHocDAO {

    public List<MonHoc> getAll() {

        List<MonHoc> list = new ArrayList<>();

        String sql = """
            SELECT m.*, l.TENLOAIMH
            FROM MONHC m
            JOIN LOAIMONHOC l ON m.MALOAIMH = l.MALOAIMH
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                MonHoc mh = new MonHoc(
                        rs.getString("MAMH"),
                        rs.getString("TENMH"),
                        rs.getInt("SOTC"),
                        rs.getString("MALOAIMH")
                );

                mh.setTenLoaiMH(rs.getString("TENLOAIMH"));
                list.add(mh);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public boolean insert(MonHoc mh) {

        String sql = "INSERT INTO MONHC VALUES (?,?,?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mh.getMaMH());
            ps.setString(2, mh.getTenMH());
            ps.setInt(3, mh.getSoTC());
            ps.setString(4, mh.getMaLoaiMH());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean update(MonHoc mh) {

        String sql = "UPDATE MONHC SET TENMH=?, SOTC=?, MALOAIMH=? WHERE MAMH=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mh.getTenMH());
            ps.setInt(2, mh.getSoTC());
            ps.setString(3, mh.getMaLoaiMH());
            ps.setString(4, mh.getMaMH());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean delete(String maMH) {

        String sql = "DELETE FROM MONHC WHERE MAMH=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMH);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
}