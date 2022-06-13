package com.example.ph19127_mob2041.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.ThanhVienAdapter;
import com.example.ph19127_mob2041.dao.ThanhVienDAO;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ThanhVienFragment extends Fragment {
    private Context mContext;
    private List<ThanhVien> mThanhVienList;
    private ThanhVienAdapter mThanhVienAdapter;
    private ThanhVienDAO mThanhVienDAO;

    private RecyclerView mRcvThanhVien;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;

    public ThanhVienFragment(List<ThanhVien> mNhanVienList, ThanhVienDAO mThanhVienDAO, Context context) {
        this.mContext = context;
        this.mThanhVienList = mNhanVienList;
        this.mThanhVienDAO = mThanhVienDAO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRcvThanhVien = view.findViewById(R.id.rcv_ThanhVienFragment_showThanhVienList);
        mFab = view.findViewById(R.id.fab_ThanhVienFragment_addThanhVien);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRcvThanhVien.setLayoutManager(mLayoutManager);

        mThanhVienAdapter = new ThanhVienAdapter(mContext,
                mThanhVienList,
                mThanhVienDAO);
        mRcvThanhVien.setAdapter(mThanhVienAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreate();
            }

            private void openDialogCreate() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.dialog_view_thanh_vien_create, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etMaThanhVien, etTenThanhVien, etSoDienThoai;
                Button btnThem, btnHuy;

                etMaThanhVien = view.findViewById(R.id.et_dialogThemThanhVien_maThanhVien);
                etTenThanhVien = view.findViewById(R.id.et_dialogThemThanhVien_tenThanhVien);
                etSoDienThoai = view.findViewById(R.id.et_dialogThemThanhVien_soDienThoai);
                btnThem = view.findViewById(R.id.btn_dialogThemThanhVien_themMoi);
                btnHuy = view.findViewById(R.id.btn_dialogThemThanhVien_quayLai);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idThanhVien = etMaThanhVien.getText().toString();
                        String tenThanhVien = etTenThanhVien.getText().toString();
                        String soDienThoai = etSoDienThoai.getText().toString();
                        ThanhVien thanhVienTemp = new ThanhVien(idThanhVien,
                                tenThanhVien,
                                soDienThoai);
                        if (mThanhVienDAO.insert(thanhVienTemp) != -1) {
                            mThanhVienList.clear();
                            mThanhVienList.addAll(mThanhVienDAO.getAll());
                            dialog.dismiss();
                            mThanhVienAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
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
    public void onStop() {
        super.onStop();
        mContext = null;
        mFab.setOnClickListener(null);
    }
}