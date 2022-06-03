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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.LoaiSachDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.PhieuMuonViewHolder> {
    private Context context;
    private LoaiSachDAO loaiSachDAO;
    private List<LoaiSach> loaiSachList;
//    private List<Sach> sachListByLoaiSach;

    public LoaiSachAdapter(Context context, List<LoaiSach> loaiSachList, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.loaiSachDAO = loaiSachDAO;
        this.loaiSachList = loaiSachList;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_loai_sach, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        LoaiSach loaiSach = loaiSachList.get(position);
        holder.tvMaLoaiSach.setText("Mã Loại: #" + loaiSach.getMaLoaiSach());
        holder.tvTenLoaiSach.setText("Tên: " + loaiSach.getTenLoaiSach());
        holder.cardViewLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(loaiSach);
            }

            private void openDialogUpdate(LoaiSach loaiSach) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_view_loai_sach_update, null);

                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etMaLoaiSach, etTenLoaiSach;
                Button btnSua, btnHuy;


                etMaLoaiSach = view.findViewById(R.id.etMaLoaiSach_dialogUpdateLoaiSach);
                etTenLoaiSach = view.findViewById(R.id.etTenLoaiSach_dialogUpdateLoaiSach);
                btnSua = view.findViewById(R.id.btnCreate_dialogUpdateLoaiSach);
                btnHuy = view.findViewById(R.id.btnCancel_dialogUpdateLoaiSach);

                etMaLoaiSach.setText(loaiSach.getMaLoaiSach());
                etTenLoaiSach.setText(loaiSach.getTenLoaiSach());


                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loaiSach.setTenLoaiSach(etTenLoaiSach.getText().toString());

                        if (loaiSachDAO.update(loaiSach) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            loaiSachList.clear();
                            loaiSachList.addAll(loaiSachDAO.getAll());
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
                builder.setTitle("Xóa Loại Sách").setMessage("Xóa " + loaiSach.toString() + " sẽ xóa theo \nCác sách liên quan \nCác phiếu mượn liên quan" +
                        "\nBạn có chắc chắn sẽ xóa " + loaiSach.toString() + " ?");

                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (loaiSachDAO.delete(loaiSach) != 0) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loaiSachList.clear();
                            loaiSachList.addAll(loaiSachDAO.getAll());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        return loaiSachList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLoaiSach, tvTenLoaiSach;
        ImageView ivIcon, ivDelete;
        CardView cardViewLoaiSach;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLoaiSach = itemView.findViewById(R.id.tvMaLoaiSach_view_item_loai_sach);
            tvTenLoaiSach = itemView.findViewById(R.id.tvTenLoaiSach_view_item_loai_sach);

            ivIcon = itemView.findViewById(R.id.ivIcon_view_item_loai_sach);
            ivDelete = itemView.findViewById(R.id.ivDelete_view_item_loai_sach);

            cardViewLoaiSach = itemView.findViewById(R.id.cardViewLoaiSach);
        }
    }
}
