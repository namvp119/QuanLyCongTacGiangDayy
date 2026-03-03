package dao;

import java.sql.*;
import java.util.*;
import model.SinhVien;

public class SinhVienDAO {

    public List<SinhVien> getAll() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SINHVIEN";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SinhVien sv = new SinhVien(
                        rs.getString("MSSV"),
                        rs.getString("TENSV"),
                        rs.getString("GIOITINH"),
                        rs.getDate("NGAYSINH"),
                        rs.getString("QUEQUAN"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getString("TINHTRANG"),
                        rs.getString("MALOP")
                );
                list.add(sv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean exists(String mssv) {
        String sql = "SELECT 1 FROM SINHVIEN WHERE MSSV=?";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mssv);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(SinhVien sv) {
        String sql = "INSERT INTO SINHVIEN VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sv.getMssv());
            ps.setString(2, sv.getTenSV());
            ps.setString(3, sv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(sv.getNgaySinh().getTime()));
            ps.setString(5, sv.getQueQuan());
            ps.setString(6, sv.getDiaChi());
            ps.setString(7, sv.getSdt());
            ps.setString(8, sv.getEmail());
            ps.setString(9, sv.getTinhTrang());
            ps.setString(10, sv.getMaLop());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SinhVien sv) {
        String sql = """
            UPDATE SINHVIEN 
            SET TENSV=?, GIOITINH=?, NGAYSINH=?, QUEQUAN=?, DIACHI=?, 
                SDT=?, EMAIL=?, TINHTRANG=?, MALOP=? 
            WHERE MSSV=?
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sv.getTenSV());
            ps.setString(2, sv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(sv.getNgaySinh().getTime()));
            ps.setString(4, sv.getQueQuan());
            ps.setString(5, sv.getDiaChi());
            ps.setString(6, sv.getSdt());
            ps.setString(7, sv.getEmail());
            ps.setString(8, sv.getTinhTrang());
            ps.setString(9, sv.getMaLop());
            ps.setString(10, sv.getMssv());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String mssv) {
        String sql = "DELETE FROM SINHVIEN WHERE MSSV=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mssv);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<String> getAllMaLop() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT MALOP FROM LOPCHUYENNGANH";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                list.add(rs.getString("MALOP"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
}