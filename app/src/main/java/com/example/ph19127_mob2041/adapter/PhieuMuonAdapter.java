package com.example.ph19127_mob2041.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.PhieuMuon;

import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    Context context;
    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.phieuMuonList = phieuMuonList;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_phieu_muon, parent, false);
        PhieuMuonViewHolder phieuMuonViewHolder = new PhieuMuonViewHolder(view);
        return phieuMuonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        holder.tvMaPhieuMuon.setText(phieuMuon.getMaPhieuMuon());
        holder.tvMaSach.setText(phieuMuon.getMaSach());
        holder.tvMaThanhVien.setText(phieuMuon.getMaThanhVien());
        holder.tvMaThuThu.setText(phieuMuon.getMaThuThu());

        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO viết mã update
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaPhieuMuon, tvMaThanhVien, tvMaSach, tvMaThuThu;
        ImageView ivIcon, ivUpdate, ivDelete;
        CardView cardViewPhieuMuon;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuMuon = itemView.findViewById(R.id.tvMaPhieuMuon);
            tvMaThanhVien = itemView.findViewById(R.id.tvMaThanhVien);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvMaThuThu = itemView.findViewById(R.id.tvMaThuThu);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivUpdate = itemView.findViewById(R.id.ivUpdate);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            cardViewPhieuMuon = itemView.findViewById(R.id.cardViewPhieuMuon);
        }
    }
}
