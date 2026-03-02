package model;

public class MonHoc {

    private String maMH;
    private String tenMH;
    private int soTC;
    private String maLoaiMH;
    private String tenLoaiMH;

    public MonHoc() {}

    public MonHoc(String maMH, String tenMH, int soTC, String maLoaiMH) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTC = soTC;
        this.maLoaiMH = maLoaiMH;
    }

    public String getMaMH() { return maMH; }
    public void setMaMH(String maMH) { this.maMH = maMH; }

    public String getTenMH() { return tenMH; }
    public void setTenMH(String tenMH) { this.tenMH = tenMH; }

    public int getSoTC() { return soTC; }
    public void setSoTC(int soTC) { this.soTC = soTC; }

    public String getMaLoaiMH() { return maLoaiMH; }
    public void setMaLoaiMH(String maLoaiMH) { this.maLoaiMH = maLoaiMH; }

    public String getTenLoaiMH() { return tenLoaiMH; }
    public void setTenLoaiMH(String tenLoaiMH) { this.tenLoaiMH = tenLoaiMH; }
}