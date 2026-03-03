package gui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrmXemLich extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboGiangVien;
    private JComboBox<String> cboHocKy;
    private JTable tblLich;
    private DefaultTableModel tableModel;
    private JButton btnXemLich;
    
    // Khai báo DAO
    private dao.LichDayDAO lichDAO = new dao.LichDayDAO();

    public FrmXemLich() {
        setTitle("Tra Cứu Thời Khóa Biểu Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 500);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTieuDe = new JLabel("LỊCH GIẢNG DẠY CÁN BỘ");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(Color.BLUE);
        lblTieuDe.setBounds(290, 10, 250, 30);
        contentPane.add(lblTieuDe);
        
        // --- KHU VỰC LỌC DỮ LIỆU ---
        JLabel lblGiangVien = new JLabel("Mã Giảng Viên:");
        lblGiangVien.setBounds(50, 60, 100, 25);
        contentPane.add(lblGiangVien);
        
        cboGiangVien = new JComboBox<>();
        cboGiangVien.setBounds(150, 60, 150, 25);
        contentPane.add(cboGiangVien);
        
        JLabel lblHocKy = new JLabel("Học Kỳ:");
        lblHocKy.setBounds(330, 60, 60, 25);
        contentPane.add(lblHocKy);
        
        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(390, 60, 120, 25);
        contentPane.add(cboHocKy);
        
        btnXemLich = new JButton("Tra Cứu");
        btnXemLich.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXemLich.setBounds(540, 55, 120, 35);
        contentPane.add(btnXemLich);
        
        // --- KHU VỰC BẢNG HIỂN THỊ ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 780, 320);
        contentPane.add(scrollPane);
        
        String[] cols = {"Mã LHP", "Tên Môn", "Phòng", "Thứ", "Tiết BĐ", "Tiết KT", "Sĩ Số"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLich = new JTable(tableModel);
        scrollPane.setViewportView(tblLich);
        
        loadComboBox();
        
        btnXemLich.addActionListener(e -> traCuuLich());
    }

    private void loadComboBox() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            
            ResultSet rs1 = st.executeQuery("SELECT MSCB FROM CANBOGIANGDAY");
            while(rs1.next()) cboGiangVien.addItem(rs1.getString(1));
            
            ResultSet rs2 = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs2.next()) cboHocKy.addItem(rs2.getString(1));
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ĐÃ LÀM SẠCH BẰNG DAO
    private void traCuuLich() {
        tableModel.setRowCount(0);
        if(cboGiangVien.getSelectedItem() == null || cboHocKy.getSelectedItem() == null) return;
        
        String mscb = cboGiangVien.getSelectedItem().toString();
        String hk = cboHocKy.getSelectedItem().toString();
        
        java.util.List<model.LichDay> ds = lichDAO.traCuuLichDay(mscb, hk);
        
        for (model.LichDay ld : ds) {
            tableModel.addRow(new Object[]{
                ld.getMaLHP(), ld.getTenMH(), ld.getMaPhong(), 
                ld.getThu(), ld.getTietBD(), ld.getTietKT(), ld.getSiSo()
            });
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giảng viên này không có lịch dạy trong học kỳ đã chọn!");
        }
    }

    public static void main(String[] args) {
        new FrmXemLich().setVisible(true);
    }
}