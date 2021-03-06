package com.example.ph19127_mob2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PhuongNamLIB";
    public static final int VERSION = 19;


    public static final String TABLE_PHIEU_MUON = "PhieuMuon";
    public static final String PHIEU_MUON_ID = "maPhieuMuon";
    public static final String PHIEU_MUON_ID_THANH_VIEN = "maThanhVien";
    public static final String PHIEU_MUON_ID_SACH = "maSach";
    public static final String PHIEU_MUON_ID_THU_THU = "maThuThu";
    public static final String PHIEU_MUON_NGAY_MUON = "ngayMuon";
    public static final String PHIEU_MUON_TRANG_THAI = "trangThai";

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
        //getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crateTableThuThu = "CREATE TABLE IF NOT EXISTS " + TABLE_THU_THU + "(" +
                THU_THU_ID + " TEXT NOT NULL PRIMARY KEY, " +
                THU_THU_PASSWORD + " TEXT NOT NULL, " +
                THU_THU_TEN + " TEXT, " +
                THU_THU_SDT + " TEXT" +
                ")";
        db.execSQL(crateTableThuThu);

        String crateTableThanhVien = "CREATE TABLE IF NOT EXISTS " + TABLE_THANH_VIEN + "(" +
                THANH_VIEN_ID + " TEXT NOT NULL PRIMARY KEY, " +
                THANH_VIEN_TEN + " TEXT, " +
                THANH_VIEN_SDT + " TEXT " +
                ")";
        db.execSQL(crateTableThanhVien);

        String crateTableLoaiSach = "CREATE TABLE IF NOT EXISTS " + TABLE_LOAI_SACH + "(" +
                LOAI_SACH_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                LOAI_SACH_TEN + " TEXT" +
                ")";
        db.execSQL(crateTableLoaiSach);

        String crateTableSach = "CREATE TABLE IF NOT EXISTS " + TABLE_SACH + "(" +
                SACH_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                SACH_ID_LOAI_SACH + " INTEGER NOT NULL, " +
                SACH_TIEU_DE + " TEXT," +
                SACH_TAC_GIA + " TEXT, " +
                SACH_DON_GIA + " REAL," +
                "FOREIGN KEY (" +
                SACH_ID_LOAI_SACH +") " +
                "REFERENCES " +
                TABLE_LOAI_SACH + "(" +
                LOAI_SACH_ID +
                ")" +
                ")";
        db.execSQL(crateTableSach);

        String crateTablePhieuMuon = "CREATE TABLE IF NOT EXISTS " + TABLE_PHIEU_MUON + "(" +
                PHIEU_MUON_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                PHIEU_MUON_ID_SACH + " INTEGER NOT NULL, " +
                PHIEU_MUON_ID_THANH_VIEN + " TEXT NOT NULL, " +
                PHIEU_MUON_ID_THU_THU + " TEXT NOT NULL," +
                PHIEU_MUON_NGAY_MUON + " TEXT NOT NULL," +
                PHIEU_MUON_TRANG_THAI + " BOOLEAN NOT NULL," +
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

        db.execSQL("INSERT INTO ThuThu VALUES ('admin', 'admin','admin', '0389119966')");
        db.execSQL("INSERT INTO ThuThu VALUES ('tminh400', '123456','tran cong minh', '0389119966')");
        db.execSQL("INSERT INTO ThuThu VALUES ('tminh401', '123456','tran cong minh', '0389119966')");
        db.execSQL("INSERT INTO ThuThu VALUES ('tminh402', '123456','tran cong minh', '0389119966')");
        db.execSQL("INSERT INTO ThanhVien VALUES ('MATHANHVIEN1', 'nguyen van a','098888888')");
        db.execSQL("INSERT INTO ThanhVien VALUES ('MATHANHVIEN2', 'nguyen van b','098888888')");
        db.execSQL("INSERT INTO LoaiSach VALUES (1,'hoc tap')");
        db.execSQL("INSERT INTO LoaiSach VALUES (2,'tieu thuyet')");
        db.execSQL("INSERT INTO LoaiSach VALUES (3,'tai lieu')");
        db.execSQL("INSERT INTO Sach VALUES (1,1,'html css', 'nguyen van c',5000)");
        db.execSQL("INSERT INTO Sach VALUES (2,1,'JAVA', 'nguyen van c',6000)");
        db.execSQL("INSERT INTO Sach VALUES (3,1,'C++', 'nguyen van c',7000)");
        db.execSQL("INSERT INTO Sach VALUES (4,2,'nghin le 1 dem', 'nguyen van d',10000)");
        db.execSQL("INSERT INTO Sach VALUES (5,3,'Datastructure & algorithm', 'nguyen van e',9000)");
        /*db.execSQL("INSERT INTO PhieuMuon VALUES " +
                "(1,1, 'MATHANHVIEN1', 'tminh400', '2022-06-01', 0)");
        db.execSQL("INSERT INTO PhieuMuon VALUES " +
                "(2,2, 'MATHANHVIEN2', 'tminh401', '2022-06-01', 0)");*/
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
