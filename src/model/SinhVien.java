package model;

import java.util.Date;

public class SinhVien {

    private String mssv;
    private String tenSV;
    private String gioiTinh;
    private Date ngaySinh;
    private String queQuan;
    private String diaChi;
    private String sdt;
    private String email;
    private String tinhTrang;
    private String maLop;

    public SinhVien() {}

    public SinhVien(String mssv, String tenSV, String gioiTinh, Date ngaySinh,
                     String queQuan, String diaChi, String sdt,
                     String email, String tinhTrang, String maLop) {
        this.mssv = mssv;
        this.tenSV = tenSV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.tinhTrang = tinhTrang;
        this.maLop = maLop;
    }

    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }

    public String getTenSV() { return tenSV; }
    public void setTenSV(String tenSV) { this.tenSV = tenSV; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getQueQuan() { return queQuan; }
    public void setQueQuan(String queQuan) { this.queQuan = queQuan; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }
}