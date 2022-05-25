package com.example.ph19127_mob2041.model;

public class Sach {
    private String maSach;
    private String maLoaiSach;
    private String tieuDe;
    private String tacGia;
    private double donGia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sach sach = (Sach) o;

        return maSach.equals(sach.maSach);
    }

    @Override
    public int hashCode() {
        return maSach.hashCode();
    }

    public Sach() {
    }

    @Override
    public String toString() {
        return maSach + " - " + tieuDe;
    }

    public Sach(String maSach) {
        this.maSach = maSach;
    }

    public Sach(String maSach, String maLoaiSach, String tieuDe, String tacGia, double donGia) {
        this.maSach = maSach;
        this.maLoaiSach = maLoaiSach;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.donGia = donGia;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
