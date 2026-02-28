package gd;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import dao.KhoaDAO;
import model.Khoa;
import java.util.ArrayList;

public class QLKhoa extends JFrame {

    private JTextField txtMaKhoa, txtTenKhoa;
    private JButton btnThem, btnLuu, btnSua, btnXoa, btnThoat;
    private JList<String> listKhoa;
    private DefaultListModel<String> listModel;

    public QLKhoa() {

        setTitle("Quản lý khoa");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel pnlThongTin = new JPanel(null);
        pnlThongTin.setBorder(new TitledBorder("Thông tin chi tiết khoa"));
        pnlThongTin.setBounds(10, 10, 480, 380);
        add(pnlThongTin);

        JLabel lblMa = new JLabel("Mã khoa (*)");
        lblMa.setBounds(20, 40, 100, 25);
        pnlThongTin.add(lblMa);

        txtMaKhoa = new JTextField();
        txtMaKhoa.setBounds(130, 40, 300, 25);
        pnlThongTin.add(txtMaKhoa);

        JLabel lblTen = new JLabel("Tên khoa (*)");
        lblTen.setBounds(20, 80, 100, 25);
        pnlThongTin.add(lblTen);

        txtTenKhoa = new JTextField();
        txtTenKhoa.setBounds(130, 80, 300, 25);
        pnlThongTin.add(txtTenKhoa);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(20, 140, 90, 35);
        pnlThongTin.add(btnThem);

        btnLuu = new JButton("Lưu");
        btnLuu.setBounds(120, 140, 90, 35);
        pnlThongTin.add(btnLuu);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(220, 140, 90, 35);
        pnlThongTin.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(320, 140, 90, 35);
        pnlThongTin.add(btnXoa);

        btnThoat = new JButton("Thoát");
        btnThoat.setBounds(170, 190, 120, 35);
        pnlThongTin.add(btnThoat);

        JPanel pnlDanhSach = new JPanel(null);
        pnlDanhSach.setBorder(new TitledBorder("Danh sách khoa"));
        pnlDanhSach.setBounds(500, 10, 270, 380);
        add(pnlDanhSach);

        listModel = new DefaultListModel<>();
        listKhoa = new JList<>(listModel);

        JScrollPane scroll = new JScrollPane(listKhoa);
        scroll.setBounds(10, 25, 250, 340);
        pnlDanhSach.add(scroll);

        xuLySuKien();
        loadData();
    }

    private void loadData() {
        listModel.clear();
        ArrayList<Khoa> ds = KhoaDAO.layDanhSachKhoa();
        for (Khoa k : ds) {
            listModel.addElement(k.getMaKhoa() + " - " + k.getTenKhoa());
        }
    }

    private void xuLySuKien() {

        btnThem.addActionListener(e -> {
            txtMaKhoa.setText("");
            txtTenKhoa.setText("");
            txtMaKhoa.requestFocus();
        });

        btnLuu.addActionListener(e -> {

            String ma = txtMaKhoa.getText().trim();
            String ten = txtTenKhoa.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập đầy đủ thông tin!");
                return;
            }

            Khoa k = new Khoa(ma, ten);

            if (KhoaDAO.themKhoa(k)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnSua.addActionListener(e -> {

            String ma = txtMaKhoa.getText().trim();
            String ten = txtTenKhoa.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập đầy đủ thông tin!");
                return;
            }

            Khoa k = new Khoa(ma, ten);

            if (KhoaDAO.suaKhoa(k)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnXoa.addActionListener(e -> {

            int index = listKhoa.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Chọn khoa cần xóa!");
                return;
            }

            String item = listKhoa.getSelectedValue();
            String ma = item.split(" - ")[0];

            if (KhoaDAO.xoaKhoa(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnThoat.addActionListener(e -> System.exit(0));

        listKhoa.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String item = listKhoa.getSelectedValue();
                if (item != null) {
                    String[] arr = item.split(" - ");
                    txtMaKhoa.setText(arr[0]);
                    txtTenKhoa.setText(arr[1]);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QLKhoa().setVisible(true);
        });
    }
}