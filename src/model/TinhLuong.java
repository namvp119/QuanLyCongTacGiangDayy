package model;

public class TinhLuong {
    private String maCB;
    private String hoTen;
    private int tongTC;
    private double tongTien;

    public TinhLuong() {}

    public TinhLuong(String maCB, String hoTen, int tongTC, double tongTien) {
        this.maCB = maCB;
        this.hoTen = hoTen;
        this.tongTC = tongTC;
        this.tongTien = tongTien;
    }

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getTongTC() { return tongTC; }
    public void setTongTC(int tongTC) { this.tongTC = tongTC; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}