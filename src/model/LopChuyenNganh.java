package model;

public class LopChuyenNganh {

    private String maLop;
    private String tenLop;
    private String nganh;
    private String tinhTrang;
    private String maDonVi;
    private String maHe;
    private String maCB;
    private String maKhoa;
    private int soLuongSV;
    private String tenDonVi;
    private String tenHe;
    private String tenCB;
    private String tenKhoa;

    public LopChuyenNganh() {}

    public LopChuyenNganh(String maLop, String tenLop, String nganh,
                          String tinhTrang, String maDonVi,
                          String maHe, String maCB,
                          String maKhoa, int soLuongSV) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.nganh = nganh;
        this.tinhTrang = tinhTrang;
        this.maDonVi = maDonVi;
        this.maHe = maHe;
        this.maCB = maCB;
        this.maKhoa = maKhoa;
        this.soLuongSV = soLuongSV;
    }

    // getter setter đầy đủ

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }

    public String getNganh() { return nganh; }
    public void setNganh(String nganh) { this.nganh = nganh; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getMaDonVi() { return maDonVi; }
    public void setMaDonVi(String maDonVi) { this.maDonVi = maDonVi; }

    public String getMaHe() { return maHe; }
    public void setMaHe(String maHe) { this.maHe = maHe; }

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getMaKhoa() { return maKhoa; }
    public void setMaKhoa(String maKhoa) { this.maKhoa = maKhoa; }

    public int getSoLuongSV() { return soLuongSV; }
    public void setSoLuongSV(int soLuongSV) { this.soLuongSV = soLuongSV; }

    public String getTenDonVi() { return tenDonVi; }
    public void setTenDonVi(String tenDonVi) { this.tenDonVi = tenDonVi; }

    public String getTenHe() { return tenHe; }
    public void setTenHe(String tenHe) { this.tenHe = tenHe; }

    public String getTenCB() { return tenCB; }
    public void setTenCB(String tenCB) { this.tenCB = tenCB; }

    public String getTenKhoa() { return tenKhoa; }
    public void setTenKhoa(String tenKhoa) { this.tenKhoa = tenKhoa; }
}