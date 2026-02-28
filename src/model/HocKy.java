package model;

public class HocKy {
    private String maHocKy;
    private String tenHK;
    private String namHoc;
    private String tinhTrang;

    public HocKy() {}

    public HocKy(String maHocKy, String tenHK, String namHoc, String tinhTrang) {
        this.maHocKy = maHocKy;
        this.tenHK = tenHK;
        this.namHoc = namHoc;
        this.tinhTrang = tinhTrang;
    }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public String getTenHK() { return tenHK; }
    public void setTenHK(String tenHK) { this.tenHK = tenHK; }

    public String getNamHoc() { return namHoc; }
    public void setNamHoc(String namHoc) { this.namHoc = namHoc; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
}