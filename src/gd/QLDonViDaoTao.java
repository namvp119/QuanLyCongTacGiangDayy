package gd;

import dao.DonViDaoTaoDAO;
import model.DonViDaoTao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class QLDonViDaoTao extends JFrame {

    private JTextField txtMa, txtTen;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnLuu, btnSua, btnXoa, btnThoat;

    private boolean isThem = false;

    public QLDonViDaoTao() {
        setTitle("Quản lý đơn vị đào tạo");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadData();
        addEvents();
    }

    private void initUI() {

        JPanel left = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("Thông tin chi tiết đơn vị đào tạo");
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        left.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2,3,5,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        txtMa = new JTextField();
        txtTen = new JTextField();

        form.add(new JLabel("Mã đơn vị (*)"));
        form.add(new JLabel(""));
        form.add(txtMa);

        form.add(new JLabel("Tên đơn vị (*)"));
        form.add(new JLabel(""));
        form.add(txtTen);

        left.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

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

        // ===== Right Table =====

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã đơn vị", "Tên đơn vị"});

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLUE),
                "Danh sách đơn vị đào tạo"
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
        model.setRowCount(0);

        ArrayList<DonViDaoTao> list = DonViDaoTaoDAO.layDanhSach();

        for (DonViDaoTao dv : list) {
            model.addRow(new Object[]{
                    dv.getMaDonVi(),
                    dv.getTenDonVi()
            });
        }
    }
    private void insertData() {

        if (txtMa.getText().isEmpty() || txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Nhập đầy đủ dữ liệu!");
            return;
        }

        DonViDaoTao dv = new DonViDaoTao(
                txtMa.getText().trim(),
                txtTen.getText().trim()
        );

        if (DonViDaoTaoDAO.tonTai(dv.getMaDonVi())) {
            JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
            return;
        }

        if (DonViDaoTaoDAO.them(dv)) {
            JOptionPane.showMessageDialog(this,"Thêm thành công!");
            loadData();
            clearForm();
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

        if (txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Tên không được rỗng!");
            return;
        }

        DonViDaoTao dv = new DonViDaoTao(
                txtMa.getText().trim(),
                txtTen.getText().trim()
        );

        if (DonViDaoTaoDAO.sua(dv)) {
            JOptionPane.showMessageDialog(this,"Cập nhật thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"Cập nhật thất bại!");
        }
    }

    private void addEvents() {

        // THÊM → chỉ clear form
        btnThem.addActionListener(e -> {
            clearForm();
            txtMa.setEditable(true);
        });

        // LƯU → chỉ dùng cho thêm mới
        btnLuu.addActionListener(e -> insertData());

        // SỬA → update ngay
        btnSua.addActionListener(e -> updateData());

        btnXoa.addActionListener(e -> deleteData());

        btnThoat.addActionListener(e -> System.exit(0));

        table.getSelectionModel().addListSelectionListener(e -> showDetail());
    }
    private void saveData() {

        if (txtMa.getText().isEmpty() || txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Nhập đầy đủ dữ liệu!");
            return;
        }

        DonViDaoTao dv = new DonViDaoTao(
                txtMa.getText().trim(),
                txtTen.getText().trim()
        );

        boolean result;

        if (DonViDaoTaoDAO.tonTai(dv.getMaDonVi())) {
            result = DonViDaoTaoDAO.sua(dv);
        } else {
            result = DonViDaoTaoDAO.them(dv);
        }

        if (result) {
            JOptionPane.showMessageDialog(this,"Thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"Thất bại!");
        }
    }

    private void deleteData() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        String ma = model.getValueAt(row,0).toString();

        if (DonViDaoTaoDAO.xoa(ma)) {
            JOptionPane.showMessageDialog(this,"Đã xóa!");
            loadData();
        }
    }

    private void showDetail() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMa.setText(model.getValueAt(row,0).toString());
        txtTen.setText(model.getValueAt(row,1).toString());
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
    }

    public static void main(String[] args) {
        new QLDonViDaoTao().setVisible(true);
    }
}