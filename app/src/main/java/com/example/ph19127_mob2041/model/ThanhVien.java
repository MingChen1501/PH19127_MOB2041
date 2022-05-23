package com.example.ph19127_mob2041.model;

public class ThanhVien {
    private String maThanhVien;
    private String tenThanhVien;
    private String soDienThoai;

    public ThanhVien() {
    }

    public ThanhVien(String maThanhVien, String tenThanhVien, String soDienThoai) {
        this.maThanhVien = maThanhVien;
        this.tenThanhVien = tenThanhVien;
        this.soDienThoai = soDienThoai;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getTenThanhVien() {
        return tenThanhVien;
    }

    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
