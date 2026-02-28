package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Khoa;

public class KhoaDAO {

    public static ArrayList<Khoa> layDanhSachKhoa() {

        ArrayList<Khoa> ds = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM KHOA";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Khoa k = new Khoa();
                k.setMaKhoa(rs.getString("MAKHOA"));
                k.setTenKhoa(rs.getString("TENKHOA"));
                ds.add(k);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public static boolean themKhoa(Khoa k) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO KHOA VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, k.getMaKhoa());
            ps.setString(2, k.getTenKhoa());

            int rows = ps.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean suaKhoa(Khoa k) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE KHOA SET TENKHOA=? WHERE MAKHOA=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, k.getTenKhoa());
            ps.setString(2, k.getMaKhoa());

            int rows = ps.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoaKhoa(String maKhoa) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM KHOA WHERE MAKHOA=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKhoa);

            int rows = ps.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}