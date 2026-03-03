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

    private String mode = "";

    public QLHeDaoTao() {
        setTitle("Quản lý hệ đào tạo");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadData();
        addEvents();
        setDefaultState();
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
            for (HeDaoTao hdt : list) {
                model.addRow(new Object[]{
                        hdt.getMaHDT(),
                        hdt.getTenHDT()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultState() {
        txtMa.setText("");
        txtTen.setText("");
        txtMa.setEditable(false);
        txtTen.setEditable(false);
        btnLuu.setEnabled(false);
        mode = "";
    }

    private void setAddState() {
        txtMa.setText("");
        txtTen.setText("");
        txtMa.setEditable(true);
        txtTen.setEditable(true);
        btnLuu.setEnabled(true);
        mode = "ADD";
        txtMa.requestFocus();
    }

    private void setEditState() {
        txtMa.setEditable(false);
        txtTen.setEditable(true);
        btnLuu.setEnabled(true);
        mode = "EDIT";
        txtTen.requestFocus();
    }

    private void addEvents() {

        btnThem.addActionListener(e -> setAddState());

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,"Chọn dòng cần sửa!");
                return;
            }
            setEditState();
        });

        btnLuu.addActionListener(e -> saveData());

        btnXoa.addActionListener(e -> deleteData());

        btnThoat.addActionListener(e -> System.exit(0));

        table.getSelectionModel().addListSelectionListener(e -> showDetail());
    }

    private void saveData() {

        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Nhập đầy đủ dữ liệu!");
            return;
        }

        HeDaoTao hdt = new HeDaoTao(ma, ten);
        boolean result = false;

        if (mode.equals("ADD")) {

            if (HeDaoTaoDAO.tonTai(ma)) {
                JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
                return;
            }

            result = HeDaoTaoDAO.them(hdt);

        } else if (mode.equals("EDIT")) {
            result = HeDaoTaoDAO.sua(hdt);
        }

        if (result) {
            JOptionPane.showMessageDialog(this,"Thành công!");
            loadData();
            setDefaultState();
        } else {
            JOptionPane.showMessageDialog(this,"Thất bại!");
        }
    }

    private void deleteData() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String ma = model.getValueAt(row,0).toString();

        if (HeDaoTaoDAO.xoa(ma)) {
            JOptionPane.showMessageDialog(this,"Đã xóa!");
            loadData();
            setDefaultState();
        }
    }

    private void showDetail() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMa.setText(model.getValueAt(row,0).toString());
        txtTen.setText(model.getValueAt(row,1).toString());
    }

    public static void main(String[] args) {
        new QLHeDaoTao().setVisible(true);
    }
}