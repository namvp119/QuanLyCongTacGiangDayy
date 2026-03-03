package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TinhLuong;

public class TinhLuongDAO {

    // Hàm thực hiện tính lương tự động cho cả học kỳ
    public List<TinhLuong> tinhLuongHocKy(String hk) {
        List<TinhLuong> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT c.MSCB, c.HOTEN, " +
                         "SUM(m.SOTC) AS TONG_TC, " +
                         "SUM(m.SOTC * 15 * cd.DONGIATIET * lm.HESO) AS TONG_TIEN " +
                         "FROM CANBOGIANGDAY c " +
                         "JOIN LOPHOCPHAN lhp ON c.MSCB = lhp.MSCB " +
                         "JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "JOIN LOAIMONHOC lm ON m.MALOAIMH = lm.MALOAIMH " +
                         "JOIN CHUCDANH cd ON c.MACD = cd.MACD " +
                         "WHERE lhp.MAHOCKY = ? " +
                         "GROUP BY c.MSCB, c.HOTEN";
                         
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hk);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TinhLuong(
                    rs.getString("MSCB"),
                    rs.getString("HOTEN"),
                    rs.getInt("TONG_TC"),
                    rs.getDouble("TONG_TIEN")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm lấy chi tiết đối soát lương (Dùng cho tính năng nhấp đúp chuột)
    public ResultSet layChiTietLuong(String maCB, String hk) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT lhp.MALHP, m.TENMH, m.SOTC, cd.DONGIATIET, lm.HESO, " +
                         "(m.SOTC * 15 * cd.DONGIATIET * lm.HESO) AS THANHTIEN " +
                         "FROM LOPHOCPHAN lhp " +
                         "JOIN MONHC m ON lhp.MAMH = m.MAMH " +
                         "JOIN LOAIMONHOC lm ON m.MALOAIMH = lm.MALOAIMH " +
                         "JOIN CANBOGIANGDAY c ON lhp.MSCB = c.MSCB " +
                         "JOIN CHUCDANH cd ON c.MACD = cd.MACD " +
                         "WHERE lhp.MSCB = ? AND lhp.MAHOCKY = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCB);
            ps.setString(2, hk);
            return ps.executeQuery(); // Trả về ResultSet để GUI tự đổ vào bảng ảo
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}