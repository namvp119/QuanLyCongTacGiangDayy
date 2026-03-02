package gd;

import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuanLyLopChuyenNganh extends JFrame {

    JTextField txtMa, txtTen, txtNganh, txtSoLuong;
    JComboBox<String> cboTinhTrang;
    JComboBox<DonViDaoTao> cboDonVi;
    JComboBox<HeDaoTao> cboHe;
    JComboBox<Khoa> cboKhoa;
    JComboBox<CanBoGiangDay> cboCanBo;

    JTable table;
    DefaultTableModel model;

    LopChuyenNganhDAO dao = new LopChuyenNganhDAO();

    boolean isInsert = false;

    public QuanLyLopChuyenNganh(){

        setTitle("Quản lý lớp chuyên ngành");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadCombo();
        loadTable();
        setButtonState(true);
    }

    private void initUI(){

        JPanel left = new JPanel(new GridLayout(11,2,5,5));

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtNganh = new JTextField();
        txtSoLuong = new JTextField();

        cboTinhTrang = new JComboBox<>(new String[]{"Đang mở","Kết thúc"});
        cboDonVi = new JComboBox<>();
        cboHe = new JComboBox<>();
        cboKhoa = new JComboBox<>();
        cboCanBo = new JComboBox<>();

        left.add(new JLabel("Mã lớp (*)")); left.add(txtMa);
        left.add(new JLabel("Tên lớp (*)")); left.add(txtTen);
        left.add(new JLabel("Ngành (*)")); left.add(txtNganh);
        left.add(new JLabel("Tình trạng (*)")); left.add(cboTinhTrang);
        left.add(new JLabel("Đơn vị đào tạo (*)")); left.add(cboDonVi);
        left.add(new JLabel("Hệ đào tạo (*)")); left.add(cboHe);
        left.add(new JLabel("Khoa (*)")); left.add(cboKhoa);
        left.add(new JLabel("Cán bộ (*)")); left.add(cboCanBo);
        left.add(new JLabel("Số lượng SV (*)")); left.add(txtSoLuong);

        JButton btnThem = new JButton("Thêm");
        JButton btnLuu = new JButton("Lưu");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnThoat = new JButton("Thoát");

        JPanel pBtn = new JPanel();
        pBtn.add(btnThem);
        pBtn.add(btnLuu);
        pBtn.add(btnSua);
        pBtn.add(btnXoa);
        pBtn.add(btnThoat);

        JPanel mainLeft = new JPanel(new BorderLayout());
        mainLeft.add(left,BorderLayout.CENTER);
        mainLeft.add(pBtn,BorderLayout.SOUTH);

        model = new DefaultTableModel(
                new String[]{"Mã lớp","Tên lớp","Ngành",
                        "Tình trạng","Đơn vị","Hệ","Khoa","Cán bộ","Số SV"},0);

        table = new JTable(model);

        add(mainLeft,BorderLayout.WEST);
        add(new JScrollPane(table),BorderLayout.CENTER);

        // ====== EVENTS ======

        btnThem.addActionListener(e -> {
            clearForm();
            isInsert = true;
            setButtonState(false);
        });

        btnLuu.addActionListener(e -> saveData());

        btnSua.addActionListener(e -> {
            isInsert = false;
            setButtonState(false);
        });

        btnXoa.addActionListener(e -> deleteData());

        btnThoat.addActionListener(e -> dispose());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }


    private void loadCombo(){

        cboDonVi.removeAllItems();
        for(DonViDaoTao dv : DonViDaoTaoDAO.layDanhSach())
            cboDonVi.addItem(dv);

        cboCanBo.removeAllItems();
        CanBoGiangDayDAO cbDAO = new CanBoGiangDayDAO();
        for(CanBoGiangDay cb : cbDAO.getAll())
            cboCanBo.addItem(cb);
    }
    private void loadTable(){

        model.setRowCount(0);

        List<LopChuyenNganh> list = dao.getAll();

        for(LopChuyenNganh l : list){
            model.addRow(new Object[]{
                    l.getMaLop(),
                    l.getTenLop(),
                    l.getNganh(),
                    l.getTinhTrang(),
                    l.getTenDonVi(),
                    l.getTenHe(),
                    l.getTenKhoa(),
                    l.getTenCB(),
                    l.getSoLuongSV()
            });
        }
    }

    private void fillForm(){

        int row = table.getSelectedRow();
        if(row < 0) return;

        txtMa.setText(model.getValueAt(row,0).toString());
        txtTen.setText(model.getValueAt(row,1).toString());
        txtNganh.setText(model.getValueAt(row,2).toString());
        cboTinhTrang.setSelectedItem(model.getValueAt(row,3).toString());
        txtSoLuong.setText(model.getValueAt(row,8).toString());
    }

    private void saveData(){

        if(!validateData()) return;

        LopChuyenNganh l = new LopChuyenNganh(
                txtMa.getText(),
                txtTen.getText(),
                txtNganh.getText(),
                cboTinhTrang.getSelectedItem().toString(),
                ((DonViDaoTao)cboDonVi.getSelectedItem()).getMaDonVi(),
                ((HeDaoTao)cboHe.getSelectedItem()).getMaHDT(),
                ((CanBoGiangDay)cboCanBo.getSelectedItem()).getMscb(),
                ((Khoa)cboKhoa.getSelectedItem()).getMaKhoa(),
                Integer.parseInt(txtSoLuong.getText())
        );

        boolean result = isInsert ? dao.insert(l) : dao.update(l);

        if(result){
            loadTable();
            JOptionPane.showMessageDialog(this,"Thành công");
            setButtonState(true);
        }
    }

    private void deleteData(){

        if(dao.delete(txtMa.getText())){
            loadTable();
            JOptionPane.showMessageDialog(this,"Xóa thành công");
        }
    }

    private boolean validateData(){

        if(txtMa.getText().trim().length() != 6){
            JOptionPane.showMessageDialog(this,"Mã lớp phải đủ 6 ký tự");
            return false;
        }

        if(txtTen.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Tên lớp không được rỗng");
            return false;
        }

        try{
            Integer.parseInt(txtSoLuong.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Số lượng phải là số");
            return false;
        }

        return true;
    }

    private void clearForm(){
        txtMa.setText("");
        txtTen.setText("");
        txtNganh.setText("");
        txtSoLuong.setText("");
    }

    private void setButtonState(boolean normal){
        // normal = true → trạng thái ban đầu
        // normal = false → đang thêm/sửa
    }

    public static void main(String[] args) {
        new QuanLyLopChuyenNganh().setVisible(true);
    }
}