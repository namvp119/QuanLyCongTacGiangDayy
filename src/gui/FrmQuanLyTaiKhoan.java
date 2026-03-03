package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.TaiKhoanDAO;
import dao.LoaiNguoiDungDAO;
import model.TaiKhoan;
import model.LoaiNguoiDung;

public class FrmQuanLyTaiKhoan extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTaiKhoan;
    private JTextField txtMatKhau;
    private JTextField txtTimKiem; // Thêm biến cho ô tìm kiếm
    
    private JComboBox<LoaiNguoiDung> cboQuyen; 
    
    private JTable tblTaiKhoan;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> rowSorter; // Bộ lọc tìm kiếm

    // Khai báo các DAO xử lý dữ liệu
    private TaiKhoanDAO tkDAO = new TaiKhoanDAO();
    private LoaiNguoiDungDAO loaiDAO = new LoaiNguoiDungDAO();

    public FrmQuanLyTaiKhoan() {
        setTitle("Hệ Thống Quản Lý Công Tác Giảng Dạy - Quản Lý & Phân Quyền Tài Khoản");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 720, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTieuDe = new JLabel("CẤP PHÁT TÀI KHOẢN & PHÂN QUYỀN");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTieuDe.setForeground(new Color(0, 102, 204));
        lblTieuDe.setBounds(180, 15, 400, 30);
        contentPane.add(lblTieuDe);

        // --- KHU VỰC NHẬP LIỆU ---
        JPanel pnlInput = new JPanel();
        pnlInput.setBorder(new TitledBorder("Thông tin tài khoản"));
        pnlInput.setBounds(20, 60, 660, 110);
        pnlInput.setLayout(null);
        contentPane.add(pnlInput);

        JLabel lblTaiKhoan = new JLabel("Tên đăng nhập:");
        lblTaiKhoan.setBounds(20, 30, 100, 25);
        pnlInput.add(lblTaiKhoan);
        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(120, 30, 180, 25);
        pnlInput.add(txtTaiKhoan);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(340, 30, 80, 25);
        pnlInput.add(lblMatKhau);
        txtMatKhau = new JTextField("123"); 
        txtMatKhau.setBounds(420, 30, 210, 25);
        pnlInput.add(txtMatKhau);

        JLabel lblQuyen = new JLabel("Loại quyền:");
        lblQuyen.setBounds(20, 70, 100, 25);
        pnlInput.add(lblQuyen);
        
        cboQuyen = new JComboBox<>();
        cboQuyen.setBounds(120, 70, 220, 25);
        pnlInput.add(cboQuyen);

        // --- KHU VỰC NÚT BẤM & TÌM KIẾM ---
        // Đã canh lại tọa độ để nhường chỗ cho thanh tìm kiếm
        JButton btnThem = new JButton("Cấp Tài Khoản");
        btnThem.setBounds(20, 190, 130, 35);
        btnThem.setBackground(new Color(46, 139, 87));
        btnThem.setForeground(Color.WHITE);
        contentPane.add(btnThem);

        JButton btnXoa = new JButton("Thu Hồi (Xóa)");
        btnXoa.setBounds(160, 190, 120, 35);
        btnXoa.setBackground(new Color(220, 20, 60));
        btnXoa.setForeground(Color.WHITE);
        contentPane.add(btnXoa);

        JButton btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setBounds(290, 190, 100, 35);
        contentPane.add(btnLamMoi);

        // Thêm thanh Tìm kiếm
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTimKiem.setBounds(410, 197, 70, 20);
        contentPane.add(lblTimKiem);

        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(480, 190, 200, 35);
        txtTimKiem.setToolTipText("Nhập tên đăng nhập hoặc quyền...");
        contentPane.add(txtTimKiem);

        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 240, 660, 190);
        contentPane.add(scrollPane);

        String[] cols = {"Tên Đăng Nhập", "Mật Khẩu", "Quyền Hạn (Mã)"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblTaiKhoan = new JTable(model);
        
        // Gắn bộ lọc tìm kiếm cho Bảng
        rowSorter = new TableRowSorter<>(model);
        tblTaiKhoan.setRowSorter(rowSorter);
        scrollPane.setViewportView(tblTaiKhoan);

        loadData();
        loadComboBoxLoaiND();

        // --- SỰ KIỆN GÕ BÀN PHÍM TÌM KIẾM REAL-TIME ---
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null); 
                } else {
                    // (?i) để tìm kiếm không phân biệt hoa thường
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // --- SỰ KIỆN CLICK BẢNG ---
        tblTaiKhoan.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = tblTaiKhoan.getSelectedRow();
                if (r >= 0) {
                    // Chuyển đổi Index hiển thị sang Index thật của Model khi dùng Sorter
                    int modelRow = tblTaiKhoan.convertRowIndexToModel(r);
                    
                    txtTaiKhoan.setText(model.getValueAt(modelRow, 0).toString());
                    txtMatKhau.setText(model.getValueAt(modelRow, 1).toString());
                    
                    String maLoaiTable = model.getValueAt(modelRow, 2).toString();
                    for (int i = 0; i < cboQuyen.getItemCount(); i++) {
                        LoaiNguoiDung l = cboQuyen.getItemAt(i);
                        if (l.getMaLoai().equals(maLoaiTable)) {
                            cboQuyen.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtTaiKhoan.setEditable(false);
                }
            }
        });

        btnLamMoi.addActionListener(e -> {
            txtTaiKhoan.setText("");
            txtMatKhau.setText("123");
            txtTimKiem.setText(""); // Reset thanh tìm kiếm
            rowSorter.setRowFilter(null); // Trả bảng về ban đầu
            
            if(cboQuyen.getItemCount() > 0) cboQuyen.setSelectedIndex(0);
            txtTaiKhoan.setEditable(true);
            txtTaiKhoan.requestFocus();
            tblTaiKhoan.clearSelection();
        });

        btnThem.addActionListener(e -> themTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());
    }

    private void loadComboBoxLoaiND() {
        cboQuyen.removeAllItems();
        List<LoaiNguoiDung> ds = loaiDAO.getAll();
        for (LoaiNguoiDung l : ds) {
            cboQuyen.addItem(l);
        }
    }

    private void loadData() {
        model.setRowCount(0);
        List<TaiKhoan> ds = tkDAO.getAllTaiKhoan();
        for (TaiKhoan tk : ds) {
            model.addRow(new Object[]{
                tk.getTenDangNhap(),
                "******", 
                tk.getMaLoai()
            });
        }
    }

    private void themTaiKhoan() {
        if (txtTaiKhoan.getText().trim().isEmpty() || txtMatKhau.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Tài khoản và Mật khẩu!");
            return;
        }

        LoaiNguoiDung loaiSelected = (LoaiNguoiDung) cboQuyen.getSelectedItem();
        TaiKhoan tk = new TaiKhoan(
            txtTaiKhoan.getText().trim(),
            txtMatKhau.getText().trim(),
            loaiSelected.getMaLoai()
        );
        
        if (tkDAO.themTaiKhoan(tk)) {
            JOptionPane.showMessageDialog(this, "Đã cấp tài khoản thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi: Không có mã cán bộ nào.");
        }
    }

    private void xoaTaiKhoan() {
        int r = tblTaiKhoan.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!");
            return;
        }
        
        String user = txtTaiKhoan.getText();
        if (user.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản Quản trị viên gốc!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thu hồi tài khoản: " + user + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (tkDAO.xoaTaiKhoan(user)) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thực hiện xóa!");
            }
        }
    }

    public static void main(String[] args) {
        new FrmQuanLyTaiKhoan().setVisible(true);
    }
}