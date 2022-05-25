package com.example.ph19127_mob2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PhuongNamLIB";
    public static final int VERSION = 9;

    public static final String TABLE_PHIEU_MUON = "PhieuMuon";
    public static final String PHIEU_MUON_ID = "maPhieuMuon";
    public static final String PHIEU_MUON_ID_THANH_VIEN = "maThanhVien";
    public static final String PHIEU_MUON_ID_SACH = "maSach";
    public static final String PHIEU_MUON_ID_THU_THU = "maThuThu";

    public static final String TABLE_THANH_VIEN = "ThanhVien";
    public static final String THANH_VIEN_ID = "maThanhVien";
    public static final String THANH_VIEN_TEN = "tenThanhVien";
    public static final String THANH_VIEN_SDT = "soDienThoai";

    public static final String TABLE_LOAI_SACH = "LoaiSach";
    public static final String LOAI_SACH_ID = "maLoaiSach";
    public static final String LOAI_SACH_TEN = "tenLoaiSach";

    public static final String TABLE_SACH = "Sach";
    public static final String SACH_ID = "maSach";
    public static final String SACH_ID_LOAI_SACH = "maLoaiSach";
    public static final String SACH_TIEU_DE = "tieuDe";
    public static final String SACH_TAC_GIA = "tacGia";
    public static final String SACH_DON_GIA = "donGia";

    public static final String TABLE_THU_THU = "ThuThu";
    public static final String THU_THU_ID = "maThuThu";
    public static final String THU_THU_PASSWORD = "passWord";
    public static final String THU_THU_TEN = "hoTen";
    public static final String THU_THU_SDT = "soDienThoai";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crateTableThuThu = "CREATE TABLE IF NOT EXISTS " + TABLE_THU_THU + "(" +
                THU_THU_ID + " TEXT NOT NULL PRIMARY KEY, " +
                THU_THU_PASSWORD + " TEXT NOT NULL, " +
                THU_THU_TEN + " TEXT, " +
                THU_THU_SDT + "TEXT" +
                ")";
        db.execSQL(crateTableThuThu);

        String crateTableThanhVien = "CREATE TABLE IF NOT EXISTS " + TABLE_THANH_VIEN + "(" +
                THANH_VIEN_ID + " TEXT NOT NULL PRIMARY KEY, " +
                THANH_VIEN_TEN + " TEXT, " +
                THANH_VIEN_SDT + " TEXT " +
                ")";
        db.execSQL(crateTableThanhVien);

        String crateTableLoaiSach = "CREATE TABLE IF NOT EXISTS " + TABLE_LOAI_SACH + "(" +
                LOAI_SACH_ID + " TEXT NOT NULL PRIMARY KEY, " +
                LOAI_SACH_TEN + " TEXT" +
                ")";
        db.execSQL(crateTableLoaiSach);

        String crateTableSach = "CREATE TABLE IF NOT EXISTS " + TABLE_SACH + "(" +
                SACH_ID + " TEXT NOT NULL PRIMARY KEY, " +
                SACH_ID_LOAI_SACH + " TEXT NOT NULL, " +
                SACH_TIEU_DE + "TEXT," +
                SACH_TAC_GIA + " TEXT, " +
                SACH_DON_GIA + "REAL," +
                "FOREIGN KEY (" +
                SACH_ID_LOAI_SACH +") " +
                "REFERENCES " +
                TABLE_LOAI_SACH + "(" +
                LOAI_SACH_ID +
                ")" +
                ")";
        db.execSQL(crateTableSach);

        String crateTablePhieuMuon = "CREATE TABLE IF NOT EXISTS " + TABLE_PHIEU_MUON + "(" +
                PHIEU_MUON_ID + " TEXT NOT NULL PRIMARY KEY, " +
                PHIEU_MUON_ID_SACH + " TEXT NOT NULL, " +
                PHIEU_MUON_ID_THANH_VIEN + " TEXT NOT NULL, " +
                PHIEU_MUON_ID_THU_THU + " TEXT NOT NULL," +
                "FOREIGN KEY (" +
                PHIEU_MUON_ID_SACH +") " +
                "REFERENCES " +
                TABLE_SACH + "(" +
                SACH_ID +
                ")," +
                "FOREIGN KEY (" +
                PHIEU_MUON_ID_THANH_VIEN +") " +
                "REFERENCES " +
                TABLE_THANH_VIEN + "(" +
                THANH_VIEN_ID +
                ")," +
                "FOREIGN KEY (" +
                PHIEU_MUON_ID_THU_THU +") " +
                "REFERENCES " +
                TABLE_THU_THU + "(" +
                THU_THU_ID +
                ")" +
                ")";
        db.execSQL(crateTablePhieuMuon);

        db.execSQL("INSERT INTO ThuThu VALUES ('MATHUTHU', 'PWTHUTHU','HOTENTT', 'SDT')");
        db.execSQL("INSERT INTO ThanhVien VALUES ('MATHANHVIEN', 'TENTHANHVIEN','SDT')");
        db.execSQL("INSERT INTO ThanhVien VALUES ('MATHANHVIEN2', 'TENTHANHVIEN','SDT')");
        db.execSQL("INSERT INTO LoaiSach VALUES ('MALOAISACH', 'TENLOAISACH')");
        db.execSQL("INSERT INTO Sach VALUES ('MASACH', 'MALOAISACH','TIEUDE', 'TACGIA',10000)");
        db.execSQL("INSERT INTO Sach VALUES ('MASACH2', 'MALOAISACH','TIEUDE', 'TACGIA',10000)");
        db.execSQL("INSERT INTO Sach VALUES ('MASACH3', 'MALOAISACH','TIEUDE', 'TACGIA',10000)");
        db.execSQL("INSERT INTO PhieuMuon VALUES ('MAPHIEUMUON','MASACH', 'MATHANHVIEN', 'MATHUTHU')");
        db.execSQL("INSERT INTO PhieuMuon VALUES ('MAPHIEUMUON2','MASACH2', 'MATHANHVIEN2', 'MATHUTHU')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHIEU_MUON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANH_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THU_THU);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
