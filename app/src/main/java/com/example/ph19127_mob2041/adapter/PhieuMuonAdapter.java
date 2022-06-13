package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;

    private List<PhieuMuon> phieuMuonList;
    private List<ThanhVien> thanhVienList;
    private List<ThuThu> thuThuList;
    private List<LoaiSach> loaiSachList;
    private List<Sach> sachListByLoaiSach;
    private List<Sach> sachList;
    private String mThuThuId;
//    private List<Sach> sachListByLoaiSach;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
    }

    public PhieuMuonAdapter(Context context,
                            PhieuMuonDAO phieuMuonDAO,
                            List<PhieuMuon> phieuMuonList,
                            List<ThanhVien> thanhVienList,
                            List<ThuThu> thuThuList,
                            List<LoaiSach> loaiSachList,
                            List<Sach> sachList, String userId) {
        this.context = context;
        this.phieuMuonDAO = phieuMuonDAO;
        this.phieuMuonList = phieuMuonList;
        this.thanhVienList = thanhVienList;
        this.thuThuList = thuThuList;
        this.loaiSachList = loaiSachList;
        this.sachList = sachList;
        this.mThuThuId = userId;
        this.sachListByLoaiSach = new ArrayList<>();
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        Sach sach = (sachList.get(sachList.indexOf(new Sach(phieuMuon.getMaSach()))));
        LoaiSach loaiSach = (loaiSachList.get(loaiSachList.indexOf(new LoaiSach(sach.getMaLoaiSach())))) ;
        holder.tvMaPhieuMuon.setText("Mã phiếu: #" + phieuMuon.getMaPhieuMuon());
        holder.tvMaSach.setText("Tiêu đề sách: " + sach.getTieuDe());
        holder.tvLoaiSach.setText("Loại sách: " + loaiSach.getTenLoaiSach());
        holder.tvMaThanhVien.setText("Người mượn: " + phieuMuon.getMaThanhVien());
        holder.tvMaThuThu.setText("Người tạo: " + phieuMuon.getMaThuThu());
        holder.tvNgayMuon.setText(simpleDateFormat.format(phieuMuon.getNgayMuon()));
        if (phieuMuon.isDaTra()) {
            holder.tvTienTrangThai.setText("Tiền thuê: "+ sach.getDonGia() + "\nĐã trả");
            holder.tvTienTrangThai.setTextColor(Color.GREEN);
        } else {
            holder.tvTienTrangThai.setText("Tiền thuê: "+ sach.getDonGia() + "\nChưa trả");
            holder.tvTienTrangThai.setTextColor(Color.RED);
        }

        holder.cardViewPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(phieuMuon);
            }

            private void openDialogUpdate(PhieuMuon phieuMuon) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_view_phieu_muon_update, null);

                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();


                Switch swTrangThai;
                TextView tvGiaThue, tvNgayMuon;
                EditText etMaPhieuMuon;
                Spinner spnLoaiSach, spnSach, spnThanhVien;
                Button btnSua, btnHuy;

                tvNgayMuon = view.findViewById(R.id.tv_dialogUpdatePhieuMuon_ngayMuon);
                tvGiaThue = view.findViewById(R.id.tv_dialogUpdatePhieuMuon_giaSach);

                etMaPhieuMuon = view.findViewById(R.id.et_dialogUpdatePhieuMuon_phieuMuon);

                spnLoaiSach = view.findViewById(R.id.spn_dialogUpdatePhieuMuon_loaiSach);
                spnSach = view.findViewById(R.id.spn_dialogUpdatePhieuMuon_sach);
                spnThanhVien = view.findViewById(R.id.spn_dialogUpdatePhieuMuon_thanhVien);

                swTrangThai = view.findViewById(R.id.sw_dialogUpdatePhieuMuon_daTraSach);

                btnSua = view.findViewById(R.id.btn_dialogUpdatePhieuMuon_sua);
                btnHuy = view.findViewById(R.id.btn_dialogUpdatePhieuMuon_quayLai);
                /*
                * tvNgayMuon use MaterialDatePicker*/
                String NgayMuon = simpleDateFormat.format(phieuMuon.getNgayMuon());
                StringBuilder mesageTvNgayMuon = new StringBuilder().append("Ngày tạo: ").append(NgayMuon);
                tvNgayMuon.setText(mesageTvNgayMuon);
                MaterialDatePicker.Builder materialDatePickerBuilder = MaterialDatePicker.Builder.datePicker();
                materialDatePickerBuilder.setTitleText("Sửa ngày tạo phiếu mượn");
                MaterialDatePicker materialDatePicker = materialDatePickerBuilder.build();
                tvNgayMuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(((FragmentActivity)context).getSupportFragmentManager(),
                                "METARIAL DATE PICKER ADAPTER CARDVIEW FRM PHIEU MUON");
                    }
                });
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long)selection);
                        String date = simpleDateFormat.format(calendar.getTime());
                        tvNgayMuon.setText("Ngày tạo: " + date);
                    }
                });
                /*
                * genarate IDPhieuMuon autoincrement
                * */
                genarateMaPhieuMuon(etMaPhieuMuon);
                /*
                * spnSach
                * */
                ArrayAdapter<Sach> spnSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        sachListByLoaiSach
                );
                spnSach.setAdapter(spnSachAdapter);
                Sach sachTemp = sachList.get(sachList.indexOf(new Sach(phieuMuon.getMaSach())));
                spnSach.setSelection(sachListByLoaiSach.indexOf(sachTemp));
                spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvGiaThue.setText(
                                String.valueOf(
                                        ((Sach)spnSach.getSelectedItem())
                                                .getDonGia()
                                )
                        );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                /*
                * spnLoaiSach
                * */
                ArrayAdapter<LoaiSach> spnLoaiSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        loaiSachList
                );
                spnLoaiSach.setAdapter(spnLoaiSachAdapter);
                spnLoaiSach.setSelection(loaiSachList.indexOf(new LoaiSach(sachTemp.getMaLoaiSach())));
                spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        sachListByLoaiSach.clear();
                        sachListByLoaiSach.addAll(sachList.stream().filter(sach1 ->
                                sach1.getMaLoaiSach().equals(((LoaiSach)spnLoaiSach.getSelectedItem())
                                        .getMaLoaiSach())
                        ).collect(Collectors.toList()));
                        spnSachAdapter.notifyDataSetChanged();
                        try {
                            spnSach.setSelection(0);
                            tvGiaThue.setText(
                                    String.valueOf(
                                            ((Sach)spnSach.getSelectedItem())
                                                    .getDonGia()
                                    )
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




                /*
                * spnThanhVien*/
                ArrayAdapter<ThanhVien> spnThanhVienAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        thanhVienList
                );

                spnThanhVien.setAdapter(spnThanhVienAdapter);
                spnThanhVien.setSelection(thanhVienList.indexOf(new ThanhVien(phieuMuon.getMaThanhVien())));

                swTrangThai.setChecked(phieuMuon.isDaTra());
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phieuMuon.setDaTra(swTrangThai.isChecked());
                        phieuMuon.setMaSach(
                                ((Sach)spnSach.getSelectedItem()).getMaSach());
                        phieuMuon.setMaThanhVien(
                                ((ThanhVien)spnThanhVien.getSelectedItem()).getMaThanhVien());
                        phieuMuon.setMaThuThu(mThuThuId);
                        try {
                            phieuMuon.setNgayMuon(simpleDateFormat.parse((tvNgayMuon.getText().toString()).substring(10)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        phieuMuonDAO.update(phieuMuon);
                        phieuMuonList.clear();
                        phieuMuonList.addAll(phieuMuonDAO.getAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            private void genarateMaPhieuMuon(EditText etMaPhieuMuon) {
                int id = 0;
                try {
                    id = Integer.parseInt(phieuMuonList.get(phieuMuonList.size() - 1).getMaPhieuMuon());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    etMaPhieuMuon.setText("#" + id);
                }
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Phiếu Mượn").setMessage("Bạn chắc chắn muốn xóa phiếu mượn này");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (phieuMuonDAO.delete(phieuMuon) != 0) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            phieuMuonList.clear();
                            phieuMuonList.addAll(phieuMuonDAO.getAll());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                builder.setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO làm gì đó
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaPhieuMuon, tvMaThanhVien, tvMaSach, tvMaThuThu, tvNgayMuon, tvTienTrangThai, tvLoaiSach;
        ImageView ivIcon, ivDelete;
        CardView cardViewPhieuMuon;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuMuon = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_maPhieuMuon);
            tvMaThanhVien = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_tenThanhVien);
            tvMaSach = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_tenSach);
            tvLoaiSach = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_LoaiSach);
            tvMaThuThu = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_tenThuThu);
            tvNgayMuon = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_ngayMuonSach);
            tvTienTrangThai = itemView.findViewById(R.id.tv_PhieuMuonFragment_viewItemPhieuMuon_giaTienVaTrangThai);

            ivIcon = itemView.findViewById(R.id.iv_PhieuMuonFragment_viewItemPhieuMuon_icon);
            ivDelete = itemView.findViewById(R.id.iv_PhieuMuonFragment_viewItemPhieuMuon_deleteItem);

            cardViewPhieuMuon = itemView.findViewById(R.id.cardView_PhieuMuonFragment_viewItemPhieuMuon);
        }
    }
}
