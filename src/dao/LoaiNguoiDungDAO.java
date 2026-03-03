package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.LoaiNguoiDung;

public class LoaiNguoiDungDAO {
    public List<LoaiNguoiDung> getAll() {
        List<LoaiNguoiDung> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM loainguoidung");
            while (rs.next()) {
                list.add(new LoaiNguoiDung(rs.getString("MaLoai"), rs.getString("TenLoaiND")));
            }
            con.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
}