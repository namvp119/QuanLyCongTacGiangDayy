package gd;

import dao.HeDaoTaoDAO;
import model.HeDaoTao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QLHeDaoTao extends JFrame {

    private JTextField txtMa, txtTen;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnLuu, btnSua, btnXoa, btnThoat;

    public QLHeDaoTao() {
        setTitle("Quản lý hệ đào tạo");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadData();
        addEvents();
    }

    private void initUI() {

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(new Color(236,226,214));

        JLabel lblTitle = new JLabel("Thông tin chi tiết hệ đào tạo");
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        left.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2,2,5,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.setBackground(new Color(236,226,214));

        txtMa = new JTextField();
        txtTen = new JTextField();

        form.add(new JLabel("Mã hệ đào tạo (*)"));
        form.add(txtMa);
        form.add(new JLabel("Tên hệ đào tạo (*)"));
        form.add(txtTen);

        left.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236,226,214));

        btnThem = new JButton("Thêm");
        btnLuu = new JButton("Lưu");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnThoat = new JButton("Thoát");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnThoat);

        left.add(buttonPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã hệ", "Tên hệ"});
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLUE),
                "Danh sách hệ đào tạo"
        );
        border.setTitleColor(Color.BLUE);
        scroll.setBorder(border);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                left,
                scroll
        );
        split.setDividerLocation(350);

        add(split);
    }

    private void loadData() {

        try {
            model.setRowCount(0);

            ArrayList<HeDaoTao> list = HeDaoTaoDAO.layDanhSach();

            System.out.println("Số dòng lấy được: " + list.size());

            for (HeDaoTao hdt : list) {
                model.addRow(new Object[]{
                        hdt.getMaHDT(),
                        hdt.getTenHDT()
                });
            }

            model.fireTableDataChanged();   // đảm bảo table refresh

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addEvents() {

        btnThem.addActionListener(e -> {
            txtMa.setText("");
            txtTen.setText("");
            txtMa.setEditable(true);
        });

        btnLuu.addActionListener(e -> insertData());

        btnSua.addActionListener(e -> updateData());

        btnXoa.addActionListener(e -> deleteData());

        btnThoat.addActionListener(e -> System.exit(0));

        table.getSelectionModel().addListSelectionListener(e -> showDetail());
    }

    private void insertData() {

        if (txtMa.getText().isEmpty() || txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Nhập đầy đủ dữ liệu!");
            return;
        }

        HeDaoTao hdt = new HeDaoTao(
                txtMa.getText().trim(),
                txtTen.getText().trim()
        );

        if (HeDaoTaoDAO.tonTai(hdt.getMaHDT())) {
            JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
            return;
        }

        if (HeDaoTaoDAO.them(hdt)) {
            JOptionPane.showMessageDialog(this,"Thêm thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"Thêm thất bại!");
        }
    }

    private void updateData() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,"Chọn dòng cần sửa!");
            return;
        }

        HeDaoTao hdt = new HeDaoTao(
                txtMa.getText().trim(),
                txtTen.getText().trim()
        );

        if (HeDaoTaoDAO.sua(hdt)) {
            JOptionPane.showMessageDialog(this,"Cập nhật thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"Cập nhật thất bại!");
        }
    }

    private void deleteData() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        String ma = model.getValueAt(row,0).toString();

        if (HeDaoTaoDAO.xoa(ma)) {
            JOptionPane.showMessageDialog(this,"Đã xóa!");
            loadData();
        }
    }

    private void showDetail() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMa.setText(model.getValueAt(row,0).toString());
        txtTen.setText(model.getValueAt(row,1).toString());
        txtMa.setEditable(false);
    }

    public static void main(String[] args) {
        new QLHeDaoTao().setVisible(true);
    }
}