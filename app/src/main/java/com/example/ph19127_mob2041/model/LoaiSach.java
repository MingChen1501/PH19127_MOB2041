package com.example.ph19127_mob2041.model;

public class LoaiSach {
    private String maLoaiSach;
    private String tenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(String maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoaiSach loaiSach = (LoaiSach) o;

        return maLoaiSach.equals(loaiSach.maLoaiSach);
    }

    @Override
    public int hashCode() {
        return maLoaiSach.hashCode();
    }

    @Override
    public String toString() {
        return maLoaiSach + " - " + tenLoaiSach;
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
