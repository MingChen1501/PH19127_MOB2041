package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.ThanhVienDAO;
import com.example.ph19127_mob2041.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienHolder> {
    private final Context mContext;
    private ThanhVienDAO mthanhVienDAO;
    private List<ThanhVien> mthanhVienList;
//    private List<Sach> sachListByLoaiSach;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList, ThanhVienDAO thanhVienDAO) {
        this.mContext = context;
        this.mthanhVienDAO = thanhVienDAO;
        this.mthanhVienList = thanhVienList;
    }

    @NonNull
    @Override
    public ThanhVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_item_thanh_vien, parent, false);
        return new ThanhVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienHolder holder, int position) {
        ThanhVien thanhVien = mthanhVienList.get(position);
        holder.tvMaThanhVien.setText("ID: " + thanhVien.getMaThanhVien());
        holder.tvTenThanhVien.setText("Tên: " + thanhVien.getTenThanhVien());
        holder.tvSoDienThoai.setText("SĐT: " + thanhVien.getSoDienThoai());
        holder.cardViewThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(thanhVien);
            }

            private void openDialogUpdate(ThanhVien thanhVien) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_view_thanh_vien_update, null);

                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etMaThanhVien, etTenThanhVien, etSoDienThoai;
                Button btnSua, btnHuy;


                etMaThanhVien = view.findViewById(R.id.et_dialogSuaThanhVien_maThanhVien);
                etTenThanhVien = view.findViewById(R.id.et_dialogSuaThanhVien_tenThanhVien);
                etSoDienThoai = view.findViewById(R.id.et_dialogSuaThanhVien_soDienThoai);
                btnSua = view.findViewById(R.id.btn_dialogSuaThanhVien_sua);
                btnHuy = view.findViewById(R.id.btn_dialogSuaThanhVien_quayLai);

                etMaThanhVien.setText(thanhVien.getMaThanhVien());
                etTenThanhVien.setText(thanhVien.getTenThanhVien());
                etSoDienThoai.setText(thanhVien.getSoDienThoai());


                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (etMaThanhVien.getText().toString().isEmpty()) throw new IllegalArgumentException("id");
                            if (etTenThanhVien.getText().toString().isEmpty()) throw new IllegalArgumentException("ten");
                            if (etSoDienThoai   .getText().toString().isEmpty()) throw new IllegalArgumentException("sdt");
                            thanhVien.setTenThanhVien(etTenThanhVien.getText().toString());
                            thanhVien.setSoDienThoai(etSoDienThoai.getText().toString());

                            if (mthanhVienDAO.update(thanhVien) > 0) {
                                Toast.makeText(mContext, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                mthanhVienList.clear();
                                mthanhVienList.addAll(mthanhVienDAO.getAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(mContext, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                //TODO validate ...
                            }
                        } catch (IllegalArgumentException e) {
                            if (e.getMessage().equals("id")) {
                                Toast.makeText(mContext, "chưa có id", Toast.LENGTH_SHORT).show();

                            }
                            if (e.getMessage().equals("ten")) {
                                Toast.makeText(mContext, "tên không được bỏ trống", Toast.LENGTH_SHORT).show();

                            }
                            if (e.getMessage().equals("sdt")) {
                                Toast.makeText(mContext, "số điện thoại không được bỏ trống", Toast.LENGTH_SHORT).show();

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
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xóa thành viên").setMessage("Xóa " + thanhVien.toString());

                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        try {
                            if (mthanhVienDAO.delete(thanhVien) != 0) {
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                mthanhVienList.clear();
                                mthanhVienList.addAll(mthanhVienDAO.getAll());
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Không thể xóa thành viên" +
                                    " vì đã tồn tại những phiếu mượn liên quan tới thành viên này",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setPositiveButton("Quay Lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO làm gì đó
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mthanhVienList.size();
    }

    public class ThanhVienHolder extends RecyclerView.ViewHolder{
        TextView tvMaThanhVien, tvTenThanhVien, tvSoDienThoai;
        ImageView ivIcon, ivDelete;
        CardView cardViewThanhVien;

        public ThanhVienHolder(@NonNull View itemView) {
            super(itemView);
            tvMaThanhVien = itemView.findViewById(R.id.tv_ThanhVienFragment_ThanhVienViewItem_maThanhVien);
            tvTenThanhVien = itemView.findViewById(R.id.tv_ThanhVienFragment_ThanhVienViewItem_tenThanhVien);
            tvSoDienThoai = itemView.findViewById(R.id.tv_ThanhVienFragment_ThanhVienViewItem_soDienThoai);

            ivIcon = itemView.findViewById(R.id.iv_ThanhVienFragment_ThanhVienViewItem_icon);
            ivDelete = itemView.findViewById(R.id.iv_ThanhVienFragment_ThanhVienViewItem_deleteItem);

            cardViewThanhVien = itemView.findViewById(R.id.cardView_ThanhVienFragment_ThanhVienViewItem);
        }
    }
}
