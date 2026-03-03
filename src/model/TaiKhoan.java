package model;

public class TaiKhoan {
    private String tenDangNhap; // Tương ứng MaND trong DB
    private String matKhau;     // Tương ứng MatKhau trong DB
    private String maLoai;      // Tương ứng MaLoai trong DB

    public TaiKhoan() {}

    public TaiKhoan(String tenDangNhap, String matKhau, String maLoai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maLoai = maLoai;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getMaLoai() { return maLoai; }
    public void setMaLoai(String maLoai) { this.maLoai = maLoai; }
}	