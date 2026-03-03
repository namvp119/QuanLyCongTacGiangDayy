package model;

public class ThongKe {
    private String maCB;
    private String hoTen;
    private int tongLop;
    private int tongTiet;
    private int tongSV;
    private String lopCoVan;

    public ThongKe() {}

    public ThongKe(String maCB, String hoTen, int tongLop, int tongTiet, int tongSV, String lopCoVan) {
        this.maCB = maCB;
        this.hoTen = hoTen;
        this.tongLop = tongLop;
        this.tongTiet = tongTiet;
        this.tongSV = tongSV;
        this.lopCoVan = lopCoVan;
    }

    // Getters and Setters
    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getTongLop() { return tongLop; }
    public void setTongLop(int tongLop) { this.tongLop = tongLop; }

    public int getTongTiet() { return tongTiet; }
    public void setTongTiet(int tongTiet) { this.tongTiet = tongTiet; }

    public int getTongSV() { return tongSV; }
    public void setTongSV(int tongSV) { this.tongSV = tongSV; }

    public String getLopCoVan() { return lopCoVan; }
    public void setLopCoVan(String lopCoVan) { this.lopCoVan = lopCoVan; }
}