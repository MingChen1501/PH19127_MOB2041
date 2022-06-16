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
    private final String TAG = getClass().getSimpleName();
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
        rcvPhieuMuon = view.findViewById(R.id.rcv_PhieuMuonFragment_showPhieuMuonList);
        fab = view.findViewById(R.id.fab_PhieuMuonFragment_AddPhieuMuon);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);

        phieuMuonList = phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(view.getContext(),
                phieuMuonDAO,
                phieuMuonList,
                thanhVienList,
                thuThuList,
                loaiSachList,
                sachList,
                userId);
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

                etPhieuMuon = view.findViewById(R.id.et_dialogCreatePhieuMuon_maPhieuMuon);
                tvGiaThue = view.findViewById(R.id.tv_dialogCreatePhieuMuon_giaSach);
                tvNgayMuon = view.findViewById(R.id.tv_dialogCreatePhieuMuon_ngayMuon);
                spnSach = view.findViewById(R.id.spn_dialogCreatePhieuMuon_sach);
                spnLoaiSach = view.findViewById(R.id.spn_dialogCreatePhieuMuon_loaiSach);
                spnThanhVien = view.findViewById(R.id.spn_dialogCreatePhieuMuon_thanhVien);
                swDaTra = view.findViewById(R.id.sw_dialogCreatePhieuMuon_daTraSach);
                btnThem = view.findViewById(R.id.btn_dialogCreatePhieuMuon_sua);
                btnHuy = view.findViewById(R.id.btn_dialogCreatePhieuMuon_quayLai);

                generateMaPhieuMuon(etPhieuMuon);
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
                        //TODO bug khi loại sách empty. spnSach setSelection null
                        spnSach.setEnabled(true);
                        sachListByLoaiSach.clear();
                        for (Sach sach : sachList) {
                            if (sach.getMaLoaiSach().equals(
                                    ((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach()))
                                sachListByLoaiSach.add(sach);
                        }

                        spnSachAdapter.notifyDataSetChanged();
                        spnSach.setSelection(0);
                        if (sachListByLoaiSach.isEmpty()) {
                            tvGiaThue.setText("0");
                        } else {
                            tvGiaThue
                                    .setText(String.valueOf(((Sach)spnSach.getSelectedItem()).getDonGia()));
                        }

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
                        tvNgayMuon.setOnClickListener(null);
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
                        addPhieuMuon();
                    }

                    private void addPhieuMuon() {
                        //TODO code add PhieuMuon to database
                        try {
                            String idPhieuMuon = etPhieuMuon.getText().toString().substring(1);
                            if (loaiSachList.isEmpty()) throw new Exception("loaiSach");
                            if (sachListByLoaiSach.isEmpty()) throw new Exception("sach");
                            if (thanhVienList.isEmpty()) throw new Exception("thanhVien");
                            String idSach = ((Sach)spnSach.getSelectedItem()).getMaSach();
                            String idThanhVien = ((ThanhVien)spnThanhVien.getSelectedItem()).getMaThanhVien();
                            String idThuThu = userId;
                            boolean isDaTra = swDaTra.isChecked();
                            //TODO xử lí lại ngayMuon
                            Date ngayMuon = new SimpleDateFormat("dd-MM-yyyy").
                                    parse((tvNgayMuon.getText().toString()).substring(10));
                            if (tvNgayMuon.getText().toString().substring(10).isEmpty()) throw new Exception("ngayMuon");
//                            if (idSach.isEmpty()) throw new Exception("Sach");
//                            if (idThanhVien.isEmpty()) throw new Exception("thanhVien");
                            if (idThuThu.isEmpty()) throw new Exception("thuThu");

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
                        } catch (IndexOutOfBoundsException e) {
                            Log.d(TAG, "addPhieuMuon: " + (tvNgayMuon.getText().toString()).length());
                            Log.d(TAG, "addPhieuMuon: id substring() OR date subString()");
                            Toast.makeText(getContext(), "Chưa chọn ngày mượn", Toast.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            Log.d(TAG, "addPhieuMuon: Date ngayMuon khong the parse");
                            Toast.makeText(getContext(), "lỗi ngày mượn", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            switch (e.getMessage()) {
                                case "loaiSach":
                                    Log.d(TAG, "addPhieuMuon: loại sách null");
                                    Toast.makeText(getContext(), "Chưa tạo loại sách", Toast.LENGTH_SHORT).show();
                                    break;
                                case "sach":
                                    Log.d(TAG, "addPhieuMuon: Sach null");
                                    Toast.makeText(getContext(), "Chưa tạo sách", Toast.LENGTH_SHORT).show();
                                    break;
                                case "thanhVien":
                                    Log.d(TAG, "addPhieuMuon: ThanhVien null");
                                    Toast.makeText(getContext(), "Chưa tạo thành viên", Toast.LENGTH_SHORT).show();
                                    break;
                                case "thuThu":
                                    Log.d(TAG, "addPhieuMuon: ThuThu null");
                                    Toast.makeText(getContext(), "Chưa tạo thủ thư", Toast.LENGTH_SHORT).show();
                                    break;
                                case "ngayMuon":
                                    Log.d(TAG, "addPhieuMuon: ngayMuon null");
                                    Toast.makeText(getContext(), "Chưa chọn ngày tạo phiếu mượn", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Log.d(TAG, "addPhieuMuon: khum xác định");
                            }
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

            private void generateMaPhieuMuon(EditText etPhieuMuon) {
                int id = 1;
                try {
                    id = Integer.parseInt(
                            phieuMuonList.get(phieuMuonList.size() - 1).getMaPhieuMuon()) + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    etPhieuMuon.setText("#" + id);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

}