package gui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
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

public class FrmTinhLuong extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cboHocKy;
    private JTable tblLuong;
    private DefaultTableModel tableModel;
    private JButton btnTinhLuong;
    private JButton btnLuuDb;
    private JButton btnXuatExcel;
    
    // Khai báo DAO
    private dao.TinhLuongDAO tlDAO = new dao.TinhLuongDAO();

    public FrmTinhLuong() {
        setTitle("Tính Bảng Lương Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 500);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblTieuDe = new JLabel("TỔNG HỢP & TÍNH LƯƠNG GIẢNG VIÊN");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(Color.RED);
        lblTieuDe.setBounds(230, 10, 400, 30);
        contentPane.add(lblTieuDe);
        
        // --- KHU VỰC ĐIỀU KHIỂN ---
        JLabel lblHocKy = new JLabel("Chọn Học Kỳ cần tính:");
        lblHocKy.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHocKy.setBounds(50, 60, 150, 25);
        contentPane.add(lblHocKy);
        
        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(200, 60, 150, 25);
        contentPane.add(cboHocKy);
        
        btnTinhLuong = new JButton("Tự Động Tính Lương");
        btnTinhLuong.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTinhLuong.setBackground(new Color(173, 216, 230)); 
        btnTinhLuong.setBounds(380, 55, 180, 35);
        contentPane.add(btnTinhLuong);
        
        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXuatExcel.setBackground(new Color(144, 238, 144)); 
        btnXuatExcel.setBounds(740, 55, 110, 35); 
        btnXuatExcel.setEnabled(false); 
        contentPane.add(btnXuatExcel);
        
        btnLuuDb = new JButton("Lưu Chốt Lương");
        btnLuuDb.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLuuDb.setBackground(new Color(255, 182, 193)); 
        btnLuuDb.setBounds(580, 55, 150, 35);
        btnLuuDb.setEnabled(false); 
        contentPane.add(btnLuuDb);
        
        // --- KHU VỰC BẢNG ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 780, 320);
        contentPane.add(scrollPane);
        
        String[] cols = {"Mã Cán Bộ", "Họ Tên", "Tổng Tín Chỉ", "Tổng Lương (VNĐ)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tblLuong = new JTable(tableModel);
        scrollPane.setViewportView(tblLuong);
        
        loadHocKy();
        
        // --- BẮT SỰ KIỆN CLICK ĐÚP ---
        tblLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tblLuong.getSelectedRow();
                    if (row >= 0) {
                        String mscb = tblLuong.getValueAt(row, 0).toString();
                        String tenCB = tblLuong.getValueAt(row, 1).toString();
                        String hk = cboHocKy.getSelectedItem().toString();
                        xemChiTietLuong(mscb, tenCB, hk);
                    }
                }
            }
        });
        
        // BẮT SỰ KIỆN NÚT BẤM
        btnTinhLuong.addActionListener(e -> thucHienTinhLuong());
        btnLuuDb.addActionListener(e -> luuVaoDatabase());
        btnXuatExcel.addActionListener(e -> xuatFileExcel());
    }

    // --- ĐÃ ĐƯỢC LÀM SẠCH BẰNG DAO ---
    private void xemChiTietLuong(String mscb, String tenCB, String hk) {
        try {
            ResultSet rs = tlDAO.layChiTietLuong(mscb, hk);
            if (rs == null) return;

            String[] cols = {"Mã Lớp HP", "Tên Môn", "Số TC", "Đơn Giá", "Hệ Số", "Thành Tiền"};
            DefaultTableModel modelChiTiet = new DefaultTableModel(cols, 0);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            
            while (rs.next()) {
                modelChiTiet.addRow(new Object[]{
                    rs.getString("MALHP"),
                    rs.getString("TENMH"),
                    rs.getInt("SOTC"),
                    formatter.format(rs.getDouble("DONGIATIET")),
                    rs.getDouble("HESO"),
                    formatter.format(rs.getDouble("THANHTIEN"))
                });
            }
            
            JTable tblChiTiet = new JTable(modelChiTiet);
            JScrollPane scrollPane = new JScrollPane(tblChiTiet);
            scrollPane.setPreferredSize(new java.awt.Dimension(650, 200)); 
            
            JOptionPane.showMessageDialog(this, scrollPane, "Chi tiết lương: " + tenCB + " (" + hk + ")", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy xuất chi tiết lương!");
        }
    }

    private void xuatFileExcel() {
        try {
            javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
            jfc.setDialogTitle("Chọn nơi lưu file Bảng Lương");
            int choose = jfc.showSaveDialog(this);

            if (choose == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = jfc.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".csv")) {
                    file = new java.io.File(file.getParentFile(), file.getName() + ".csv");
                }

                java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
                fos.write(239); 
                fos.write(187); 
                fos.write(191); 

                java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(fos, "UTF-8");
                java.io.BufferedWriter bw = new java.io.BufferedWriter(osw);

                for (int i = 0; i < tblLuong.getColumnCount(); i++) {
                    bw.write(tblLuong.getColumnName(i));
                    if (i < tblLuong.getColumnCount() - 1) bw.write(","); 
                }
                bw.newLine(); 

                for (int i = 0; i < tblLuong.getRowCount(); i++) {
                    for (int j = 0; j < tblLuong.getColumnCount(); j++) {
                        String text = tblLuong.getValueAt(i, j) != null ? tblLuong.getValueAt(i, j).toString() : "";
                        if (text.contains(",")) {
                            text = "\"" + text + "\"";
                        }
                        bw.write(text);
                        if (j < tblLuong.getColumnCount() - 1) bw.write(",");
                    }
                    bw.newLine();
                }

                bw.close(); osw.close(); fos.close();
                JOptionPane.showMessageDialog(this, "Xuất file thành công!\nĐã lưu tại: " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel!");
        }
    }

    private void loadHocKy() {
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAHOCKY FROM HOCKY");
            while(rs.next()) cboHocKy.addItem(rs.getString(1));
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- ĐÃ ĐƯỢC LÀM SẠCH BẰNG DAO ---
    private void thucHienTinhLuong() {
        tableModel.setRowCount(0);
        if(cboHocKy.getSelectedItem() == null) return;
        String hk = cboHocKy.getSelectedItem().toString();
        
        java.util.List<model.TinhLuong> ds = tlDAO.tinhLuongHocKy(hk);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        
        for (model.TinhLuong tl : ds) {
            tableModel.addRow(new Object[]{
                tl.getMaCB(),
                tl.getHoTen(),
                tl.getTongTC(),
                formatter.format(tl.getTongTien())
            });
        }

        if (tableModel.getRowCount() > 0) {
            btnLuuDb.setEnabled(true); 
            btnXuatExcel.setEnabled(true); 
            JOptionPane.showMessageDialog(this, "Đã tính xong lương cho học kỳ " + hk);
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu giảng dạy trong học kỳ này!");
        }
    }

    private void luuVaoDatabase() {
        String hk = cboHocKy.getSelectedItem().toString();
        try {
            Connection conn = dao.DatabaseConnection.getConnection();
            PreparedStatement psDelete = conn.prepareStatement("DELETE FROM BANGLUONG WHERE MAHOCKY = ?");
            psDelete.setString(1, hk);
            psDelete.executeUpdate();
            
            String sqlInsert = "INSERT INTO BANGLUONG (MSCB, MAHOCKY, TONGTIEN, NGAYTAO) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            for (int i = 0; i < tblLuong.getRowCount(); i++) {
                psInsert.setString(1, tblLuong.getValueAt(i, 0).toString()); 
                psInsert.setString(2, hk); 
                String tienStr = tblLuong.getValueAt(i, 3).toString().replace(",", "");
                psInsert.setDouble(3, Double.parseDouble(tienStr));
                psInsert.executeUpdate();
            }
            conn.close();
            JOptionPane.showMessageDialog(this, "Đã chốt và lưu Bảng lương thành công vào hệ thống!");
            btnLuuDb.setEnabled(false); 

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào Database!");
        }
    }

    public static void main(String[] args) {
        new FrmTinhLuong().setVisible(true);
    }
}