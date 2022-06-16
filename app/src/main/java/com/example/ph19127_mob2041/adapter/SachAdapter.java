package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
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
        String tenLoaiSach = "";
        if (! loaiSachList.isEmpty()) {
            tenLoaiSach = loaiSachList.
                    get(loaiSachList.indexOf(new LoaiSach(sach.getMaLoaiSach(), "")))
                    .getTenLoaiSach();
        }
        holder.tvTenLoaiSach.setText("Thể loại: " + tenLoaiSach);
        holder.tvMaSach.setText("Mã: #" + sach.getMaSach());
        holder.tvTieuDe.setText("Tiêu đề: " + sach.getTieuDe());
        holder.tvTacGia.setText("Tác giả: " + sach.getTacGia());
        holder.tvDonGia.setText("Giá thuê: " + sach.getDonGia());
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
                etMaSach.setText("#" + sach.getMaSach());
                etTieuDe.setText(sach.getTieuDe());
                etTacGia.setText(sach.getTacGia());
                etDonGia.setText(String.valueOf(sach.getDonGia()));


                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (loaiSachList.isEmpty()) throw new NullPointerException("loaiSach");
                            sach.setMaLoaiSach(((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach());
                            if (etTieuDe.getText().toString().isEmpty()) throw new NullPointerException("tieuDe");
                            if (etTacGia.getText().toString().isEmpty()) throw new NullPointerException("tacGia");
                            sach.setDonGia(Double.parseDouble(etDonGia.getText().toString()));
                            sach.setTieuDe(etTieuDe.getText().toString());
                            sach.setTacGia(etTacGia.getText().toString());

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
                        } catch (NullPointerException e) {
                            if (e.getMessage().equals("loaiSach"))
                                Toast.makeText(context, "Loại sách đang chưa có, hãy tạo loại sách trước", Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("tieuDe"))
                                Toast.makeText(context, "Tiêu đề không được để trống", Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("tacGia"))
                                Toast.makeText(context, "Tác giả không được để trống", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "giá sách không được để trống", Toast.LENGTH_SHORT).show();
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
                        try {
                            if (sachDAO.delete(sach) != 0) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                sachList.clear();
                                sachList.addAll(sachDAO.getAll());
                                notifyDataSetChanged();

                            } else {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Không thể xóa sách" +
                                    " vì đã tồn tại phiếu mượn liên quan tới cuốn sách này",
                                    Toast.LENGTH_SHORT)
                                    .show();
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
            tvTenLoaiSach = itemView.findViewById(R.id.tv_SachFragment_viewItemSach_tenLoai);
            tvMaSach = itemView.findViewById(R.id.tv_SachFragment_viewItemSach_maSach);
            tvTieuDe = itemView.findViewById(R.id.tv_SachFragment_viewItemSach_tieuDe);
            tvTacGia = itemView.findViewById(R.id.tv_SachFragment_viewItemSach_tacGia);
            tvDonGia = itemView.findViewById(R.id.tv_SachFragment_viewItemSach_donGia);


            ivIcon = itemView.findViewById(R.id.iv_SachFragment_viewItemSach_icon);
            ivDelete = itemView.findViewById(R.id.iv_SachFragment_viewItemSach_deleteItem);

            cardViewSach = itemView.findViewById(R.id.cardView_SachFragment_viewItemSach);
        }
    }
}
