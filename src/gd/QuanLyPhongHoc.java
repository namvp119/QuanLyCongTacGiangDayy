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

    private JButton btnThem, btnLuu, btnSua, btnXoa;

    private String cheDo = ""; 
    private JFrame parent;

    public QuanLyPhongHoc() {

        setTitle("Quản lý phòng học");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        Dimension size = new Dimension(90,35);

        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        btnThem.setPreferredSize(size);
        btnLuu.setPreferredSize(size);
        btnSua.setPreferredSize(size);
        btnXoa.setPreferredSize(size);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        model = new DefaultTableModel(
                new String[]{"Mã phòng","Tên phòng","Tình trạng"},0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phòng"));
        add(scroll, BorderLayout.CENTER);

        loadData();
        setFormEnabled(false);
        btnLuu.setEnabled(false);

        btnThem.addActionListener(e -> {
            cheDo = "THEM";
            clearForm();
            setFormEnabled(true);
            btnLuu.setEnabled(true);
            txtMa.setEditable(true);
        });

        btnLuu.addActionListener(e -> {

            if (cheDo.equals("")) return;

            PhongHoc ph = getForm();

            if (cheDo.equals("THEM")) {

                if (PhongHocDAO.tonTai(ph.getMaPhong())) {
                    JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
                    return;
                }
                if(txtMa.getText().isEmpty() || txtTen.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Nhập đầy đủ dữ liệu!");
                    return;
                }

                if (PhongHocDAO.them(ph)) {
                    JOptionPane.showMessageDialog(this,"Thêm thành công");
                }

            } else if (cheDo.equals("SUA")) {

                if (PhongHocDAO.sua(ph)) {
                    JOptionPane.showMessageDialog(this,"Sửa thành công");
                }
            }

            loadData();
            clearForm();
            setFormEnabled(false);
            btnLuu.setEnabled(false);
            cheDo = "";
            
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                cheDo = "SUA";
                setFormEnabled(true);
                btnLuu.setEnabled(true);
                txtMa.setEditable(false);
            }
        });
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    String ma = model.getValueAt(row,0).toString();
                    if (PhongHocDAO.xoa(ma)) {
                        JOptionPane.showMessageDialog(this,"Xóa thành công");
                        loadData();
                        clearForm();
                    }
                }
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

    private void setFormEnabled(boolean enabled) {
        txtMa.setEnabled(enabled);
        txtTen.setEnabled(enabled);
        cbTinhTrang.setEnabled(enabled);
    }

    public static void main(String[] args) {
        new QuanLyPhongHoc().setVisible(true);
    }
}