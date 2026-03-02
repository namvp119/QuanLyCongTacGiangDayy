package model;

public class ChucDanh {

    private String maCD;
    private String tenCD;
    private int donGiaTiet;

    public ChucDanh(String maCD, String tenCD, int donGiaTiet) {
        this.maCD = maCD;
        this.tenCD = tenCD;
        this.donGiaTiet = donGiaTiet;
    }

    public String getMaCD() { return maCD; }
    public String getTenCD() { return tenCD; }
    public int getDonGiaTiet() { return donGiaTiet; }

    @Override
    public String toString() {
        return tenCD;
    }
}