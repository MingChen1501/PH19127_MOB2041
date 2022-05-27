package com.example.ph19127_mob2041.fragment;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.LoaiSachAdapter;
import com.example.ph19127_mob2041.dao.LoaiSachDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LoaiSachFragment extends Fragment {

    private List<LoaiSach> loaiSachList;

    private LoaiSachDAO loaiSachDAO;

    private RecyclerView rcvLoaiSach;
    private RecyclerView.LayoutManager layoutManager;
    private LoaiSachAdapter loaiSachAdapter;
    private FloatingActionButton fab;

    public LoaiSachFragment(List<LoaiSach> loaiSachList, LoaiSachDAO loaiSachDAO) {
        this.loaiSachList = loaiSachList;
        this.loaiSachDAO = loaiSachDAO;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        fab = view.findViewById(R.id.fabAddLoaiSach);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvLoaiSach.setLayoutManager(layoutManager);

        loaiSachAdapter = new LoaiSachAdapter(view.getContext(),
                loaiSachList,
                loaiSachDAO);
        rcvLoaiSach.setAdapter(loaiSachAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaglogCreate();
            }

            private void openDiaglogCreate() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_view_loai_sach_create, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etMaLoaiSach, etTenLoaiSach;
                Button btnThem, btnHuy;

                etMaLoaiSach = view.findViewById(R.id.etMaLoaiSach_dialogCreateLoaiSach);
                etTenLoaiSach = view.findViewById(R.id.etTenLoaiSach_dialogCreateLoaiSach);
                btnThem = view.findViewById(R.id.btnCreate_dialogCreateLoaiSach);
                btnHuy = view.findViewById(R.id.btnCancel_dialogCreatePhieuMuon);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLoaiSach();
                    }

                    private void addLoaiSach() {
                        //TODO code add PhieuMuon to database
                        String maLoaiSach = etMaLoaiSach.getText().toString();
                        String tenLoaiSach = etTenLoaiSach.getText().toString();
                        LoaiSach newLoaiSach = new LoaiSach(maLoaiSach, tenLoaiSach);
                        if (loaiSachDAO.insert(newLoaiSach) != -1) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            loaiSachList.clear();
                            loaiSachList.addAll(loaiSachDAO.getAll());
                            dialog.dismiss();
                            loaiSachAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
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
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }
}