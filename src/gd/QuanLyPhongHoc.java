package gd;

import dao.PhongHocDAO;
import model.PhongHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QuanLyPhongHoc extends JFrame {

    private JTextField txtMa, txtTen;
    private JComboBox<String> cbTinhTrang;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnLuu, btnSua, btnXoa, btnThoat;

    public QuanLyPhongHoc() {

        setTitle("Quản lý phòng học");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // ===== PANEL TRÁI =====
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết phòng học"));
        leftPanel.setPreferredSize(new Dimension(420,0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMa = new JTextField(15);
        txtTen = new JTextField(15);
        cbTinhTrang = new JComboBox<>(new String[]{"Trống", "Đã có lớp"});

        gbc.gridx=0; gbc.gridy=0;
        formPanel.add(new JLabel("Mã phòng:"), gbc);
        gbc.gridx=1;
        formPanel.add(txtMa, gbc);

        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Tên phòng:"), gbc);
        gbc.gridx=1;
        formPanel.add(txtTen, gbc);

        gbc.gridx=0; gbc.gridy=2;
        formPanel.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx=1;
        formPanel.add(cbTinhTrang, gbc);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        Dimension size = new Dimension(90,35);

        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnThoat = new JButton("Thoát");

        btnThem.setPreferredSize(size);
        btnLuu.setPreferredSize(size);
        btnSua.setPreferredSize(size);
        btnXoa.setPreferredSize(size);
        btnThoat.setPreferredSize(size);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnThoat);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Mã phòng","Tên phòng","Tình trạng"},0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phòng"));

        add(scroll, BorderLayout.CENTER);

        loadData();

        // ===== EVENTS =====

        btnThem.addActionListener(e -> clearForm());

        btnLuu.addActionListener(e -> {
            PhongHoc ph = getForm();
            if (PhongHocDAO.tonTai(ph.getMaPhong())) {
                JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
                return;
            }
            if (PhongHocDAO.them(ph)) {
                JOptionPane.showMessageDialog(this,"Thêm thành công");
                loadData();
                clearForm();
            }
        });

        btnSua.addActionListener(e -> {
            PhongHoc ph = getForm();
            if (PhongHocDAO.sua(ph)) {
                JOptionPane.showMessageDialog(this,"Sửa thành công");
                loadData();
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ma = model.getValueAt(row,0).toString();
                if (PhongHocDAO.xoa(ma)) {
                    JOptionPane.showMessageDialog(this,"Xóa thành công");
                    loadData();
                }
            }
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn thoát?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMa.setText(model.getValueAt(row,0).toString());
                txtTen.setText(model.getValueAt(row,1).toString());
                cbTinhTrang.setSelectedItem(model.getValueAt(row,2).toString());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<PhongHoc> list = PhongHocDAO.layDanhSach();

        for (PhongHoc ph : list) {
            model.addRow(new Object[]{
                    ph.getMaPhong(),
                    ph.getTenPhong(),
                    ph.getTinhTrang()
            });
        }
    }

    private PhongHoc getForm() {
        return new PhongHoc(
                txtMa.getText().trim(),
                txtTen.getText().trim(),
                cbTinhTrang.getSelectedItem().toString()
        );
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        cbTinhTrang.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new QuanLyPhongHoc().setVisible(true);
    }
}