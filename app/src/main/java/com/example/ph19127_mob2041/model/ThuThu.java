package com.example.ph19127_mob2041.model;

public class ThuThu {
    private String maThuThu;
    private String password;
    private String hoTen;
    private String soDienThoai;

    public ThuThu(String maThuThu, String password, String hoTen, String soDienThoai) {
        this.maThuThu = maThuThu;
        this.password = password;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }

    public ThuThu() {
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
