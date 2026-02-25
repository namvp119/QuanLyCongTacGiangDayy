package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Khoa;

public class KhoaDAO {

    // 1. HÀM ĐỌC: Lấy danh sách Khoa (Để Ân đổ lên bảng JTable)
    public static ArrayList<Khoa> layDanhSachKhoa() {
        ArrayList<Khoa> dsKhoa = new ArrayList<>();
        try {
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

    // 2. HÀM THÊM: Nhận cái hộp (Khoa k) từ Ân và lưu xuống Database
    public static boolean themKhoa(Khoa k) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO KHOA (MAKHOA, TENKHOA) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Bung cái hộp ra để lấy dữ liệu ráp vào dấu chấm hỏi (?)
            ps.setString(1, k.getMaKhoa());
            ps.setString(2, k.getTenKhoa());
            
            int ketQua = ps.executeUpdate(); // Chạy câu lệnh
            conn.close();
            return ketQua > 0; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            System.out.println("Lỗi trùng mã khoa hoặc lỗi CSDL!");
            e.printStackTrace();
            return false;
        }
    }

    // 3. HÀM SỬA: Cập nhật Tên Khoa dựa vào Mã Khoa
    public static boolean capNhatKhoa(Khoa k) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE KHOA SET TENKHOA = ? WHERE MAKHOA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, k.getTenKhoa());
            ps.setString(2, k.getMaKhoa());
            
            int ketQua = ps.executeUpdate();
            conn.close();
            return ketQua > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. HÀM XÓA: Chỉ cần cung cấp Mã Khoa là xóa được
    public static boolean xoaKhoa(String maKhoa) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM KHOA WHERE MAKHOA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, maKhoa);
            
            int ketQua = ps.executeUpdate();
            conn.close();
            return ketQua > 0;
        } catch (Exception e) {
            System.out.println("Lỗi: Không thể xóa vì Khoa này đã có Giảng Viên hoặc Lớp!");
            e.printStackTrace();
            return false;
        }
    }

    // --- KHU VỰC NAM TỰ TEST (Không liên quan đến giao diện của Ân) ---
    public static void main(String[] args) {
        // Test 1: Đóng gói 1 cái hộp (Model) và đưa cho Shipper (DAO) thêm vào kho
        Khoa khoaMoi = new Khoa("CNTT", "Công nghệ thông tin");
        boolean isThem = KhoaDAO.themKhoa(khoaMoi);
        if (isThem) {
            System.out.println("✅ Đã thêm Khoa thành công!");
        }

        // Test 2: Thêm thử 1 khoa nữa
        Khoa khoaMoi2 = new Khoa("KT", "Kinh tế");
        KhoaDAO.themKhoa(khoaMoi2);

        // Test 3: Sửa tên khoa KT thành Quản trị kinh doanh
        Khoa khoaCanSua = new Khoa("KT", "Quản trị kinh doanh");
        boolean isSua = KhoaDAO.capNhatKhoa(khoaCanSua);
        if (isSua) {
            System.out.println("✅ Đã sửa tên Khoa thành công!");
        }

        // Test 4: Đọc danh sách kho xem hiện tại có gì
        System.out.println("--- DANH SÁCH KHOA HIỆN TẠI ---");
        ArrayList<Khoa> list = KhoaDAO.layDanhSachKhoa();
        for (Khoa k : list) {
            System.out.println(k.getMaKhoa() + " - " + k.getTenKhoa());
        }
        
        // Test 5: Nếu bạn muốn test Xóa, hãy bỏ dấu // ở 2 dòng dưới đây
        // boolean isXoa = KhoaDAO.xoaKhoa("KT");
        // System.out.println("Trạng thái xóa khoa KT: " + isXoa);
    }
}