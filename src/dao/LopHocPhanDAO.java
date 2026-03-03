package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.LopHocPhan;

public class LopHocPhanDAO {

    public static ArrayList<LopHocPhan> layDanhSachLopHocPhan() {

        ArrayList<LopHocPhan> dsLHP = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM LOPHOCPHAN";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LopHocPhan lhp = new LopHocPhan();

                lhp.setMaLHP(rs.getString("MALHP"));
                lhp.setTenLHP(rs.getString("TENLHP"));
                lhp.setSiSo(rs.getInt("SISO"));
                lhp.setTinhTrang(rs.getString("TINHTRANG"));
                lhp.setMscb(rs.getString("MSCB"));
                lhp.setMaHocKy(rs.getString("MAHOCKY"));
                lhp.setMaLop(rs.getString("MALOP"));
                lhp.setMaMH(rs.getString("MAMH"));
                lhp.setMaPhong(rs.getString("MAPHONG"));

                lhp.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                lhp.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));

                dsLHP.add(lhp);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsLHP;
    }

   
    public static boolean themLopHocPhan(LopHocPhan lhp) {

        boolean ketQua = false;

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO LOPHOCPHAN "
                    + "(MALHP, TENLHP, SISO, TINHTRANG, MSCB, MAHOCKY, MALOP, MAMH, MAPHONG, NGAYBATDAU, NGAYKETTHUC) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, lhp.getMaLHP());
            ps.setString(2, lhp.getTenLHP());
            ps.setInt(3, lhp.getSiSo());
            ps.setString(4, lhp.getTinhTrang());
            ps.setString(5, lhp.getMscb());
            ps.setString(6, lhp.getMaHocKy());
            ps.setString(7, lhp.getMaLop());
            ps.setString(8, lhp.getMaMH());
            ps.setString(9, lhp.getMaPhong());
            ps.setDate(10, lhp.getNgayBatDau());
            ps.setDate(11, lhp.getNgayKetThuc());

            int soDong = ps.executeUpdate();
            if (soDong > 0)
                ketQua = true;

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public static boolean capNhatLopHocPhan(LopHocPhan lhp) {

        boolean ketQua = false;

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "UPDATE LOPHOCPHAN SET "
                    + "TENLHP=?, SISO=?, TINHTRANG=?, MSCB=?, MAHOCKY=?, "
                    + "MALOP=?, MAMH=?, MAPHONG=?, NGAYBATDAU=?, NGAYKETTHUC=? "
                    + "WHERE MALHP=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, lhp.getTenLHP());
            ps.setInt(2, lhp.getSiSo());
            ps.setString(3, lhp.getTinhTrang());
            ps.setString(4, lhp.getMscb());
            ps.setString(5, lhp.getMaHocKy());
            ps.setString(6, lhp.getMaLop());
            ps.setString(7, lhp.getMaMH());
            ps.setString(8, lhp.getMaPhong());
            ps.setDate(9, lhp.getNgayBatDau());
            ps.setDate(10, lhp.getNgayKetThuc());
            ps.setString(11, lhp.getMaLHP());

            int soDong = ps.executeUpdate();
            if (soDong > 0)
                ketQua = true;

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

 
    public static boolean xoaLopHocPhan(String maLHP) {

        boolean ketQua = false;

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM LOPHOCPHAN WHERE MALHP=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maLHP);

            int soDong = ps.executeUpdate();
            if (soDong > 0)
                ketQua = true;

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }


    public static List<String> getDSGiangVien() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MSCB FROM CANBOGIANGDAY")) {

            while (rs.next())
                list.add(rs.getString("MSCB"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getDSMonHoc() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAMH FROM MONHC")) {

            while (rs.next())
                list.add(rs.getString("MAMH"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getDSPhongHoc() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAPHONG FROM PHONGHOC")) {

            while (rs.next())
                list.add(rs.getString("MAPHONG"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getDSHocKy() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY")) {

            while (rs.next())
                list.add(rs.getString("MAHOCKY"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getDSLop() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MALOP FROM LOPCHUYENNGANH")) {

            while (rs.next())
                list.add(rs.getString("MALOP"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<LopHocPhan> layLichDayCuaGiangVien(String mscb, String maHocKy) {

        List<LopHocPhan> list = new ArrayList<>();

        String sql = "SELECT * FROM LOPHOCPHAN WHERE MSCB = ?";

        if (maHocKy != null && !maHocKy.equals("Tất cả")) {
            sql += " AND MAHOCKY = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mscb);

            if (maHocKy != null && !maHocKy.equals("Tất cả")) {
                ps.setString(2, maHocKy);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LopHocPhan lhp = new LopHocPhan();

                lhp.setMaLHP(rs.getString("MALHP"));
                lhp.setTenLHP(rs.getString("TENLHP"));
                lhp.setMaPhong(rs.getString("MAPHONG"));
                lhp.setMaHocKy(rs.getString("MAHOCKY"));
                lhp.setSiSo(rs.getInt("SISO"));
                lhp.setNgayBatDau(rs.getDate("NGAYBATDAU"));
                lhp.setNgayKetThuc(rs.getDate("NGAYKETTHUC"));

                list.add(lhp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}