package com.example.ph19127_mob2041.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.model.Top;

import java.util.List;

public class SachTopAdapter extends RecyclerView.Adapter<SachTopAdapter.ViewHolder>{
    private Context context;
    private List<Top> topRecord;

    public SachTopAdapter(List<Top> topRecord) {
        this.topRecord = topRecord;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext().getApplicationContext())
                .inflate(R.layout.view_item_sach_top_10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Top element = topRecord.get(position);
        holder.tvIndex.setText("TOP: " + (position + 1));
        holder.tvId.setText("Mã sách" + element.getId());
        holder.tvTieuDe.setText("Tiêu đề: " + element.getTitle());
        holder.tvTacGia.setText("Tác giả: " + element.getAuthor());
        holder.tvGia.setText("Mã sách: " + element.getCost());
        holder.tvSoLuong.setText("Số lượng: " + element.getAmount());
//        holder.tvIndex.setText("Mã sách" + element.getId());
    }

    @Override
    public int getItemCount() {
        return topRecord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvId, tvTieuDe, tvTenLoai, tvTacGia, tvGia, tvSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_topNumber);
            tvId = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_maSach);
            tvTieuDe = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_tieuDe);
            tvTenLoai = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_tenLoai);
            tvTacGia = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_tacGia);
            tvGia = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_giaThue);
            tvSoLuong = itemView.findViewById(R.id.tv_TopFragment_viewItemSachTop10_soLuongDaThue);
        }
    }
}
