package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO implements DAO<ThuThu> {
    DBHelper helper;
    public ThuThuDAO(Context context) {
        this.helper = new DBHelper(context);
    }

    @Override
    public List<ThuThu> getAll() {
        List<ThuThu> thuThus = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_THU_THU;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    String id = cs.getString(0);
                    String password = cs.getString(1);
                    String ten = cs.getString(2);
                    String sdt = cs.getString(3);
                    thuThus.add(new ThuThu(id, password, ten, sdt));
                    cs.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thuThus;
    }

    @Override
    public ThuThu getById(String id) {
        String query = "SELECT * FROM " + DBHelper.TABLE_THU_THU + "" +
                " Where " + DBHelper.THU_THU_ID + " LIKE '" + id + "'";
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs.getCount() > 0) {
                cs.moveToFirst();
                String id1 = cs.getString(0);
                String password = cs.getString(1);
                String ten = cs.getString(2);
                String sdt = cs.getString(3);
                return new ThuThu(id1, password, ten, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ThuThu("", "", "", "");
    }

    @Override
    public long insert(ThuThu thuThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.THU_THU_ID, thuThu.getMaThuThu());
        contentValues.put(DBHelper.THU_THU_PASSWORD, thuThu.getPassword());
        contentValues.put(DBHelper.THU_THU_TEN, thuThu.getHoTen());
        contentValues.put(DBHelper.THU_THU_SDT, thuThu.getSoDienThoai());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.insert(DBHelper.TABLE_THU_THU, null, contentValues);
        }
    }

    @Override
    public long update(ThuThu thuThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.THU_THU_ID, thuThu.getMaThuThu());
        contentValues.put(DBHelper.THU_THU_PASSWORD, thuThu.getPassword());
        contentValues.put(DBHelper.THU_THU_TEN, thuThu.getHoTen());
        contentValues.put(DBHelper.THU_THU_SDT, thuThu.getSoDienThoai());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.update(DBHelper.TABLE_THU_THU,
                    contentValues, DBHelper.THU_THU_ID + " = ?",
                    new String[] {thuThu.getMaThuThu()});
        }
    }

    @Override
    public long delete(ThuThu thuThu) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            int a = db.delete(DBHelper.TABLE_PHIEU_MUON,
                    DBHelper.PHIEU_MUON_ID_THU_THU + " = ?",
                    new String[] {thuThu.getMaThuThu()});
            int b = db.delete(DBHelper.TABLE_THU_THU
                    , DBHelper.THU_THU_ID + " = ?"
                    , new String[] {thuThu.getMaThuThu()});
            return a + b;
        }
    }
}
