package model;

public class LichDay {
    private String maLHP;
    private String tenMH;
    private String maPhong;
    private String thu;
    private String tietBD;
    private String tietKT;
    private int siSo;

    public LichDay() {}

    public LichDay(String maLHP, String tenMH, String maPhong, String thu, String tietBD, String tietKT, int siSo) {
        this.maLHP = maLHP;
        this.tenMH = tenMH;
        this.maPhong = maPhong;
        this.thu = thu;
        this.tietBD = tietBD;
        this.tietKT = tietKT;
        this.siSo = siSo;
    }

    // Getters and Setters
    public String getMaLHP() { return maLHP; }
    public void setMaLHP(String maLHP) { this.maLHP = maLHP; }

    public String getTenMH() { return tenMH; }
    public void setTenMH(String tenMH) { this.tenMH = tenMH; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getThu() { return thu; }
    public void setThu(String thu) { this.thu = thu; }

    public String getTietBD() { return tietBD; }
    public void setTietBD(String tietBD) { this.tietBD = tietBD; }

    public String getTietKT() { return tietKT; }
    public void setTietKT(String tietKT) { this.tietKT = tietKT; }

    public int getSiSo() { return siSo; }
    public void setSiSo(int siSo) { this.siSo = siSo; }
}