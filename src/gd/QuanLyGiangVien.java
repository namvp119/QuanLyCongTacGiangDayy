package gd;

import dao.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Date;

public class QuanLyGiangVien extends JFrame {

    private JTextField txtMa, txtTen, txtQue, txtDiaChi, txtSDT, txtEmail;
    private JComboBox<String> cboGioiTinh, cboTinhTrang;
    private JComboBox<Khoa> cboKhoa;
    private JComboBox<ChucDanh> cboChucDanh;
    private JSpinner spNgaySinh;
    private JTable table;
    private DefaultTableModel model;

    private String mode = ""; 

    private CanBoGiangDayDAO dao = new CanBoGiangDayDAO();
    private KhoaDAO khoaDAO = new KhoaDAO();
    private ChucDanhDAO cdDAO = new ChucDanhDAO();

    public QuanLyGiangVien() {
        setTitle("Quản lý giảng viên");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadCombo();
        loadTable();
    }

    private void initUI() {

        JPanel left = new JPanel(null);
        left.setPreferredSize(new Dimension(500, 0));

        int y = 20;

        txtMa = addField(left, "Mã cán bộ (*)", y); y+=40;
        txtTen = addField(left, "Họ tên (*)", y); y+=40;

        cboGioiTinh = new JComboBox<>(new String[]{"Nam","Nữ"});
        addComponent(left,"Giới tính (*)",cboGioiTinh,y); y+=40;

        spNgaySinh = new JSpinner(new SpinnerDateModel());
        spNgaySinh.setEditor(new JSpinner.DateEditor(spNgaySinh,"yyyy-MM-dd"));
        addComponent(left,"Ngày sinh (*)",spNgaySinh,y); y+=40;

        txtQue = addField(left,"Quê quán (*)",y); y+=40;
        txtDiaChi = addField(left,"Địa chỉ (*)",y); y+=40;
        txtSDT = addField(left,"SĐT (*)",y); y+=40;
        txtEmail = addField(left,"Email (*)",y); y+=40;

        cboTinhTrang = new JComboBox<>(new String[]{"Đang công tác","Nghỉ phép","Đã nghỉ"});
        addComponent(left,"Tình trạng (*)",cboTinhTrang,y); y+=40;

        cboKhoa = new JComboBox<>();
        addComponent(left,"Khoa (*)",cboKhoa,y); y+=40;

        cboChucDanh = new JComboBox<>();
        addComponent(left,"Chức danh (*)",cboChucDanh,y); y+=50;

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLuu = new JButton("Lưu");
        JButton btnThoat = new JButton("Thoát");

        btnThem.setBounds(30,y,80,30);
        btnSua.setBounds(120,y,80,30);
        btnXoa.setBounds(210,y,80,30);
        btnLuu.setBounds(300,y,80,30);
        btnThoat.setBounds(390,y,80,30);

        left.add(btnThem);
        left.add(btnSua);
        left.add(btnXoa);
        left.add(btnLuu);
        left.add(btnThoat);

        btnLuu.setEnabled(false);

        // TABLE
        model = new DefaultTableModel(
                new String[]{"MSCB","Họ tên","Giới tính","Ngày sinh","Khoa","Chức danh"},0);
        table = new JTable(model);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                left,
                new JScrollPane(table)
        );
        split.setDividerLocation(500);
        add(split);

        btnThem.addActionListener(e -> {
            clearForm();
            mode = "ADD";
            btnLuu.setEnabled(true);
        });

        btnSua.addActionListener(e -> {
            mode = "UPDATE";
            btnLuu.setEnabled(true);
        });

        btnLuu.addActionListener(e -> saveData(btnLuu));

        btnXoa.addActionListener(e -> deleteData());

        btnThoat.addActionListener(e -> dispose());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loadFromTable();
            }
        });
    }

    private JTextField addField(JPanel p, String label, int y){
        JLabel lb = new JLabel(label);
        lb.setBounds(20,y,150,25);
        JTextField txt = new JTextField();
        txt.setBounds(170,y,300,25);
        p.add(lb); p.add(txt);
        return txt;
    }

    private void addComponent(JPanel p, String label, JComponent c, int y){
        JLabel lb = new JLabel(label);
        lb.setBounds(20,y,150,25);
        c.setBounds(170,y,300,25);
        p.add(lb); p.add(c);
    }

    private void loadCombo(){

        cboKhoa.removeAllItems();
        for(Khoa k : KhoaDAO.layDanhSachKhoa())
            cboKhoa.addItem(k);

        cboChucDanh.removeAllItems();
        for(ChucDanh cd : cdDAO.getAll())
            cboChucDanh.addItem(cd);
    }

    private void loadTable(){

        model.setRowCount(0);

        List<CanBoGiangDay> list = dao.getAll();

        for(CanBoGiangDay cb : list){
            model.addRow(new Object[]{
                    cb.getMscb(),
                    cb.getHoTen(),
                    cb.getGioiTinh(),
                    cb.getNgaySinh(),
            });
        }
    }

    private void saveData(JButton btnLuu){

        try{
            CanBoGiangDay cb = new CanBoGiangDay();
            cb.setMscb(txtMa.getText());
            cb.setHoTen(txtTen.getText());
            cb.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
            cb.setNgaySinh((Date)spNgaySinh.getValue());
            cb.setQueQuan(txtQue.getText());
            cb.setDiaChi(txtDiaChi.getText());
            cb.setSdt(txtSDT.getText());
            cb.setEmail(txtEmail.getText());
            cb.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
            cb.setMaKhoa(((Khoa)cboKhoa.getSelectedItem()).getMaKhoa());
            cb.setMaCD(((ChucDanh)cboChucDanh.getSelectedItem()).getMaCD());

            boolean result = false;

            if(mode.equals("ADD"))
                result = dao.insert(cb);
            else if(mode.equals("UPDATE"))
                result = dao.update(cb);

            if(result){
                JOptionPane.showMessageDialog(this,"Thành công!");
                loadTable();
                btnLuu.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(this,"Thất bại!");
            }

        }catch(Exception ex){
        	if(txtMa.getText().trim().length() != 6){
        	    JOptionPane.showMessageDialog(this,"Mã cán bộ phải đủ 6 ký tự!");
        	    return;
        	}

        	if(!txtEmail.getText().contains("@")){
        	    JOptionPane.showMessageDialog(this,"Email không hợp lệ!");
        	    return;
        	}

        	if(cboChucDanh.getSelectedItem() == null){
        	    JOptionPane.showMessageDialog(this,"Phải chọn chức danh!");
        	    return;
        	}
        }
    }

    private void deleteData(){
        String ma = txtMa.getText();
        if(ma.isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this,"Xóa?");
        if(confirm==0){
            dao.delete(ma);
            loadTable();
            clearForm();
        }
    }

    private void loadFromTable(){
        int r = table.getSelectedRow();
        if(r==-1) return;

        txtMa.setText(model.getValueAt(r,0).toString());
        txtTen.setText(model.getValueAt(r,1).toString());
    }

    private void clearForm(){
        txtMa.setText("");
        txtTen.setText("");
        txtQue.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    public static void main(String[] args) {
        new QuanLyGiangVien().setVisible(true);
    }
}