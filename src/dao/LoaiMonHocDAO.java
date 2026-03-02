package dao;

import java.sql.*;
import java.util.*;
import model.LoaiMonHoc;

public class LoaiMonHocDAO {

    public static List<LoaiMonHoc> getAll() {

        List<LoaiMonHoc> list = new ArrayList<>();

        String sql = "SELECT * FROM LOAIMONHOC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LoaiMonHoc(
                        rs.getString("MALOAIMH"),
                        rs.getString("TENLOAIMH"),
                        rs.getFloat("HESO")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}