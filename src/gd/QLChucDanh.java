package gd;

import dao.ChucDanhDAO;
import model.ChucDanh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QLChucDanh extends JFrame {

    private JTextField txtMa, txtTen, txtDonGia;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnSua, btnXoa, btnLuu, btnThoat;
    private JPanel panelForm;

    private ChucDanhDAO dao = new ChucDanhDAO();
    private boolean isAdding = false;

    public QLChucDanh() {
        setTitle("QUẢN LÝ CHỨC DANH");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadData();
        setFormVisible(false);
    }

    private void initUI() {

        panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin chức danh"));

        panelForm.add(new JLabel("Mã CD:"));
        txtMa = new JTextField();
        panelForm.add(txtMa);

        panelForm.add(new JLabel("Tên CD:"));
        txtTen = new JTextField();
        panelForm.add(txtTen);

        panelForm.add(new JLabel("Đơn giá tiết:"));
        txtDonGia = new JTextField();
        panelForm.add(txtDonGia);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"Mã CD", "Tên CD", "Đơn giá tiết"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnThoat = new JButton("Thoát");

        panelBottom.add(btnThem);
        panelBottom.add(btnSua);
        panelBottom.add(btnXoa);
        panelBottom.add(btnLuu);
        panelBottom.add(btnThoat);

        add(panelBottom, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> {
            clearForm();
            isAdding = true;
            txtMa.setEditable(true);
            setFormVisible(true);
        });

        btnSua.addActionListener(e -> {
            if (table.getSelectedRow() >= 0) {
                isAdding = false;
                txtMa.setEditable(false);
                setFormVisible(true);
            }
        });

        btnXoa.addActionListener(e -> delete());
        btnLuu.addActionListener(e -> save());
        btnThoat.addActionListener(e -> dispose());

        table.getSelectionModel().addListSelectionListener(e -> showDetail());
    }

    private void loadData() {
        model.setRowCount(0);
        List<ChucDanh> list = dao.getAll();

        for (ChucDanh cd : list) {
            model.addRow(new Object[]{
                    cd.getMaCD(),
                    cd.getTenCD(),
                    cd.getDonGiaTiet()
            });
        }
    }

    private void showDetail() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMa.setText(model.getValueAt(row, 0).toString());
            txtTen.setText(model.getValueAt(row, 1).toString());
            txtDonGia.setText(model.getValueAt(row, 2).toString());
        }
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtDonGia.setText("");
    }

    private void setFormVisible(boolean visible) {
        panelForm.setVisible(visible);
        btnLuu.setVisible(visible);
        revalidate();
        repaint();
    }

    private void save() {
        try {
            String ma = txtMa.getText();
            String ten = txtTen.getText();
            int gia = Integer.parseInt(txtDonGia.getText());

            ChucDanh cd = new ChucDanh(ma, ten, gia);
            boolean result;

            if (isAdding) {
                result = dao.insert(cd);
            } else {
                result = dao.update(cd);
            }

            if (result) {
                JOptionPane.showMessageDialog(this, "Thành công!");
                loadData();
                setFormVisible(false);
                txtMa.setEditable(true);
            } else {
                JOptionPane.showMessageDialog(this, "Thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
        }
    }

    private void delete() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String ma = model.getValueAt(row, 0).toString();
            if (dao.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Đã xóa!");
                loadData();
            }
        }
    }

    public static void main(String[] args) {
        new QLChucDanh().setVisible(true);
    }
}