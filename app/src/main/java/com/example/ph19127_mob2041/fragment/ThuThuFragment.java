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
import com.example.ph19127_mob2041.adapter.ThuThuAdapter;
import com.example.ph19127_mob2041.dao.ThuThuDAO;
import com.example.ph19127_mob2041.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ThuThuFragment extends Fragment {

    private List<ThuThu> mThuThuList;
    private ThuThuDAO mThuThuDao;
    private ThuThuAdapter mThuThuAdapter;
    private RecyclerView mRcvThuThu;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;



    public ThuThuFragment(List<ThuThu> thuThuList, ThuThuDAO thuThuDAO) {
        this.mThuThuList = thuThuList;
        this.mThuThuDao = thuThuDAO;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRcvThuThu = view.findViewById(R.id.rcv_ThuThuFragment_showThuThuList);
        mFab = view.findViewById(R.id.fab_ThuThuFragment_addThuThu);

        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRcvThuThu.setLayoutManager(mLayoutManager);

        mThuThuAdapter = new ThuThuAdapter(view.getContext(),
                mThuThuList,
                mThuThuDao);

        mRcvThuThu.setAdapter(mThuThuAdapter);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaglogCreate();
            }

            private void openDiaglogCreate() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_view_thu_thu_create, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etTenDangNhap, etMatKhau, etMatKhau2, etTen, etSdt;
                Button btnCreate, btnCancel;
                etTen = view.findViewById(R.id.et_dialogThemThuThu_tenThuThu);
                etTenDangNhap = view.findViewById(R.id.et_dialogThemThuThu_maThuThu);
                etMatKhau = view.findViewById(R.id.et_dialogThemThuThu_matKhau);
                etMatKhau2 = view.findViewById(R.id.et_dialogThemThuThu_matKhau2);
                etSdt = view.findViewById(R.id.et_dialogThemThuThu_soDienThoai);

                btnCreate = view.findViewById(R.id.btn_dialogThemThuThu_create);
                btnCancel = view.findViewById(R.id.btn_dialogThemThuThu_cancel);

                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createThuThu();
                    }

                    private void createThuThu() {
                        String id, password, password2, name, phoneNumber;
                        id = etTenDangNhap.getText().toString();
                        password = etMatKhau.getText().toString();
                        password2 = etMatKhau2.getText().toString();
                        name = etTen.getText().toString();
                        phoneNumber = etSdt.getText().toString();

                        if (!password.equals(password2)) {
                            etMatKhau.setError("mat khau khong giong nhau");
                            etMatKhau2.setError("mat khau khong giong nhau");
                            return;
                        }
                        ThuThu thuThu = new ThuThu(id, password, name, phoneNumber);
                        Log.d("thuthu", thuThu.toString());
                        if (mThuThuDao.insert(thuThu) != -1) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            mThuThuList.clear();
                            mThuThuList.addAll(mThuThuDao.getAll());
                            //TODO mThuThuList.add(ThuThu);
                            dialog.dismiss();
                            mThuThuAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancel.setOnClickListener(v -> dialog.dismiss());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu_thu, container, false);
    }
}