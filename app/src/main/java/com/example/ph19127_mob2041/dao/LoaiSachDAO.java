package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO implements DAO<LoaiSach>{
    DBHelper helper;
    public LoaiSachDAO(Context context) {
        this.helper = new DBHelper(context);
    }
    @Override
    public List<LoaiSach> getAll() {
        List<LoaiSach> loaiSaches = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_LOAI_SACH;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    String id = cs.getString(0);
                    String ten = cs.getString(1);
                    loaiSaches.add(new LoaiSach(id, ten));
                    cs.moveToNext();
                }
            }
            return loaiSaches;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LoaiSach getById(String id) {
        String query = "SELECT * FROM " + DBHelper.TABLE_LOAI_SACH + "" +
                " Where " + DBHelper.LOAI_SACH_ID + " LIKE " + id;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                String ma = cs.getString(0);
                String ten = cs.getString(1);
                return new LoaiSach(ma, ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long insert(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.LOAI_SACH_ID, loaiSach.getMaLoaiSach());
        contentValues.put(DBHelper.LOAI_SACH_TEN, loaiSach.getMaLoaiSach());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.insert(DBHelper.TABLE_LOAI_SACH, null, contentValues);

        }
    }

    @Override
    public long update(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.LOAI_SACH_ID, loaiSach.getMaLoaiSach());
        contentValues.put(DBHelper.LOAI_SACH_TEN, loaiSach.getTenLoaiSach());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.update(DBHelper.TABLE_LOAI_SACH,
                    contentValues, DBHelper.LOAI_SACH_ID + " = ?",
                    new String[] {loaiSach.getMaLoaiSach()});
        }
    }

    @Override
    public long delete(LoaiSach loaiSach) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {


            int a = db.delete(DBHelper.TABLE_PHIEU_MUON,
                    DBHelper.PHIEU_MUON_ID_SACH + " in (" +
                            "SELECT "+ DBHelper.SACH_ID +" FROM " +
                            DBHelper.TABLE_SACH + " " +
                            "WHERE " + DBHelper.SACH_ID_LOAI_SACH + " = ? " +
                            ")",
                    new String[] {loaiSach.getMaLoaiSach()});
            //delete record from table phieu muon

            int b = db.delete(DBHelper.TABLE_SACH,
                    DBHelper.SACH_ID_LOAI_SACH + " = ?",
                    new String[] {loaiSach.getMaLoaiSach()});
            //delete record from table sach


            int c = db.delete(DBHelper.TABLE_LOAI_SACH
                    , DBHelper.LOAI_SACH_ID + " = ?"
                    , new String[] {loaiSach.getMaLoaiSach()});
            //delete record from table loai sach

            return a + b + c;
        }
    }
}
