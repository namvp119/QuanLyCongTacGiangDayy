package dao;

import java.sql.*;
import java.util.*;
import model.ChucDanh;

public class ChucDanhDAO {

    public List<ChucDanh> getAll() {
        List<ChucDanh> list = new ArrayList<>();
        String sql = "SELECT * FROM CHUCDANH";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ChucDanh(
                        rs.getString("MACD"),
                        rs.getString("TENCD"),
                        rs.getInt("DONGIATIET")));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public boolean insert(ChucDanh cd) {
        String sql = "INSERT INTO CHUCDANH VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cd.getMaCD());
            ps.setString(2, cd.getTenCD());
            ps.setInt(3, cd.getDonGiaTiet());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean update(ChucDanh cd) {
        String sql = "UPDATE CHUCDANH SET TENCD=?, DONGIATIET=? WHERE MACD=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cd.getTenCD());
            ps.setInt(2, cd.getDonGiaTiet());
            ps.setString(3, cd.getMaCD());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean delete(String maCD) {
        String sql = "DELETE FROM CHUCDANH WHERE MACD=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maCD);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
}