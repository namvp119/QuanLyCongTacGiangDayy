package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.LichDay;

public class LichDayDAO {

    public List<LichDay> traCuuLichDay(String mscb, String hk) {
        List<LichDay> ds = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT l.MALHP, m.TENMH, l.MAPHONG, tkb.THU, tkb.TIETBATDAU, tkb.TIETKETTHUC, l.SISO " +
                         "FROM LOPHOCPHAN l " +
                         "JOIN MONHC m ON l.MAMH = m.MAMH " +
                         "LEFT JOIN THOIKHOABIEU tkb ON l.MALHP = tkb.MALHP " +
                         "WHERE l.MSCB = ? AND l.MAHOCKY = ?";
                         
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mscb);
            ps.setString(2, hk);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String thu = rs.getString("THU") != null ? rs.getString("THU") : "Chưa xếp";
                String tietBD = rs.getString("TIETBATDAU") != null ? rs.getString("TIETBATDAU") : "-";
                String tietKT = rs.getString("TIETKETTHUC") != null ? rs.getString("TIETKETTHUC") : "-";
                
                ds.add(new LichDay(
                    rs.getString("MALHP"),
                    rs.getString("TENMH"),
                    rs.getString("MAPHONG"),
                    thu,
                    tietBD,
                    tietKT,
                    rs.getInt("SISO")
                ));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}