package dao;

import model.HocKy;
import java.sql.*;
import java.util.ArrayList;

public class HocKyDAO {

    public static ArrayList<HocKy> layDanhSach() {
        ArrayList<HocKy> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM HOCKY";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new HocKy(
                        rs.getString("MAHOCKY"),
                        rs.getString("TENHK"),
                        rs.getString("NAMHOC"),
                        rs.getString("TINHTRANG")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean them(HocKy hk) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO HOCKY VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk.getMaHocKy());
            ps.setString(2, hk.getTenHK());
            ps.setString(3, hk.getNamHoc());
            ps.setString(4, hk.getTinhTrang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sua(HocKy hk) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE HOCKY SET TENHK=?, NAMHOC=?, TINHTRANG=? WHERE MAHOCKY=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk.getTenHK());
            ps.setString(2, hk.getNamHoc());
            ps.setString(3, hk.getTinhTrang());
            ps.setString(4, hk.getMaHocKy());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoa(String ma) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "DELETE FROM HOCKY WHERE MAHOCKY=?";
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

            String sql = "SELECT 1 FROM HOCKY WHERE MAHOCKY=?";
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