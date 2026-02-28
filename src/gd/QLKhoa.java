package gd;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setLayout(null);
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

        JLabel lblNote = new JLabel(
                "<html><font color='red'>Lưu ý: Các thông tin có dấu (*) là bắt buộc.</font></html>");
        lblNote.setBounds(20, 250, 400, 30);
        pnlThongTin.add(lblNote);

        JPanel pnlDanhSach = new JPanel();
        pnlDanhSach.setLayout(null);
        pnlDanhSach.setBorder(new TitledBorder("Danh sách khoa"));
        pnlDanhSach.setBounds(500, 10, 270, 380);
        add(pnlDanhSach);

        listModel = new DefaultListModel<>();
        listKhoa = new JList<>(listModel);

        JScrollPane scroll = new JScrollPane(listKhoa);
        scroll.setBounds(10, 25, 250, 340);
        pnlDanhSach.add(scroll);

        xuLySuKien();
    }

    private void xuLySuKien() {

        btnThem.addActionListener(e -> {
            txtMaKhoa.setText("");
            txtTenKhoa.setText("");
            txtMaKhoa.requestFocus();
        });

        btnLuu.addActionListener(e -> {

            String maKhoa = txtMaKhoa.getText().trim();
            String tenKhoa = txtTenKhoa.getText().trim();

            if (maKhoa.isEmpty() || tenKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            listModel.addElement(maKhoa + " - " + tenKhoa);

            JOptionPane.showMessageDialog(this,
                    "Lưu thành công!");
        });

        btnXoa.addActionListener(e -> {

            int index = listKhoa.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(this,
                        "Chọn khoa cần xóa!");
                return;
            }

            listModel.remove(index);
        });

        btnThoat.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn thoát không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (c == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

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