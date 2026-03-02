package model;

public class NguoiDung {
    private String maND;      
    private String maLoai;    
    private String tinhTrang; 
    private String matKhau;

    public NguoiDung() {
    }

    public NguoiDung(String maND, String maLoai, String tinhTrang, String matKhau) {
        this.maND = maND;
        this.maLoai = maLoai;
        this.tinhTrang = tinhTrang;
        this.matKhau = matKhau;
    }

    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    public String getMaLoai() { return maLoai; }
    public void setMaLoai(String maLoai) { this.maLoai = maLoai; }
    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}