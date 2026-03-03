package gd;

import dao.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class QuanLySinhVien extends JFrame {

    JTextField txtMSSV, txtTen, txtQueQuan, txtDiaChi, txtSDT, txtEmail;
    JComboBox<String> cboGioiTinh, cboTinhTrang, cboMaLop;
    JSpinner spNgaySinh;

    JTable table;
    DefaultTableModel model;

    SinhVienDAO dao = new SinhVienDAO();

    private boolean isInsert = false;
    private boolean isEditing = false;
    private JFrame parent;

    private JButton btnLuu;

    public QuanLySinhVien() {

        setTitle("Quản lý sinh viên");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadTable();
        enableForm(false);   
    }

    private void initUI() {

        JPanel left = new JPanel(new GridLayout(11,2,5,5));

        txtMSSV = new JTextField();
        txtTen = new JTextField();
        txtQueQuan = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();

        cboGioiTinh = new JComboBox<>(new String[]{"Nam","Nữ"});
        cboTinhTrang = new JComboBox<>(new String[]{"Đang học","Tạm nghỉ","Tốt nghiệp"});
        cboMaLop = new JComboBox<>();
        loadMaLop();

        spNgaySinh = new JSpinner(new SpinnerDateModel());
        spNgaySinh.setEditor(new JSpinner.DateEditor(spNgaySinh,"dd/MM/yyyy"));

        left.add(new JLabel("MSSV (*)")); left.add(txtMSSV);
        left.add(new JLabel("Họ tên (*)")); left.add(txtTen);
        left.add(new JLabel("Giới tính (*)")); left.add(cboGioiTinh);
        left.add(new JLabel("Ngày sinh (*)")); left.add(spNgaySinh);
        left.add(new JLabel("Quê quán (*)")); left.add(txtQueQuan);
        left.add(new JLabel("Địa chỉ (*)")); left.add(txtDiaChi);
        left.add(new JLabel("SĐT")); left.add(txtSDT);
        left.add(new JLabel("Email")); left.add(txtEmail);
        left.add(new JLabel("Tình trạng (*)")); left.add(cboTinhTrang);
        left.add(new JLabel("Mã lớp (*)")); left.add(cboMaLop);

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        btnLuu = new JButton("Lưu");
        JButton btnXoa = new JButton("Xóa");

        btnLuu.setEnabled(false);

        JPanel pBtn = new JPanel();
        pBtn.add(btnThem);
        pBtn.add(btnSua);
        pBtn.add(btnLuu);
        pBtn.add(btnXoa);

        JPanel mainLeft = new JPanel(new BorderLayout());
        mainLeft.add(left,BorderLayout.CENTER);
        mainLeft.add(pBtn,BorderLayout.SOUTH);

        model = new DefaultTableModel(
                new String[]{"MSSV","Tên","Giới tính","Ngày sinh",
                        "Quê quán","Địa chỉ","SĐT","Email",
                        "Tình trạng","Mã lớp"},0);

        table = new JTable(model);

        add(mainLeft,BorderLayout.WEST);
        add(new JScrollPane(table),BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            clearForm();
            table.clearSelection();

            isInsert = true;
            isEditing = true;

            btnLuu.setEnabled(true);
            enableForm(true);
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row < 0){
                JOptionPane.showMessageDialog(this,"Chọn sinh viên cần sửa");
                return;
            }

            isInsert = false;
            isEditing = true;

            btnLuu.setEnabled(true);
            enableForm(true);
        });

        btnLuu.addActionListener(e -> saveData());

        btnXoa.addActionListener(e -> deleteData());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void enableForm(boolean enable){

        txtMSSV.setEditable(enable && isInsert);
        txtTen.setEditable(enable);
        txtQueQuan.setEditable(enable);
        txtDiaChi.setEditable(enable);
        txtSDT.setEditable(enable);
        txtEmail.setEditable(enable);

        cboGioiTinh.setEnabled(enable);
        cboTinhTrang.setEnabled(enable);
        cboMaLop.setEnabled(enable);
        spNgaySinh.setEnabled(enable);
    }

    private void saveData() {

        if(!isEditing) return;
        if(!validateData()) return;

        SinhVien sv = getFormData();
        boolean result = isInsert ? dao.insert(sv) : dao.update(sv);

        if(result){
            loadTable();
            clearForm();

            isInsert = false;
            isEditing = false;
            btnLuu.setEnabled(false);
            enableForm(false);

            JOptionPane.showMessageDialog(this,"Thành công");
        } else {
            JOptionPane.showMessageDialog(this,"Thất bại");
        }
    }

    private void deleteData() {

        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this,"Chọn sinh viên cần xóa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,"Bạn có chắc muốn xóa?","Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if(confirm == JOptionPane.YES_OPTION){
            if(dao.delete(txtMSSV.getText())){
                loadTable();
                clearForm();
                enableForm(false);
                btnLuu.setEnabled(false);
                isEditing = false;
                JOptionPane.showMessageDialog(this,"Xóa thành công");
            }
        }
    }

    private void loadMaLop(){
        cboMaLop.removeAllItems();
        List<String> list = dao.getAllMaLop();
        for(String ml : list){
            cboMaLop.addItem(ml);
        }
    }

    private void loadTable() {

        model.setRowCount(0);
        List<SinhVien> list = dao.getAll();

        for(SinhVien sv : list) {
            model.addRow(new Object[]{
                    sv.getMssv(),
                    sv.getTenSV(),
                    sv.getGioiTinh(),
                    sv.getNgaySinh(),
                    sv.getQueQuan(),
                    sv.getDiaChi(),
                    sv.getSdt(),
                    sv.getEmail(),
                    sv.getTinhTrang(),
                    sv.getMaLop()
            });
        }
    }

    private void fillForm() {

        int row = table.getSelectedRow();
        if(row < 0) return;

        txtMSSV.setText(model.getValueAt(row,0).toString());
        txtTen.setText(model.getValueAt(row,1).toString());
        cboGioiTinh.setSelectedItem(model.getValueAt(row,2).toString());
        spNgaySinh.setValue((Date) model.getValueAt(row,3));
        txtQueQuan.setText(model.getValueAt(row,4).toString());
        txtDiaChi.setText(model.getValueAt(row,5).toString());
        txtSDT.setText(model.getValueAt(row,6).toString());
        txtEmail.setText(model.getValueAt(row,7).toString());
        cboTinhTrang.setSelectedItem(model.getValueAt(row,8).toString());
        cboMaLop.setSelectedItem(model.getValueAt(row,9).toString());
    }

    private SinhVien getFormData() {
        return new SinhVien(
                txtMSSV.getText().trim(),
                txtTen.getText().trim(),
                cboGioiTinh.getSelectedItem().toString(),
                (Date) spNgaySinh.getValue(),
                txtQueQuan.getText().trim(),
                txtDiaChi.getText().trim(),
                txtSDT.getText().trim(),
                txtEmail.getText().trim(),
                cboTinhTrang.getSelectedItem().toString(),
                cboMaLop.getSelectedItem().toString()
        );
    }

    private boolean validateData() {

        if(txtMSSV.getText().trim().length() != 6){
            JOptionPane.showMessageDialog(this,"MSSV phải đủ 6 ký tự");
            return false;
        }

        if(txtTen.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Tên không được rỗng");
            return false;
        }

        return true;
    }

    private void clearForm(){
        txtMSSV.setText("");
        txtTen.setText("");
        txtQueQuan.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    public static void main(String[] args) {
        new QuanLySinhVien().setVisible(true);
    }
}