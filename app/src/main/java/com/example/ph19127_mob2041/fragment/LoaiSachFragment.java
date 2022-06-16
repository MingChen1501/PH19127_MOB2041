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
        rcvLoaiSach = view.findViewById(R.id.rcv_LoaiSachFragment_showLoaiSachList);
        fab = view.findViewById(R.id.fab_loaiSachFragment_addLoaiSach);

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

                etMaLoaiSach = view.findViewById(R.id.et_dialogCreateLoaiSach_maLoaiSach);
                etTenLoaiSach = view.findViewById(R.id.et_dialogCreateLoaiSach_tenLoaiSach);
                btnThem = view.findViewById(R.id.btn_dialogCreateLoaiSach_create);
                btnHuy = view.findViewById(R.id.btn_dialogCreatePhieuMuon_cancel);

                generateId(etMaLoaiSach);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLoaiSach();
                    }

                    private void addLoaiSach() {
                        //TODO code add PhieuMuon to database
                        try {
                            String maLoaiSach = etMaLoaiSach.getText().toString().substring(1);
                            String tenLoaiSach = etTenLoaiSach.getText().toString();
                            if (tenLoaiSach.isEmpty()) throw new NullPointerException("Tên loại sách không được trống");
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
                        } catch (NullPointerException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("LoaiSachFragment", "addLoaiSach: lỗi không xác định");
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

            private void generateId(EditText etMaLoaiSach) {
                int id = 1;
                try {
                    id = Integer.parseInt(
                            loaiSachList.get(loaiSachList.size() - 1).getMaLoaiSach()) + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    etMaLoaiSach.setText("#" + id);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }
}