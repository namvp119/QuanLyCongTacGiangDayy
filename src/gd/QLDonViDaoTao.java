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

    private JButton btnThem, btnLuu, btnSua, btnXoa;

    private boolean isThem = false;
    private boolean isSua = false;

    public QLDonViDaoTao() {
        setTitle("Quản lý đơn vị đào tạo");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadData();
        setFormEnabled(false);
        btnLuu.setEnabled(false);
        addEvents();
    }

    private void initUI() {

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("Thông tin chi tiết đơn vị đào tạo");
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2,2,5,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        txtMa = new JTextField();
        txtTen = new JTextField();

        form.add(new JLabel("Mã đơn vị (*)"));
        form.add(txtMa);
        form.add(new JLabel("Tên đơn vị (*)"));
        form.add(txtTen);

        topPanel.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        btnThem = new JButton("Thêm");  
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");

        buttonPanel.add(btnThem);    
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLuu);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

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

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
    private void setFormEnabled(boolean enabled) {
        txtMa.setEditable(enabled);
        txtTen.setEditable(enabled);
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

    private void addEvents() {

        btnThem.addActionListener(e -> {
            isThem = true;
            isSua = false;
            clearForm();
            setFormEnabled(true);
            txtMa.requestFocus();
            btnLuu.setEnabled(true);
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,"Chọn dòng cần sửa!");
                return;
            }
            isSua = true;
            isThem = false;
            setFormEnabled(true);
            txtMa.setEditable(false);
            btnLuu.setEnabled(true);
        });

        btnLuu.addActionListener(e -> saveData());

        btnXoa.addActionListener(e -> deleteData());


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

        boolean result = false;

        if (isThem) {
            if (DonViDaoTaoDAO.tonTai(dv.getMaDonVi())) {
                JOptionPane.showMessageDialog(this,"Mã đã tồn tại!");
                return;
            }
            result = DonViDaoTaoDAO.them(dv);
        }

        if (isSua) {
            result = DonViDaoTaoDAO.sua(dv);
        }

        if (result) {
            JOptionPane.showMessageDialog(this,"Thành công!");
            loadData();
            clearForm();
            setFormEnabled(false);
            btnLuu.setEnabled(false);
            isThem = false;
            isSua = false;
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
            clearForm();
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