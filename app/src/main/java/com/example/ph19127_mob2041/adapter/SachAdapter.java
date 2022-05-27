package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.LoaiSachDAO;
import com.example.ph19127_mob2041.dao.SachDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.Sach;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.PhieuMuonViewHolder> {
    private Context context;
    private SachDAO sachDAO;
    private List<Sach> sachList;
    private List<LoaiSach> loaiSachList;
//    private List<Sach> sachListByLoaiSach;

    public SachAdapter(Context context, List<Sach> sachList, List<LoaiSach> loaiSachList, SachDAO sachDAO) {
        this.context = context;
        this.sachDAO = sachDAO;
        this.sachList = sachList;
        this.loaiSachList = loaiSachList;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_sach, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        Sach sach = sachList.get(position);
        String tenLoaiSach = loaiSachList.
                get(loaiSachList.indexOf(new LoaiSach(sach.getMaLoaiSach(), "")))
                .getTenLoaiSach();
        holder.tvTenLoaiSach.setText(tenLoaiSach);
        holder.tvMaSach.setText(sach.getMaSach());
        holder.tvTieuDe.setText(sach.getTieuDe());
        holder.tvTacGia.setText(sach.getTacGia());
        holder.tvDonGia.setText(String.valueOf(sach.getDonGia()));
        holder.cardViewSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(sach);
            }

            private void openDialogUpdate(Sach sach) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_view_sach_update, null);

                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                Spinner spnLoaiSach;
                EditText etMaSach, etTieuDe, etTacGia, etDonGia;
                Button btnSua, btnHuy;


                spnLoaiSach = view.findViewById(R.id.spn_dialogSuaSach_maLoaiSach);

                etMaSach = view.findViewById(R.id.et_dialogSuaSach_maSach);
                etTieuDe = view.findViewById(R.id.et_dialogSuaSach_tieuDeSach);
                etTacGia = view.findViewById(R.id.et_dialogSuaSach_tacGiaSach);
                etDonGia = view.findViewById(R.id.et_dialogSuaSach_giaSach);

                btnSua = view.findViewById(R.id.btn_dialogSuaSach_themMoi);
                btnHuy = view.findViewById(R.id.btn_dialogSuaSach_quayLai);

                ArrayAdapter spnLoaiSachAdapter = new ArrayAdapter(view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        loaiSachList);
                spnLoaiSach.setAdapter(spnLoaiSachAdapter);
                spnLoaiSach.setSelection(loaiSachList.indexOf(new LoaiSach(
                        sach.getMaLoaiSach(), "")
                ));
                etMaSach.setText(sach.getMaSach());
                etTieuDe.setText(sach.getTieuDe());
                etTacGia.setText(sach.getTacGia());
                etDonGia.setText(String.valueOf(sach.getDonGia()));


                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sach.setMaLoaiSach(((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach());

                        sach.setMaSach(etMaSach.getText().toString());
                        sach.setTieuDe(etTieuDe.getText().toString());
                        sach.setTacGia(etTacGia.getText().toString());
                        sach.setDonGia(Double.parseDouble(etDonGia.getText().toString()));

                        if (sachDAO.update(sach) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            sachList.clear();
                            sachList.addAll(sachDAO.getAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            //TODO validate ...
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
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách").setMessage("Xóa \'" + sach.toString() +"\' sẽ xóa các phiếu mượn liên quan"+
                        "\nBạn chắc chắn muốn xóa sách này");
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (sachDAO.delete(sach) != 0) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            sachList.clear();
                            sachList.addAll(sachDAO.getAll());
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
        return sachList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenLoaiSach,
                tvMaSach,
                tvTieuDe,
                tvTacGia,
                tvDonGia;
        ImageView ivIcon, ivDelete;
        CardView cardViewSach;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiSach = itemView.findViewById(R.id.tv_sachAdapter_TenLoaiSach);
            tvMaSach = itemView.findViewById(R.id.tv_sachAdapter_maSach);
            tvTieuDe = itemView.findViewById(R.id.tv_sachAdapter_tieuDe);
            tvTacGia = itemView.findViewById(R.id.tv_sachAdapter_tacGia);
            tvDonGia = itemView.findViewById(R.id.tv_sachAdapter_donGia);


            ivIcon = itemView.findViewById(R.id.iv_sachAdapter_icon);
            ivDelete = itemView.findViewById(R.id.iv_sachAdapter_xoa);

            cardViewSach = itemView.findViewById(R.id.cardviewSach);
        }
    }
}
