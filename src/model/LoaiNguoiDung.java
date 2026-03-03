package model;

public class LoaiNguoiDung {
    private String maLoai;
    private String tenLoaiND;

    public LoaiNguoiDung(String maLoai, String tenLoaiND) {
        this.maLoai = maLoai;
        this.tenLoaiND = tenLoaiND;
    }

    public String getMaLoai() { return maLoai; }
    public String getTenLoaiND() { return tenLoaiND; }

    // RẤT QUAN TRỌNG: Hàm này giúp ComboBox hiện Tên (VD: Quản trị viên) thay vì hiện Mã
    @Override
    public String toString() {
        return tenLoaiND; 
    }
}