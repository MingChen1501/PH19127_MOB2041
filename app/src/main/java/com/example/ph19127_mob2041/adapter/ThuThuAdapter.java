package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import com.example.ph19127_mob2041.dao.ThuThuDAO;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.List;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.PhieuMuonViewHolder> {
    private final Context mContext;
    private ThuThuDAO mThuThuDAO;
    private List<ThuThu> mThuThuList;
//    private List<Sach> sachListByLoaiSach;

    public ThuThuAdapter(Context context, List<ThuThu> thuThuList, ThuThuDAO thuThuDAO) {
        this.mContext = context;
        this.mThuThuDAO = thuThuDAO;
        this.mThuThuList = thuThuList;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_thu_thu, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        int indexOfElement = position;
        ThuThu thuThu = mThuThuList.get(position);
        holder.tvId.setText("Mã Thủ thư: " + thuThu.getMaThuThu());
        holder.tvName.setText("Tên: " + thuThu.getHoTen());
        holder.tvPhoneNumber.setText("SĐT: " + thuThu.getSoDienThoai());
        holder.cardViewThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(thuThu);
            }

            private void openDialogUpdate(ThuThu thuThu) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_view_thu_thu_update, null);

                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText etId, etPassword, etPassword2, etName, etNumberPhone;
                Button btnUpdate, btnCancel;


                etId = view.findViewById(R.id.et_dialogSuaThuThu_maThuThu);
                etPassword = view.findViewById(R.id.et_dialogSuaThuThu_matKhau);
                etPassword2 = view.findViewById(R.id.et_dialogSuaThuThu_matKhau2);
                etName = view.findViewById(R.id.et_dialogSuaThuThu_tenThuThu);
                etNumberPhone = view.findViewById(R.id.et_dialogSuaThuThu_soDienThoai);

                btnUpdate = view.findViewById(R.id.btn_dialogSuaThuThu_update);
                btnCancel = view.findViewById(R.id.btn_dialogSuaThuThu_cancel);

                etId.setText(thuThu.getMaThuThu());
                etName.setText(thuThu.getHoTen());
                etNumberPhone.setText(thuThu.getSoDienThoai());


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etPassword2.getText().equals(etPassword.getText())) {
                            etPassword2.setError("mật khẩu không giống");
                            etPassword.setError("mật khẩu không giống");
                            return;
                        }
                        thuThu.setMaThuThu(etId.getText().toString());
                        thuThu.setPassword(etPassword.getText().toString());
                        thuThu.setHoTen(etName.getText().toString());
                        thuThu.setSoDienThoai(etNumberPhone.getText().toString());

                        if (mThuThuDAO.update(thuThu) > 0) {
                            Toast.makeText(mContext, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            mThuThuList.get(indexOfElement).setMaThuThu(thuThu.getMaThuThu());
                            mThuThuList.get(indexOfElement).setHoTen(thuThu.getHoTen());
                            mThuThuList.get(indexOfElement).setPassword(thuThu.getPassword());
                            mThuThuList.get(indexOfElement).setSoDienThoai(thuThu.getSoDienThoai());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Log.d("d", mThuThuList.toString());
                        } else {
                            Toast.makeText(mContext, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            //TODO validate ...
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
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
                builder.setTitle("Xóa thủ thư").setMessage("Xóa " + thuThu.toString() + " sẽ xóa theo\nCác phiếu mượn liên quan" +
                        "\nBạn có chắc chắn sẽ xóa " + thuThu.toString() + " ?");

                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mThuThuDAO.delete(thuThu) != 0) {
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            mThuThuList.remove(thuThu);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        return mThuThuList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvName, tvPhoneNumber;
        ImageView ivIcon, ivDelete;
        CardView cardViewThuThu;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_ThuThuFragment_ThuThuViewItem_maThuThu);
            tvName = itemView.findViewById(R.id.tv_ThuThuFragment_ThuThuViewItem_tenThuThu);
            tvPhoneNumber = itemView.findViewById(R.id.tv_ThuThuFragment_ThuThuViewItem_soDienThoai);

            ivIcon = itemView.findViewById(R.id.iv_ThuThuFragment_ThuThuViewItem_Icon);
            ivDelete = itemView.findViewById(R.id.iv_ThuThuFragment_ThuThuViewItem_deleteItem);

            cardViewThuThu = itemView.findViewById(R.id.cardView_ThuThuFragment_ThuThuViewItem);
        }
    }
}
