package dao;

import java.sql.*;
import java.util.*;
import model.ChucDanh;
import dao.DatabaseConnection;

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
}