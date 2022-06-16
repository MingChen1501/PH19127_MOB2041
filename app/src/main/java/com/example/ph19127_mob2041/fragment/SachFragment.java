package com.example.ph19127_mob2041.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFormatException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.SachAdapter;
import com.example.ph19127_mob2041.dao.SachDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SachFragment extends Fragment {

    private List<Sach> sachList;
    private List<Sach> sachByLoaiSachList;
    private List<LoaiSach> loaiSachList;

    private SachDAO sachDAO;

    private RecyclerView rcvSach;
    private RecyclerView.LayoutManager layoutManager;
    private SachAdapter sachAdapter;
    private FloatingActionButton fab;

    public SachFragment(List<Sach> sachList, List<LoaiSach> loaiSachList, SachDAO sachDAO) {
        this.loaiSachList = loaiSachList;
        this.sachList = sachList;
        this.sachDAO = sachDAO;
        this.sachByLoaiSachList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvSach = view.findViewById(R.id.rcv_SachFragment_showSachList);
        fab = view.findViewById(R.id.fab_SachFragment_addSach);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvSach.setLayoutManager(layoutManager);

        sachList.clear();
        sachList.addAll(sachDAO.getAll());
        sachAdapter = new SachAdapter(view.getContext(),
                sachList,
                loaiSachList,
                sachDAO);
        rcvSach.setAdapter(sachAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaglogCreate();
            }

            private void openDiaglogCreate() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.dialog_view_sach_create, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                Spinner spnLoaiSach;
                EditText etMaSach, etTieuDe, etTacGia, etGiaSach;
                Button btnThem, btnHuy;

                spnLoaiSach = view.findViewById(R.id.spn_dialogThemSach_maLoaiSach);
                etMaSach = view.findViewById(R.id.et_dialogThemSach_maSach);
                etTieuDe = view.findViewById(R.id.et_dialogThemSach_tieuDeSach);
                etTacGia = view.findViewById(R.id.et_dialogThemSach_tacGiaSach);
                etGiaSach = view.findViewById(R.id.et_dialogThemSach_giaSach);
                btnThem = view.findViewById(R.id.btn_dialogThemSach_themMoi);
                btnHuy = view.findViewById(R.id.btn_dialogThemSach_quayLai);

                generateId(etMaSach);
                ArrayAdapter<LoaiSach> spnLoaiSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        loaiSachList
                );
                spnLoaiSach.setAdapter(spnLoaiSachAdapter);
                spnLoaiSach.setSelection(0);
                /*spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sachByLoaiSachList.clear();
                        LoaiSach loaiSachSelected = (LoaiSach) spnLoaiSach.getSelectedItem();
                        for (Sach sach : sachList) {
                            if (sach.getMaLoaiSach().equals(loaiSachSelected.getMaLoaiSach()))
                                sachByLoaiSachList.add(sach);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //TODO không biết làm gì. có thể set item cũ vào nếu không select item nào
                    }
                });*/

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addSach();
                    }

                    private void addSach() {
                        //TODO code add PhieuMuon to database
                        try {
                            if (loaiSachList.isEmpty()) throw new NullPointerException("loaiSach");
                            String maLoaiSach = ((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach();
                            String maSach = etMaSach.getText().toString().substring(1);
                            String tieuDe = etTieuDe.getText().toString();
                            String tacGia = etTacGia.getText().toString();
                            if (tieuDe.isEmpty()) throw new NullPointerException("tieuDe");
                            if (tacGia.isEmpty()) throw new NullPointerException("tacGia");
                            double donGia = Double.parseDouble(etGiaSach.getText().toString());

                            Sach sachTemp = new Sach(maSach, maLoaiSach, tieuDe, tacGia, donGia);
                            if (sachDAO.insert(sachTemp) != -1) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                sachList.clear();
                                sachList.addAll(sachDAO.getAll());
                                dialog.dismiss();
                                sachAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NullPointerException e) {
                            if (e.getMessage().equals("loaiSach"))
                                Toast.makeText(getContext(), "Loại sách đang chưa có, hãy tạo loại sách trước", Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("tieuDe"))
                                Toast.makeText(getContext(), "Tiêu đề không được để trống", Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("tacGia"))
                                Toast.makeText(getContext(), "Tác giả không được để trống", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "giá sách không được để trống", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("SachFragment", "addSach: lỗi không xác định");
                            e.printStackTrace();
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

            private void generateId(EditText etMaSach) {
                int id = 1;
                try {
                    id = Integer.parseInt(sachList.get(sachList.size() - 1).getMaSach()) + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    etMaSach.setText("#" + id);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }
}