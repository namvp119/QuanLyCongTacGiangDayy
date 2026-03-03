package gd;

import dao.HocKyDAO;
import model.HocKy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QuanLyHocKy extends JFrame {

    private JTextField txtMa, txtTen, txtNam;
    private JComboBox<String> cbTinhTrang;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnLuu, btnSua, btnXoa, btnThoat;

    private String cheDo = "";

    public QuanLyHocKy() {

        setTitle("Quản lý học kỳ");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết học kỳ"));
        leftPanel.setPreferredSize(new Dimension(400,0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMa = new JTextField(15);
        txtTen = new JTextField(15);
        txtNam = new JTextField(15);
        cbTinhTrang = new JComboBox<>(new String[]{"Đang diễn ra", "Đã kết thúc"});

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã học kỳ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMa, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên học kỳ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Năm học:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNam, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cbTinhTrang, gbc);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        Dimension btnSize = new Dimension(90,35);

        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnThoat = new JButton("Thoát");

        btnThem.setPreferredSize(btnSize);
        btnLuu.setPreferredSize(btnSize);
        btnSua.setPreferredSize(btnSize);
        btnXoa.setPreferredSize(btnSize);
        btnThoat.setPreferredSize(btnSize);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnThoat);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        model = new DefaultTableModel(
                new String[]{"Mã HK","Tên HK","Năm học","Tình trạng"},0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách học kỳ"));
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

            if(cheDo.equals("")) return;

            HocKy hk = getForm();

            if(cheDo.equals("THEM")){
                if (HocKyDAO.them(hk)) {
                    JOptionPane.showMessageDialog(this,"Thêm thành công");
                }
            } else if(cheDo.equals("SUA")){
                if (HocKyDAO.sua(hk)) {
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
                String ma = model.getValueAt(row,0).toString();
                if (HocKyDAO.xoa(ma)) {
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
                txtNam.setText(model.getValueAt(row,2).toString());
                cbTinhTrang.setSelectedItem(model.getValueAt(row,3).toString());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<HocKy> list = HocKyDAO.layDanhSach();
        for (HocKy hk : list) {
            model.addRow(new Object[]{
                    hk.getMaHocKy(),
                    hk.getTenHK(),
                    hk.getNamHoc(),
                    hk.getTinhTrang()
            });
        }
    }

    private HocKy getForm() {
        return new HocKy(
                txtMa.getText().trim(),
                txtTen.getText().trim(),
                txtNam.getText().trim(),
                cbTinhTrang.getSelectedItem().toString()
        );
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtNam.setText("");
        cbTinhTrang.setSelectedIndex(0);
    }

    private void setFormEnabled(boolean enabled){
        txtMa.setEnabled(enabled);
        txtTen.setEnabled(enabled);
        txtNam.setEnabled(enabled);
        cbTinhTrang.setEnabled(enabled);
    }

    public static void main(String[] args) {
        new QuanLyHocKy().setVisible(true);
    }
}