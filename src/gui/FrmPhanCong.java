package gui;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrmPhanCong extends JFrame {

    private JPanel contentPane;

    private JTextField txtMaLHP;
    private JTextField txtTenLHP;
    private JTextField txtSiSo;
    private JTextField txtNgayBD;
    private JTextField txtNgayKT;

    private JComboBox<String> cboTinhTrang;
    private JComboBox<String> cboGiangVien;
    private JComboBox<String> cboMonHoc;
    private JComboBox<String> cboPhongHoc;
    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboLop;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;

    private JTable tblPhanCong;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmPhanCong frame = new FrmPhanCong();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmPhanCong() {

        setTitle("Quản Lý Phân Công Giảng Dạy");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 680);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTieuDe = new JLabel("PHÂN CÔNG GIẢNG DẠY");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTieuDe.setBounds(330, 10, 400, 30);
        contentPane.add(lblTieuDe);

        JLabel lblMa = new JLabel("Mã LHP:");
        lblMa.setBounds(30, 60, 100, 25);
        contentPane.add(lblMa);

        txtMaLHP = new JTextField();
        txtMaLHP.setBounds(130, 60, 180, 25);
        contentPane.add(txtMaLHP);

        JLabel lblTen = new JLabel("Tên LHP:");
        lblTen.setBounds(30, 100, 100, 25);
        contentPane.add(lblTen);

        txtTenLHP = new JTextField();
        txtTenLHP.setBounds(130, 100, 180, 25);
        contentPane.add(txtTenLHP);

        JLabel lblSiSo = new JLabel("Sĩ Số:");
        lblSiSo.setBounds(30, 140, 100, 25);
        contentPane.add(lblSiSo);

        txtSiSo = new JTextField();
        txtSiSo.setBounds(130, 140, 180, 25);
        contentPane.add(txtSiSo);

        JLabel lblNgayBD = new JLabel("Ngày BĐ:");
        lblNgayBD.setBounds(30, 180, 100, 25);
        contentPane.add(lblNgayBD);

        txtNgayBD = new JTextField("YYYY-MM-DD");
        txtNgayBD.setBounds(130, 180, 180, 25);
        contentPane.add(txtNgayBD);

        JLabel lblNgayKT = new JLabel("Ngày KT:");
        lblNgayKT.setBounds(30, 220, 100, 25);
        contentPane.add(lblNgayKT);

        txtNgayKT = new JTextField("YYYY-MM-DD");
        txtNgayKT.setBounds(130, 220, 180, 25);
        contentPane.add(txtNgayKT);

        JLabel lblGV = new JLabel("Giảng Viên:");
        lblGV.setBounds(350, 60, 100, 25);
        contentPane.add(lblGV);

        cboGiangVien = new JComboBox<>();
        cboGiangVien.setBounds(470, 60, 200, 25);
        contentPane.add(cboGiangVien);

        JLabel lblMH = new JLabel("Môn Học:");
        lblMH.setBounds(350, 100, 100, 25);
        contentPane.add(lblMH);

        cboMonHoc = new JComboBox<>();
        cboMonHoc.setBounds(470, 100, 200, 25);
        contentPane.add(cboMonHoc);

        JLabel lblPhong = new JLabel("Phòng:");
        lblPhong.setBounds(350, 140, 100, 25);
        contentPane.add(lblPhong);

        cboPhongHoc = new JComboBox<>();
        cboPhongHoc.setBounds(470, 140, 200, 25);
        contentPane.add(cboPhongHoc);

        JLabel lblHocKy = new JLabel("Học Kỳ:");
        lblHocKy.setBounds(350, 180, 100, 25);
        contentPane.add(lblHocKy);

        cboHocKy = new JComboBox<>();
        cboHocKy.setBounds(470, 180, 200, 25);
        contentPane.add(cboHocKy);

        JLabel lblLop = new JLabel("Lớp:");
        lblLop.setBounds(350, 220, 100, 25);
        contentPane.add(lblLop);

        cboLop = new JComboBox<>();
        cboLop.setBounds(470, 220, 200, 25);
        contentPane.add(cboLop);

        JLabel lblTinhTrang = new JLabel("Tình Trạng:");
        lblTinhTrang.setBounds(720, 60, 100, 25);
        contentPane.add(lblTinhTrang);

        cboTinhTrang = new JComboBox<>();
        cboTinhTrang.addItem("Đang mở");
        cboTinhTrang.addItem("Đã khóa");
        cboTinhTrang.setBounds(820, 60, 150, 25);
        contentPane.add(cboTinhTrang);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(250, 270, 120, 35);
        contentPane.add(btnThem);

        btnSua = new JButton("Cập Nhật");
        btnSua.setBounds(390, 270, 120, 35);
        contentPane.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(530, 270, 120, 35);
        contentPane.add(btnXoa);

        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setBounds(670, 270, 120, 35);
        contentPane.add(btnLamMoi);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 330, 940, 280);
        contentPane.add(scrollPane);

        String[] cols = {
                "Mã LHP", "Tên LHP", "Môn", "Giảng Viên",
                "Phòng", "Sĩ Số", "Ngày BĐ", "Ngày KT"
        };

        tableModel = new DefaultTableModel(cols, 0);
        tblPhanCong = new JTable(tableModel);
        scrollPane.setViewportView(tblPhanCong);

        loadTatCaComboBox();
        docDuLieuVaoBang();

        btnThem.addActionListener(e -> {
            try {
                model.LopHocPhan lhp = new model.LopHocPhan();

                lhp.setMaLHP(txtMaLHP.getText());
                lhp.setTenLHP(txtTenLHP.getText());
                lhp.setSiSo(Integer.parseInt(txtSiSo.getText()));
                lhp.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                lhp.setMscb(cboGiangVien.getSelectedItem().toString());
                lhp.setMaMH(cboMonHoc.getSelectedItem().toString());
                lhp.setMaPhong(cboPhongHoc.getSelectedItem().toString());
                lhp.setMaHocKy(cboHocKy.getSelectedItem().toString());
                lhp.setMaLop(cboLop.getSelectedItem().toString());

                lhp.setNgayBatDau(java.sql.Date.valueOf(txtNgayBD.getText()));
                lhp.setNgayKetThuc(java.sql.Date.valueOf(txtNgayKT.getText()));

                if (dao.LopHocPhanDAO.themLopHocPhan(lhp)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    docDuLieuVaoBang();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Sai định dạng ngày hoặc dữ liệu!");
            }
        });

        btnSua.addActionListener(e -> {
            try {
                model.LopHocPhan lhp = new model.LopHocPhan();

                lhp.setMaLHP(txtMaLHP.getText());
                lhp.setTenLHP(txtTenLHP.getText());
                lhp.setSiSo(Integer.parseInt(txtSiSo.getText()));
                lhp.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                lhp.setMscb(cboGiangVien.getSelectedItem().toString());
                lhp.setMaMH(cboMonHoc.getSelectedItem().toString());
                lhp.setMaPhong(cboPhongHoc.getSelectedItem().toString());
                lhp.setMaHocKy(cboHocKy.getSelectedItem().toString());
                lhp.setMaLop(cboLop.getSelectedItem().toString());

                lhp.setNgayBatDau(java.sql.Date.valueOf(txtNgayBD.getText()));
                lhp.setNgayKetThuc(java.sql.Date.valueOf(txtNgayKT.getText()));

                if (dao.LopHocPhanDAO.capNhatLopHocPhan(lhp)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    docDuLieuVaoBang();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Sai dữ liệu!");
            }
        });

        tblPhanCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int row = tblPhanCong.getSelectedRow();

                txtMaLHP.setText(tableModel.getValueAt(row, 0).toString());
                txtTenLHP.setText(tableModel.getValueAt(row, 1).toString());
                cboMonHoc.setSelectedItem(tableModel.getValueAt(row, 2));
                cboGiangVien.setSelectedItem(tableModel.getValueAt(row, 3));
                cboPhongHoc.setSelectedItem(tableModel.getValueAt(row, 4));
                txtSiSo.setText(tableModel.getValueAt(row, 5).toString());
                txtNgayBD.setText(tableModel.getValueAt(row, 6).toString());
                txtNgayKT.setText(tableModel.getValueAt(row, 7).toString());
            }
        });
    }

    private void docDuLieuVaoBang() {
        tableModel.setRowCount(0);
        for (model.LopHocPhan lhp : dao.LopHocPhanDAO.layDanhSachLopHocPhan()) {
            tableModel.addRow(new Object[]{
                    lhp.getMaLHP(),
                    lhp.getTenLHP(),
                    lhp.getMaMH(),
                    lhp.getMscb(),
                    lhp.getMaPhong(),
                    lhp.getSiSo(),
                    lhp.getNgayBatDau(),
                    lhp.getNgayKetThuc()
            });
        }
    }

    private void loadTatCaComboBox() {
        cboGiangVien.removeAllItems();
        for (String s : dao.LopHocPhanDAO.getDSGiangVien())
            cboGiangVien.addItem(s);

        cboMonHoc.removeAllItems();
        for (String s : dao.LopHocPhanDAO.getDSMonHoc())
            cboMonHoc.addItem(s);

        cboPhongHoc.removeAllItems();
        for (String s : dao.LopHocPhanDAO.getDSPhongHoc())
            cboPhongHoc.addItem(s);

        cboHocKy.removeAllItems();
        for (String s : dao.LopHocPhanDAO.getDSHocKy())
            cboHocKy.addItem(s);

        cboLop.removeAllItems();
        for (String s : dao.LopHocPhanDAO.getDSLop())
            cboLop.addItem(s);
    }
}