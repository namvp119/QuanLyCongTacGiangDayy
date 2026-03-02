package dao;

import model.PhongHoc;
import java.sql.*;
import java.util.ArrayList;

public class PhongHocDAO {

    public static ArrayList<PhongHoc> layDanhSach() {
        ArrayList<PhongHoc> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM PHONGHOC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new PhongHoc(
                        rs.getString("MAPHONG"),
                        rs.getString("TENPHONG"),
                        rs.getString("TINHTRANG")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean them(PhongHoc ph) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO PHONGHOC VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ph.getMaPhong());
            ps.setString(2, ph.getTenPhong());
            ps.setString(3, ph.getTinhTrang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sua(PhongHoc ph) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE PHONGHOC SET TENPHONG=?, TINHTRANG=? WHERE MAPHONG=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ph.getTenPhong());
            ps.setString(2, ph.getTinhTrang());
            ps.setString(3, ph.getMaPhong());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoa(String ma) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "DELETE FROM PHONGHOC WHERE MAPHONG=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean tonTai(String ma) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT 1 FROM PHONGHOC WHERE MAPHONG=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}