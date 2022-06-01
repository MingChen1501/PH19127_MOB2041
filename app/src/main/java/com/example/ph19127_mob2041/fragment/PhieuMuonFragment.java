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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.PhieuMuonAdapter;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    private String userId;


    public PhieuMuonFragment(List<PhieuMuon> phieuMuonList,
                             List<ThanhVien> thanhVienList,
                             List<ThuThu> thuThuList,
                             List<LoaiSach> loaiSachList,
                             List<Sach> sachList,
                             PhieuMuonDAO phieuMuonDAO, String user) {
        this.phieuMuonList = phieuMuonList;
        this.thanhVienList = thanhVienList;
        this.thuThuList = thuThuList;
        this.loaiSachList = loaiSachList;
        this.sachList = sachList;
        this.phieuMuonDAO = phieuMuonDAO;
        this.userId = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        fab = view.findViewById(R.id.fabAddPhieuMuon);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_view_phieu_muon_create, null);
                alertDialogBuilder.setView(view);
                Dialog dialog = alertDialogBuilder.create();
                dialog.show();

                TextView tvNgayMuon, tvGiaThue;
                EditText etPhieuMuon;
                Spinner spnLoaiSach, spnSach, spnThanhVien;
                Switch swDaTra;
                Button btnThem, btnHuy;
                List<Sach> sachListByLoaiSach = new ArrayList<>();
                sachListByLoaiSach.addAll(sachList);

                etPhieuMuon = view.findViewById(R.id.etPhieuMuon_dialogCreatePhieuMuon);
                tvGiaThue = view.findViewById(R.id.tvGia_dialogCreatePhieuMuon);
                tvNgayMuon = view.findViewById(R.id.tvNgayMuon_dialogCreatePhieuMuon);
                spnSach = view.findViewById(R.id.spnSach_dialogCreatePhieuMuon);
                spnLoaiSach = view.findViewById(R.id.spnLoaiSach_dialogCreatePhieuMuon);
                spnThanhVien = view.findViewById(R.id.spnThanhVien_dialogCreatePhieuMuon);
                swDaTra = view.findViewById(R.id.swDaTraSach_dialogCreatePhieuMuon);
                btnThem = view.findViewById(R.id.btnSua_dialogCreatePhieuMuon);
                btnHuy = view.findViewById(R.id.btnQuayLai_dialogCreatePhieuMuon);

                ArrayAdapter<Sach> spnSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        sachListByLoaiSach
                );
                spnSach.setAdapter(spnSachAdapter);
                spnSach.setSelection(0);
                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvGiaThue
                                .setText(String.valueOf(((Sach)spnSach.getSelectedItem()).getDonGia()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        tvGiaThue
                                .setText(String.valueOf(((Sach)spnSach.getSelectedItem()).getDonGia()));
                    }
                });

                ArrayAdapter<LoaiSach> spnLoaiSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        loaiSachList
                );
                spnLoaiSach.setAdapter(spnLoaiSachAdapter);
                spnLoaiSach.setSelection(0);
                spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spnSach.setEnabled(true);
                        sachListByLoaiSach.clear();
                        for (Sach sach : sachList) {
                            if (sach.getMaLoaiSach().equals(
                                    ((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach()))
                                sachListByLoaiSach.add(sach);
                        }
                        spnSachAdapter.notifyDataSetChanged();
                        tvGiaThue
                                .setText(String.valueOf(((Sach)spnSach.getSelectedItem()).getDonGia()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });





                //create materialDatePicker
                MaterialDatePicker.Builder materialDatePickerBuilder = MaterialDatePicker.Builder.datePicker();
                materialDatePickerBuilder.setTitleText("Chọn ngày tạo phiếu mượn");
                MaterialDatePicker materialDatePicker = materialDatePickerBuilder.build();
                tvNgayMuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDatePickerDialog();
                    }

                    private void openDatePickerDialog() {
                        materialDatePicker.show(getActivity().getSupportFragmentManager()
                                , "METARIAL DATE PICKER FRM PHIEU MUON");
                    }
                });
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long)selection);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate  = simpleDateFormat.format(calendar.getTime());
                        tvNgayMuon.setText("Ngày tạo: " + formattedDate);
                    }
                });
                ArrayAdapter<ThanhVien> spnThanhVienAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        thanhVienList
                );
                spnThanhVien.setAdapter(spnThanhVienAdapter);
                spnThanhVien.setSelection(0);


                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            addPhieuMuon();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    private void addPhieuMuon() throws ParseException {
                        //TODO code add PhieuMuon to database

                        String idPhieuMuon = etPhieuMuon.getText().toString();
                        String idSach = ((Sach)spnSach.getSelectedItem()).getMaSach();
                        String idThanhVien = ((ThanhVien)spnThanhVien.getSelectedItem()).getMaThanhVien();
                        String idThuThu = userId;
                        boolean isDaTra = swDaTra.isChecked();
                        Log.d("isDaTra", String.valueOf(isDaTra));
                        Date ngayMuon = new SimpleDateFormat("dd-MM-yyyy").
                                parse((tvNgayMuon.getText().toString()).substring(10));
                        Log.d("substring", new SimpleDateFormat("dd-MM-yyyy").
                                parse((tvNgayMuon.getText().toString()).substring(10)).toString());

                        PhieuMuon newPhieuMuon = new PhieuMuon(idPhieuMuon,idThanhVien, idSach, idThuThu, ngayMuon, isDaTra);
                        if (phieuMuonDAO.insert(newPhieuMuon) != -1) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            phieuMuonList.clear();
                            phieuMuonList.addAll(phieuMuonDAO.getAll());
                            dialog.dismiss();
                            phieuMuonAdapter.notifyDataSetChanged();
                            Log.d("phieu muon", phieuMuonList.toString());
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
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }
}