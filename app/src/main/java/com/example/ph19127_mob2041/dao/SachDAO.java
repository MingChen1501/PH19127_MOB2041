package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO implements DAO<Sach> {
    DBHelper helper;
    public SachDAO(Context context) {
        this.helper = new DBHelper(context);
    }
    @Override
    public List<Sach> getAll() {
        List<Sach> saches = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_SACH;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    String id = cs.getString(0);
                    String idLoaiSach = cs.getString(1);
                    String tieuDe = cs.getString(2);
                    String tacGia = cs.getString(3);
                    double donGia = Double.parseDouble(cs.getString(4));
                    saches.add(new Sach(id, idLoaiSach, tieuDe, tacGia, donGia));
                    cs.moveToNext();
                }
            }
            return saches;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sach getById(String id) {
        String query = "SELECT * FROM " + DBHelper.TABLE_SACH + "" +
                " Where " + DBHelper.SACH_ID + " LIKE " + id;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                String idSach = cs.getString(0);
                String idLoaiSach = cs.getString(1);
                String tieuDe = cs.getString(2);
                String tacGia = cs.getString(3);
                double donGia = Double.parseDouble(cs.getString(4));
                return new Sach(idSach, idLoaiSach, tieuDe, tacGia, donGia);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long insert(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SACH_ID, sach.getMaSach());
        contentValues.put(DBHelper.SACH_ID_LOAI_SACH, sach.getMaLoaiSach());
        contentValues.put(DBHelper.SACH_TIEU_DE, sach.getTieuDe());
        contentValues.put(DBHelper.SACH_TAC_GIA, sach.getTacGia());
        contentValues.put(DBHelper.SACH_DON_GIA, sach.getDonGia());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.insert(DBHelper.TABLE_SACH, null, contentValues);

        }
    }

    @Override
    public long update(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SACH_ID, sach.getMaSach());
        contentValues.put(DBHelper.SACH_ID_LOAI_SACH, sach.getMaLoaiSach());
        contentValues.put(DBHelper.SACH_TIEU_DE, sach.getTieuDe());
        contentValues.put(DBHelper.SACH_TAC_GIA, sach.getTacGia());
        contentValues.put(DBHelper.SACH_DON_GIA, sach.getDonGia());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.update(DBHelper.TABLE_SACH,
                    contentValues, DBHelper.SACH_ID + " = ?",
                    new String[] {sach.getMaSach()});
        }
    }

    @Override
    public long delete(Sach sach) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.delete(DBHelper.TABLE_SACH
                    , DBHelper.SACH_ID + " = ?"
                    , new String[] {sach.getMaSach()});
        }
    }
}
