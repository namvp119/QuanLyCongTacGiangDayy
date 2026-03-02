package dao;

import java.sql.*;
import java.util.*;
import model.CanBoGiangDay;
import dao.DatabaseConnection;

public class CanBoGiangDayDAO {

	public List<CanBoGiangDay> getAll() {

	    List<CanBoGiangDay> list = new ArrayList<>();

	    String sql = """
	        SELECT cb.*, k.TENKHOA, cd.TENCD
	        FROM CANBOGIANGDAY cb
	        JOIN KHOA k ON cb.MAKHOA = k.MAKHOA
	        JOIN CHUCDANH cd ON cb.MACD = cd.MACD
	    """;

	    try (Connection con = DatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            CanBoGiangDay cb = new CanBoGiangDay(
	                    rs.getString("MSCB"),
	                    rs.getString("HOTEN"),
	                    rs.getString("GIOITINH"),
	                    rs.getDate("NGAYSINH"),
	                    rs.getString("QUEQUAN"),
	                    rs.getString("DIACHI"),
	                    rs.getString("SDT"),
	                    rs.getString("EMAIL"),
	                    rs.getString("TINHTRANG"),
	                    rs.getString("MAKHOA"),
	                    rs.getString("MACD")
	            );

	            list.add(cb);
	        }

	    } catch (Exception e) { e.printStackTrace(); }

	    return list;
	}
    public boolean insert(CanBoGiangDay cb) {
        String sql = "INSERT INTO CANBOGIANGDAY VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cb.getMscb());
            ps.setString(2, cb.getHoTen());
            ps.setString(3, cb.getGioiTinh());
            ps.setDate(4, new java.sql.Date(cb.getNgaySinh().getTime()));
            ps.setString(5, cb.getQueQuan());
            ps.setString(6, cb.getDiaChi());
            ps.setString(7, cb.getSdt());
            ps.setString(8, cb.getEmail());
            ps.setString(9, cb.getTinhTrang());
            ps.setString(10, cb.getMaKhoa());
            ps.setString(11, cb.getMaCD());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean update(CanBoGiangDay cb) {
        String sql = "UPDATE CANBOGIANGDAY SET HOTEN=?,GIOITINH=?,NGAYSINH=?,QUEQUAN=?,DIACHI=?,SDT=?,EMAIL=?,TINHTRANG=?,MAKHOA=?,MACD=? WHERE MSCB=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cb.getHoTen());
            ps.setString(2, cb.getGioiTinh());
            ps.setDate(3, new java.sql.Date(cb.getNgaySinh().getTime()));
            ps.setString(4, cb.getQueQuan());
            ps.setString(5, cb.getDiaChi());
            ps.setString(6, cb.getSdt());
            ps.setString(7, cb.getEmail());
            ps.setString(8, cb.getTinhTrang());
            ps.setString(9, cb.getMaKhoa());
            ps.setString(10, cb.getMaCD());
            ps.setString(11, cb.getMscb());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean delete(String mscb) {
        String sql = "DELETE FROM CANBOGIANGDAY WHERE MSCB=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mscb);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}