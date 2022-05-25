package com.example.ph19127_mob2041.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.PhieuMuonAdapter;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PhieuMuonFragment extends Fragment {

    private List<PhieuMuon> phieuMuonList;
    private List<ThanhVien> thanhVienList;
    private List<ThuThu> thuThuList;
    private List<LoaiSach> loaiSachList;
    private List<Sach> sachList;

    private PhieuMuonDAO phieuMuonDAO;

    private RecyclerView rcvPhieuMuon;
    private RecyclerView.LayoutManager layoutManager;
    private PhieuMuonAdapter phieuMuonAdapter;
    private FloatingActionButton fab;

    public PhieuMuonFragment(List<PhieuMuon> phieuMuonList, List<ThanhVien> thanhVienList, List<ThuThu> thuThuList, List<LoaiSach> loaiSachList, List<Sach> sachList) {
        this.phieuMuonList = phieuMuonList;
        this.thanhVienList = thanhVienList;
        this.thuThuList = thuThuList;
        this.loaiSachList = loaiSachList;
        this.sachList = sachList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        fab = view.findViewById(R.id.fabAddPhieuMuon);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);

        phieuMuonDAO = new PhieuMuonDAO(view.getContext());
        phieuMuonList = phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(view.getContext(),
                phieuMuonDAO,
                phieuMuonList,
                thanhVienList,
                thuThuList,
                loaiSachList,
                sachList);
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaglogCreate();
            }

            private void openDiaglogCreate() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_view_create_phieu_muon, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                Spinner spnSach, spnThanhVien, spnThuThu;
                Button btnThem, btnHuy;

                spnSach = view.findViewById(R.id.spnSach_dialogCreatePhieuMuon);
                spnThanhVien = view.findViewById(R.id.spnThanhVien_dialogCreatePhieuMuon);
                spnThuThu = view.findViewById(R.id.spnThuThu_dialogCreatePhieuMuon);
                btnThem = view.findViewById(R.id.btnSua_dialogCreatePhieuMuon);
                btnHuy = view.findViewById(R.id.btnQuayLai_dialogCreatePhieuMuon);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPhieuMuon();
                    }

                    private void addPhieuMuon() {
                        //TODO code add PhieuMuon to database
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }
}