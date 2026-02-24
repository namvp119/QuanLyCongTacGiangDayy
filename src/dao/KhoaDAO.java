package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Khoa;

public class KhoaDAO {
    
    // Hàm lấy danh sách tất cả các Khoa để sau này Ân đổ lên JTable
    public static ArrayList<Khoa> layDanhSachKhoa() {
        ArrayList<Khoa> dsKhoa = new ArrayList<>();
        try {
            // Gọi file DatabaseConnection mà bạn đã tạo lúc nãy
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM KHOA";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Khoa k = new Khoa();
                k.setMaKhoa(rs.getString("MAKHOA"));
                k.setTenKhoa(rs.getString("TENKHOA"));
                dsKhoa.add(k);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhoa;
    }

    // Hàm main để Nam tự test thử ngầm dưới hệ thống trước khi ráp giao diện
    public static void main(String[] args) {
        ArrayList<Khoa> list = layDanhSachKhoa();
        
        if (list.isEmpty()) {
            System.out.println("Chưa có dữ liệu Khoa nào trong CSDL, hoặc kết nối bị lỗi!");
        } else {
            System.out.println("--- DANH SÁCH KHOA LẤY TỪ DATABASE ---");
            for (Khoa k : list) {
                System.out.println("Mã khoa: " + k.getMaKhoa() + " | Tên khoa: " + k.getTenKhoa());
            }
        }
    }
}