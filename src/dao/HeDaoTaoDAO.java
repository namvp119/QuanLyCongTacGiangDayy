package dao;

import model.HeDaoTao;
import java.sql.*;
import java.util.ArrayList;

public class HeDaoTaoDAO {

    public static ArrayList<HeDaoTao> layDanhSach() {
        ArrayList<HeDaoTao> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT MAHE, TENHE FROM HEDAOTAO";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new HeDaoTao(
                        rs.getString("MAHE"),
                        rs.getString("TENHE")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean them(HeDaoTao hdt) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "INSERT INTO HEDAOTAO (MAHE, TENHE) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hdt.getMaHDT());
            ps.setString(2, hdt.getTenHDT());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sua(HeDaoTao hdt) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE HEDAOTAO SET TENHE=? WHERE MAHE=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hdt.getTenHDT());
            ps.setString(2, hdt.getMaHDT());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoa(String ma) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "DELETE FROM HEDAOTAO WHERE MAHE=?";
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

            String sql = "SELECT 1 FROM HEDAOTAO WHERE MAHE=?";
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