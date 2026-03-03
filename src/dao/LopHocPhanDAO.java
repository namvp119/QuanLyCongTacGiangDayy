package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.LopHocPhan;

public class LopHocPhanDAO {

    // 1. HÀM LẤY DANH SÁCH: Quét CSDL mang lên đổ vào cái Bảng (JTable)
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
                lhp.setNgay(rs.getDate("NGAY"));
                
                dsLHP.add(lhp);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLHP;
    }

    // 2. HÀM THÊM PHÂN CÔNG: Được gọi khi bấm nút [Thêm Phân Công]
    public static boolean themLopHocPhan(LopHocPhan lhp) {
        boolean ketQua = false;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO LOPHOCPHAN (MALHP, TENLHP, SISO, TINHTRANG, MSCB, MAHOCKY, MALOP, MAMH, MAPHONG, NGAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setDate(10, lhp.getNgay());
            
            int soDongAnhHuong = ps.executeUpdate();
            if (soDongAnhHuong > 0) ketQua = true;
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // 3. HÀM SỬA PHÂN CÔNG: Được gọi khi bấm nút [Cập Nhật]
    public static boolean capNhatLopHocPhan(LopHocPhan lhp) {
        boolean ketQua = false;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE LOPHOCPHAN SET TENLHP=?, SISO=?, TINHTRANG=?, MSCB=?, MAHOCKY=?, MALOP=?, MAMH=?, MAPHONG=?, NGAY=? WHERE MALHP=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, lhp.getTenLHP());
            ps.setInt(2, lhp.getSiSo());
            ps.setString(3, lhp.getTinhTrang());
            ps.setString(4, lhp.getMscb());
            ps.setString(5, lhp.getMaHocKy());
            ps.setString(6, lhp.getMaLop());
            ps.setString(7, lhp.getMaMH());
            ps.setString(8, lhp.getMaPhong());
            ps.setDate(9, lhp.getNgay());
            ps.setString(10, lhp.getMaLHP()); // Khóa chính nằm cuối cùng ở lệnh Update
            
            int soDongAnhHuong = ps.executeUpdate();
            if (soDongAnhHuong > 0) ketQua = true;
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // 4. HÀM XÓA PHÂN CÔNG: Được gọi khi bấm nút [Xóa]
    public static boolean xoaLopHocPhan(String maLHP) {
        boolean ketQua = false;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM LOPHOCPHAN WHERE MALHP=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maLHP);
            
            int soDongAnhHuong = ps.executeUpdate();
            if (soDongAnhHuong > 0) ketQua = true;
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
 // --- 5 HÀM HỖ TRỢ ĐỔ DỮ LIỆU COMBOBOX ---
    public static java.util.List<String> getDSGiangVien() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT MSCB FROM CANBOGIANGDAY")) {
            while (rs.next()) list.add(rs.getString("MSCB"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<String> getDSMonHoc() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT MAMH FROM MONHC")) {
            while (rs.next()) list.add(rs.getString("MAMH"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<String> getDSPhongHoc() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT MAPHONG FROM PHONGHOC")) {
            while (rs.next()) list.add(rs.getString("MAPHONG"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<String> getDSHocKy() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY")) {
            while (rs.next()) list.add(rs.getString("MAHOCKY"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<String> getDSLop() {
        java.util.List<String> list = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT MALOP FROM LOPCHUYENNGANH")) {
            while (rs.next()) list.add(rs.getString("MALOP"));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}