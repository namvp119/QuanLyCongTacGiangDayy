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

    JButton btnThem, btnSua, btnXoa, btnLuu;

    String cheDo = "";

    public QuanLyMonHoc() {

        setTitle("Quản lý môn học");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadCombo();
        loadTable();

        setFormEnabled(false);
        btnLuu.setEnabled(false);
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

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");

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

        setLayout(new BorderLayout());

        add(mainLeft, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel mainleft = new JPanel(new GridLayout(4,2,10,10));
        left.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JLabel lblTitle = new JLabel("THÔNG TIN MÔN HỌC");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(left, BorderLayout.CENTER);
        topPanel.add(pBtn, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            cheDo = "THEM";
            clearForm();
            setFormEnabled(true);
            btnLuu.setEnabled(true);
            txtMa.setEditable(true);
        });

        btnLuu.addActionListener(e -> {

            if(cheDo.equals("")) return;

            try{
                MonHoc mh = new MonHoc(
                        txtMa.getText(),
                        txtTen.getText(),
                        Integer.parseInt(txtSoTC.getText()),
                        ((LoaiMonHoc)cboLoai.getSelectedItem()).getMaLoaiMH()
                );

                if(cheDo.equals("THEM")){
                    if(dao.insert(mh)){
                        JOptionPane.showMessageDialog(this,"Thêm thành công");
                    }
                }else if(cheDo.equals("SUA")){
                    if(dao.update(mh)){
                        JOptionPane.showMessageDialog(this,"Sửa thành công");
                    }
                }

                loadTable();
                clearForm();
                setFormEnabled(false);
                btnLuu.setEnabled(false);
                cheDo = "";

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Lỗi dữ liệu");
            }
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0){
                cheDo = "SUA";
                setFormEnabled(true);
                btnLuu.setEnabled(true);
                txtMa.setEditable(false);
            }
        });

        btnXoa.addActionListener(e -> {
            if(dao.delete(txtMa.getText())){
                loadTable();
                JOptionPane.showMessageDialog(this,"Xóa thành công");
            }
        });


        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0){
                txtMa.setText(model.getValueAt(row,0).toString());
                txtTen.setText(model.getValueAt(row,1).toString());
                txtSoTC.setText(model.getValueAt(row,2).toString());
            }
        });
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

    private void setFormEnabled(boolean enabled){
        txtMa.setEnabled(enabled);
        txtTen.setEnabled(enabled);
        txtSoTC.setEnabled(enabled);
        cboLoai.setEnabled(enabled);
    }

    public static void main(String[] args) {
        new QuanLyMonHoc().setVisible(true);
    }
}