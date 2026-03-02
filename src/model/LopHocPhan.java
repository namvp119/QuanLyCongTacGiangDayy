package model;

import java.sql.Date;

public class LopHocPhan {
    private String maLHP;
    private String tenLHP;
    private int siSo;
    private String tinhTrang;
    private String mscb;      
    private String maHocKy;
    private String maLop;
    private String maMH;
    private String maPhong;
    private Date ngay;        

    public LopHocPhan() {
    }

    public LopHocPhan(String maLHP, String tenLHP, int siSo, String tinhTrang, String mscb, String maHocKy,
            String maLop, String maMH, String maPhong, Date ngay) {
        this.maLHP = maLHP;
        this.tenLHP = tenLHP;
        this.siSo = siSo;
        this.tinhTrang = tinhTrang;
        this.mscb = mscb;
        this.maHocKy = maHocKy;
        this.maLop = maLop;
        this.maMH = maMH;
        this.maPhong = maPhong;
        this.ngay = ngay;
    }

    // =========================================================
    // KHU VỰC GETTER VÀ SETTER (BẮT BUỘC PHẢI CÓ ĐỂ HẾT BÁO LỖI)
    // =========================================================
    
    public String getMaLHP() {
        return maLHP;
    }

    public void setMaLHP(String maLHP) {
        this.maLHP = maLHP;
    }

    public String getTenLHP() {
        return tenLHP;
    }

    public void setTenLHP(String tenLHP) {
        this.tenLHP = tenLHP;
    }

    public int getSiSo() {
        return siSo;
    }

    public void setSiSo(int siSo) {
        this.siSo = siSo;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMscb() {
        return mscb;
    }

    public void setMscb(String mscb) {
        this.mscb = mscb;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}