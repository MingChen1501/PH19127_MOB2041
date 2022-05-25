package com.example.ph19127_mob2041.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.LoaiSach;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.example.ph19127_mob2041.model.ThanhVien;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;

    private List<PhieuMuon> phieuMuonList;
    private List<ThanhVien> thanhVienList;
    private List<ThuThu> thuThuList;
    private List<LoaiSach> loaiSachList;
    private List<Sach> sachList;
//    private List<Sach> sachListByLoaiSach;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
    }

    public PhieuMuonAdapter(Context context,
                            PhieuMuonDAO phieuMuonDAO,
                            List<PhieuMuon> phieuMuonList,
                            List<ThanhVien> thanhVienList,
                            List<ThuThu> thuThuList,
                            List<LoaiSach> loaiSachList,
                            List<Sach> sachList) {
        this.context = context;
        this.phieuMuonDAO = phieuMuonDAO;
        this.phieuMuonList = phieuMuonList;
        this.thanhVienList = thanhVienList;
        this.thuThuList = thuThuList;
        this.loaiSachList = loaiSachList;
        this.sachList = sachList;
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
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        holder.tvMaPhieuMuon.setText(phieuMuon.getMaPhieuMuon());
        holder.tvMaSach.setText(phieuMuon.getMaSach());
        holder.tvMaThanhVien.setText(phieuMuon.getMaThanhVien());
        holder.tvMaThuThu.setText(phieuMuon.getMaThuThu());
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

                Spinner spnLoaiSach, spnSach, spnThanhVien, spnThuThu;
                Button btnSua, btnHuy;

                //spnLoaiSach = view.findViewById(R.id.spnLoaiSach_dialogUpdatePhieuMuon);
                spnSach = view.findViewById(R.id.spnSach_dialogUpdatePhieuMuon);
                spnThanhVien = view.findViewById(R.id.spnThanhVien_dialogUpdatePhieuMuon);
                spnThuThu = view.findViewById(R.id.spnThuThu_dialogUpdatePhieuMuon);
                btnSua = view.findViewById(R.id.btnSua_dialogUpdatePhieuMuon);
                btnHuy = view.findViewById(R.id.btnQuayLai_dialogUpdatePhieuMuon);

                /*ArrayAdapter<LoaiSach> spnLoaiSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        loaiSachList
                );
                spnLoaiSach.setAdapter(spnLoaiSachAdapter);
                spnLoaiSach.setSelection(loaiSachList.indexOf(new Sach(phieuMuon.getMaSach())));
*/
                /*sachListByLoaiSach = sachList.stream().filter(sach ->
                    ((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach().equals(sach.getMaLoaiSach())
                ).collect(Collectors.toList());
                Log.i("sachlist",sachListByLoaiSach.toString());*/

                /*for (Sach sach : sachList) {
                    if (sach.getMaLoaiSach().equals(
                            ((LoaiSach)spnLoaiSach.getSelectedItem()).getMaLoaiSach()))
                        sachListByLoaiSach.add(sach);
                }
                Log.i("sachlist",sachListByLoaiSach.toString());*/
                ArrayAdapter<Sach> spnSachAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        sachList
                );
                spnSach.setAdapter(spnSachAdapter);
                spnSach.setSelection(sachList.indexOf(new Sach(phieuMuon.getMaSach())));

                ArrayAdapter<ThanhVien> spnThanhVienAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        thanhVienList
                );

                spnThanhVien.setAdapter(spnThanhVienAdapter);
                spnThanhVien.setSelection(thanhVienList.indexOf(new ThanhVien(phieuMuon.getMaThanhVien())));


                ArrayAdapter<ThuThu> spnThuThuAdapter = new ArrayAdapter<>(
                        view.getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        thuThuList
                );
                spnThuThu.setAdapter(spnThuThuAdapter);
                spnThuThu.setSelection(thuThuList.indexOf(new ThuThu(phieuMuon.getMaThuThu())));

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phieuMuon.setMaSach(
                                ((Sach)spnSach.getSelectedItem()).getMaSach());
                        phieuMuon.setMaThanhVien(
                                ((ThanhVien)spnThanhVien.getSelectedItem()).getMaThanhVien());
                        phieuMuon.setMaThuThu(
                                ((ThuThu)spnThuThu.getSelectedItem()).getMaThuThu());
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
        ImageView ivIcon, ivDelete;
        CardView cardViewPhieuMuon;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieuMuon = itemView.findViewById(R.id.tvMaPhieuMuon);
            tvMaThanhVien = itemView.findViewById(R.id.tvMaThanhVien);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvMaThuThu = itemView.findViewById(R.id.tvMaThuThu);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            cardViewPhieuMuon = itemView.findViewById(R.id.cardViewPhieuMuon);
        }
    }
}
