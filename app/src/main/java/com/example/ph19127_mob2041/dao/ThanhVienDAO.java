package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO implements DAO<ThanhVien> {
    DBHelper helper;
    public ThanhVienDAO(Context context) {
        this.helper = new DBHelper(context);
    }
    @Override
    public List<ThanhVien> getAll() {
        List<ThanhVien> thanhViens = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_THANH_VIEN;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    String id = cs.getString(0);
                    String ten = cs.getString(1);
                    String sdt = cs.getString(2);
                    thanhViens.add(new ThanhVien(id, ten, sdt));
                    cs.moveToNext();
                }
            }
            return thanhViens;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ThanhVien getById(String id) {
        String query = "SELECT * FROM " + DBHelper.TABLE_THANH_VIEN + "" +
                " Where " + DBHelper.THANH_VIEN_ID + " LIKE " + id;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                String ma = cs.getString(0);
                String ten = cs.getString(1);
                String sdt = cs.getString(2);
                return new ThanhVien(ma, ten, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long insert(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.THANH_VIEN_ID, thanhVien.getMaThanhVien());
        contentValues.put(DBHelper.THANH_VIEN_TEN, thanhVien.getTenThanhVien());
        contentValues.put(DBHelper.THANH_VIEN_SDT, thanhVien.getSoDienThoai());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.insert(DBHelper.TABLE_THANH_VIEN, null, contentValues);
        }
    }

    @Override
    public long update(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.THANH_VIEN_ID, thanhVien.getMaThanhVien());
        contentValues.put(DBHelper.THANH_VIEN_TEN, thanhVien.getTenThanhVien());
        contentValues.put(DBHelper.THANH_VIEN_SDT, thanhVien.getSoDienThoai());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.update(DBHelper.TABLE_THANH_VIEN,
                    contentValues, DBHelper.THANH_VIEN_ID + " = ?",
                    new String[] {thanhVien.getMaThanhVien()});
        }
    }

    @Override
    public long delete(ThanhVien thanhVien) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            /*long b = db.delete(DBHelper.TABLE_PHIEU_MUON,
                    DBHelper.PHIEU_MUON_ID_THANH_VIEN + " = ?",
                    new String[] {thanhVien.getMaThanhVien()});*/
            long a = db.delete(DBHelper.TABLE_THANH_VIEN
                    , DBHelper.THANH_VIEN_ID + " = ?"
                    , new String[] {thanhVien.getMaThanhVien()});
            return a;
        }
    }
}
