package dao;

import model.DonViDaoTao;

import java.sql.*;
import java.util.ArrayList;

public class DonViDaoTaoDAO {

    public static ArrayList<DonViDaoTao> layDanhSach() {
        ArrayList<DonViDaoTao> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM DONVIDAOTAO";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DonViDaoTao dv = new DonViDaoTao(
                        rs.getString("MADONVI"),
                        rs.getString("TENDONVI")
                );
                list.add(dv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static boolean tonTai(String ma) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT 1 FROM DONVIDAOTAO WHERE MADONVI=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean them(DonViDaoTao dv) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("Không kết nối được DB");
                return false;
            }

            String sql = "INSERT INTO DONVIDAOTAO(MADONVI, TENDONVI) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dv.getMaDonVi().trim());
            ps.setString(2, dv.getTenDonVi().trim());

            int rows = ps.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean sua(DonViDaoTao dv) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE DONVIDAOTAO SET TENDONVI=? WHERE MADONVI=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, dv.getTenDonVi());
            ps.setString(2, dv.getMaDonVi());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean xoa(String maDonVi) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "DELETE FROM DONVIDAOTAO WHERE MADONVI=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maDonVi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}