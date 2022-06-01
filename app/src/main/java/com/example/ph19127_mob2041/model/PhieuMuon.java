package com.example.ph19127_mob2041.model;


import java.util.Date;

public class PhieuMuon {
    private String maPhieuMuon;
    private String maThanhVien;
    private String maSach;
    private String maThuThu;
    private Date ngayMuon;
    private boolean isDaTra;

    public PhieuMuon(String maPhieuMuon, String maThanhVien, String maSach, String maThuThu, Date ngayMuon, boolean isDaTra) {
        this.maPhieuMuon = maPhieuMuon;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
        this.ngayMuon = ngayMuon;
        this.isDaTra = isDaTra;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public boolean isDaTra() {
        return isDaTra;
    }

    public void setDaTra(boolean daTra) {
        this.isDaTra = daTra;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhieuMuon phieuMuon = (PhieuMuon) o;

        return maPhieuMuon.equals(phieuMuon.maPhieuMuon);
    }

    @Override
    public int hashCode() {
        return maPhieuMuon.hashCode();
    }

    public PhieuMuon() {
    }
    @Deprecated
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

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPhieuMuon='" + maPhieuMuon + '\'' +
                ", maThanhVien='" + maThanhVien + '\'' +
                ", maSach='" + maSach + '\'' +
                ", maThuThu='" + maThuThu + '\'' +
                ", ngayMuon=" + ngayMuon +
                ", trangThai=" + isDaTra +
                '}';
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
