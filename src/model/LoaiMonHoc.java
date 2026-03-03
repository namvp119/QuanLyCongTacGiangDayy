package model;

public class LoaiMonHoc {

    private String maLoaiMH;
    private String tenLoaiMH;
    private float heSo;

    public LoaiMonHoc() {}

    public LoaiMonHoc(String maLoaiMH, String tenLoaiMH, float heSo) {
        this.maLoaiMH = maLoaiMH;
        this.tenLoaiMH = tenLoaiMH;
        this.heSo = heSo;
    }

    public String getMaLoaiMH() { return maLoaiMH; }
    public void setMaLoaiMH(String maLoaiMH) { this.maLoaiMH = maLoaiMH; }

    public String getTenLoaiMH() { return tenLoaiMH; }
    public void setTenLoaiMH(String tenLoaiMH) { this.tenLoaiMH = tenLoaiMH; }

    public float getHeSo() { return heSo; }
    public void setHeSo(float heSo) { this.heSo = heSo; }

    @Override
    public String toString() {
        return tenLoaiMH; 
    }
}