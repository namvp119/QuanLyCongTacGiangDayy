package gd;

import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuanLyMonHoc extends JFrame {

    JTextField txtMa, txtTen, txtSoTC;
    JComboBox<LoaiMonHoc> cboLoai;
    JTable table;
    DefaultTableModel model;
    MonHocDAO dao = new MonHocDAO();

    public QuanLyMonHoc() {

        setTitle("Quản lý môn học");
        setSize(850,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadCombo();
        loadTable();
    }

    private void initUI(){

        JPanel left = new JPanel(new GridLayout(6,2,5,5));

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtSoTC = new JTextField();
        cboLoai = new JComboBox<>();

        left.add(new JLabel("Mã môn học (*)"));
        left.add(txtMa);
        left.add(new JLabel("Tên môn học (*)"));
        left.add(txtTen);
        left.add(new JLabel("Số tín chỉ (*)"));
        left.add(txtSoTC);
        left.add(new JLabel("Loại môn học (*)"));
        left.add(cboLoai);

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLuu = new JButton("Lưu");

        JPanel pBtn = new JPanel();
        pBtn.add(btnThem);
        pBtn.add(btnLuu);
        pBtn.add(btnSua);
        pBtn.add(btnXoa);

        JPanel mainLeft = new JPanel(new BorderLayout());
        mainLeft.add(left,BorderLayout.CENTER);
        mainLeft.add(pBtn,BorderLayout.SOUTH);

        model = new DefaultTableModel(
                new String[]{"Mã MH","Tên MH","Số TC","Loại"},0);

        table = new JTable(model);

        add(mainLeft,BorderLayout.WEST);
        add(new JScrollPane(table),BorderLayout.CENTER);

        // ==== EVENT ====

        btnThem.addActionListener(e -> clearForm());

        btnLuu.addActionListener(e -> saveData());

        btnSua.addActionListener(e -> updateData());

        btnXoa.addActionListener(e -> deleteData());
    }

    private void loadCombo(){
        cboLoai.removeAllItems();
        for(LoaiMonHoc l : LoaiMonHocDAO.getAll())
            cboLoai.addItem(l);
    }

    private void loadTable(){

        model.setRowCount(0);

        List<MonHoc> list = dao.getAll();

        for(MonHoc mh : list){
            model.addRow(new Object[]{
                    mh.getMaMH(),
                    mh.getTenMH(),
                    mh.getSoTC(),
                    mh.getTenLoaiMH()
            });
        }
    }

    private void clearForm(){
        txtMa.setText("");
        txtTen.setText("");
        txtSoTC.setText("");
        cboLoai.setSelectedIndex(0);
    }

    private void saveData(){

        try{
            MonHoc mh = new MonHoc(
                    txtMa.getText(),
                    txtTen.getText(),
                    Integer.parseInt(txtSoTC.getText()),
                    ((LoaiMonHoc)cboLoai.getSelectedItem()).getMaLoaiMH()
            );

            if(dao.insert(mh)){
                loadTable();
                JOptionPane.showMessageDialog(this,"Thêm thành công");
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Lỗi dữ liệu");
        }
    }

    private void updateData(){

        try{
            MonHoc mh = new MonHoc(
                    txtMa.getText(),
                    txtTen.getText(),
                    Integer.parseInt(txtSoTC.getText()),
                    ((LoaiMonHoc)cboLoai.getSelectedItem()).getMaLoaiMH()
            );

            if(dao.update(mh)){
                loadTable();
                JOptionPane.showMessageDialog(this,"Sửa thành công");
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Lỗi dữ liệu");
        }
    }

    private void deleteData(){

        if(dao.delete(txtMa.getText())){
            loadTable();
            JOptionPane.showMessageDialog(this,"Xóa thành công");
        }
    }

    public static void main(String[] args) {
        new QuanLyMonHoc().setVisible(true);
    }
}