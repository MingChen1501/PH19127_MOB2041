package com.example.ph19127_mob2041.model;

public class PhieuMuon {
    private String maPhieuMuon;
    private String maThanhVien;
    private String maSach;
    private String maThuThu;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieuMuon, String maThanhVien, String maSach, String maThuThu) {
        this.maPhieuMuon = maPhieuMuon;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(String maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }
}
