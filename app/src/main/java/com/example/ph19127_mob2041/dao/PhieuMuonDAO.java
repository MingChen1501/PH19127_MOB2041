package com.example.ph19127_mob2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO implements DAO<PhieuMuon>{
    DBHelper helper;
    public PhieuMuonDAO(Context context) {
        this.helper = new DBHelper(context);
    }
    @Override
    public List<PhieuMuon> getAll() {
        List<PhieuMuon> phieuMuons = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_PHIEU_MUON;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    String idPM = cs.getString(0);
                    String idThanhVien = cs.getString(1);
                    String idSach = cs.getString(2);
                    String idThuThu = cs.getString(3);
                    phieuMuons.add(new PhieuMuon(idPM, idThanhVien, idSach, idThuThu));
                    cs.moveToNext();
                }
            }
            return phieuMuons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PhieuMuon getById(String id) {
        String query = "SELECT * FROM " + DBHelper.TABLE_PHIEU_MUON + "" +
                " Where " + DBHelper.PHIEU_MUON_ID + " LIKE " + id;
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.rawQuery(query, null)) {
            if (cs != null && cs.getCount() > 0) {
                String idPM = cs.getString(0);
                String idThanhVien = cs.getString(1);
                String idSach = cs.getString(2);
                String idThuThu = cs.getString(3);
                new PhieuMuon(idPM, idThanhVien, idSach, idThuThu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long insert(PhieuMuon phieuMuon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PHIEU_MUON_ID, phieuMuon.getMaPhieuMuon());
        contentValues.put(DBHelper.PHIEU_MUON_ID_SACH, phieuMuon.getMaSach());
        contentValues.put(DBHelper.PHIEU_MUON_ID_THANH_VIEN, phieuMuon.getMaThanhVien());
        contentValues.put(DBHelper.PHIEU_MUON_ID_THU_THU, phieuMuon.getMaThuThu());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.insert(DBHelper.TABLE_PHIEU_MUON, null, contentValues);

        }
    }

    @Override
    public long update(PhieuMuon phieuMuon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PHIEU_MUON_ID, phieuMuon.getMaPhieuMuon());
        contentValues.put(DBHelper.PHIEU_MUON_ID_SACH, phieuMuon.getMaSach());
        contentValues.put(DBHelper.PHIEU_MUON_ID_THANH_VIEN, phieuMuon.getMaThanhVien());
        contentValues.put(DBHelper.PHIEU_MUON_ID_THU_THU, phieuMuon.getMaThuThu());
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.update(DBHelper.TABLE_PHIEU_MUON,
                    contentValues, DBHelper.SACH_ID + " = ?",
                    new String[] {phieuMuon.getMaPhieuMuon()});
        }
    }

    @Override
    public long delete(PhieuMuon phieuMuon) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            return db.delete(DBHelper.TABLE_PHIEU_MUON
                    , DBHelper.PHIEU_MUON_ID + " = ?"
                    , new String[] {phieuMuon.getMaPhieuMuon()});
        }
    }
}
