package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.Top;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    contentValues,
                    DBHelper.SACH_ID + " = ?",
                    new String[] {sach.getMaSach()});
        }
    }

    @Override
    public long delete(Sach sach) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            /*int a = db.delete(DBHelper.TABLE_PHIEU_MUON,
                    DBHelper.PHIEU_MUON_ID_SACH + " = ? ",
                    new String[] {sach.getMaSach()});*/
            int b = db.delete(DBHelper.TABLE_SACH,
                    DBHelper.SACH_ID + " = ?",
                    new String[] {sach.getMaSach()});
            return b;
        }
    }
    public List<Top> getTopTenRecordWithPhieuMuon() {
        List<Top> res = new ArrayList<>();
        /*String query = "SELECT " + DBHelper.PHIEU_MUON_ID_SACH + ", " +
                "count("+ DBHelper.PHIEU_MUON_ID_SACH +") AS count" +
                " FROM " + DBHelper.TABLE_PHIEU_MUON + " " +
                "GROUP BY " + DBHelper.PHIEU_MUON_ID_SACH + " " +
                "ORDER BY count DESC LIMIT 10";*/
        String query2 = "SELECT s.*, count(pm.maSach) AS count FROM PhieuMuon pm JOIN sach s ON pm.maSach LIKE s.maSach GROUP BY pm.maSach ORDER BY count DESC LIMIT 10";
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query2, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                for (int i = 0; i < cs.getCount(); i++) {
                    String idSach = cs.getString(0);
                    String idLoai =cs.getString(1);
                    String tieuDe = cs.getString(2);
                    String TacGia =cs.getString(3);
                    String donGia = cs.getString(4);
                    Integer soLuong = Integer.parseInt(cs.getString(5));
                    Top top = new Top(idSach, idLoai, tieuDe, TacGia, Double.parseDouble(donGia), soLuong);
                    res.add(top);
                    cs.moveToNext();
                }

            }
            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
