package model;

import java.util.Date;

public class CanBoGiangDay {

    private String mscb;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String queQuan;
    private String diaChi;
    private String sdt;
    private String email;
    private String tinhTrang;
    private String maKhoa;
    private String maCD;

    public CanBoGiangDay() {}

    public CanBoGiangDay(String mscb, String hoTen, String gioiTinh,
                         Date ngaySinh, String queQuan, String diaChi,
                         String sdt, String email, String tinhTrang,
                         String maKhoa, String maCD) {
        this.mscb = mscb;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.tinhTrang = tinhTrang;
        this.maKhoa = maKhoa;
        this.maCD = maCD;
    }
    private String tenKhoa;
    private String tenCD;

    public String getTenKhoa() { return tenKhoa; }
    public void setTenKhoa(String tenKhoa) { this.tenKhoa = tenKhoa; }
    public String getTenCD() {
        return tenCD;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
    }

    public String getMscb() { return mscb; }
    public void setMscb(String mscb) { this.mscb = mscb; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

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

    public String getMaKhoa() { return maKhoa; }
    public void setMaKhoa(String maKhoa) { this.maKhoa = maKhoa; }

    public String getMaCD() { return maCD; }
    public void setMaCD(String maCD) { this.maCD = maCD; }
    @Override
    public String toString() {
        return this.getHoTen();
    }
}