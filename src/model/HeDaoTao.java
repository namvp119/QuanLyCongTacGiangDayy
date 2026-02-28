package model;

public class HeDaoTao {

    private String maHDT;
    private String tenHDT;

    public HeDaoTao() {
    }

    public HeDaoTao(String maHDT, String tenHDT) {
        this.maHDT = maHDT;
        this.tenHDT = tenHDT;
    }

    public String getMaHDT() {
        return maHDT;
    }

    public void setMaHDT(String maHDT) {
        this.maHDT = maHDT;
    }

    public String getTenHDT() {
        return tenHDT;
    }

    public void setTenHDT(String tenHDT) {
        this.tenHDT = tenHDT;
    }
}