package com.example.ph19127_mob2041.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.dao.SachDAO;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.example.ph19127_mob2041.model.Sach;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThuFragment extends Fragment {
    private static final String TAG = DoanhThuFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialTextView tvChooseDate, tvRangeDate, tvDoanhThu;

    private PhieuMuonDAO phieuMuonDAO;
    private List<PhieuMuon> phieuMuonList;
    private SachDAO sachDAO;
    private List<Sach> sachList;


    public DoanhThuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        phieuMuonDAO = new PhieuMuonDAO(getActivity().getApplicationContext());
        phieuMuonList = new ArrayList<>();
        phieuMuonList.addAll(phieuMuonDAO.getAll());
        sachDAO = new SachDAO(getActivity().getApplicationContext());
        sachList = new ArrayList<>();
        sachList.addAll(sachDAO.getAll());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThuFragment newInstance(String param1, String param2) {
        DoanhThuFragment fragment = new DoanhThuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        MaterialDatePicker.Builder builder = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Chọn khoảng thời gian cần tính doanh thu");
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();
        this.tvChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(),
                        "MATERIAL DATE RANGE PICKER DOANH THU FRAGMENT");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                /*
                * lấy range date từ materialDatePicker để query với db
                * */
                Long lDate = selection.first;
                Long rDate = selection.second;
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(lDate);
                String firstDate = simpleDateFormat.format(calendar.getTime());
                calendar.setTimeInMillis(rDate);
                String lastDate = simpleDateFormat.format(calendar.getTime());
                // clear HH-mm-ss == 0
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = simpleDateFormat.parse(firstDate);
                    date2 = simpleDateFormat.parse(lastDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onPositiveButtonClick: " + date1);
                Log.i(TAG, "onPositiveButtonClick: " + date2);
                Log.d(TAG, "onPositiveButtonClick: " + firstDate + " " +lastDate);
                Log.d(TAG, "onPositiveButtonClick: " + phieuMuonList.toString());
                List<PhieuMuon> phieuMuonList2 = new ArrayList<>();
                for (PhieuMuon phieuMuon : phieuMuonList) {
                    if ((phieuMuon.getNgayMuon().after(date1)
                            || phieuMuon.getNgayMuon().equals(date1))
                            &&
                            (phieuMuon.getNgayMuon().before(date2)
                                    || phieuMuon.getNgayMuon().equals(date2))) {
                        // (date1 <= phieumuon.getNgayMuon() <= date2) == true
                        phieuMuonList2.add(phieuMuon);
                        Log.d(TAG, "onPositiveButtonClick: " + phieuMuon.toString());
                    }
                }
                Double sum = 0.0;
                for (Sach sach : sachList) {
                    for (PhieuMuon phieuMuon : phieuMuonList2) {
                        if (sach.getMaSach().equals(phieuMuon.getMaSach())) {
                            sum += sach.getDonGia();
                        }
                    }
                }
                //TODO tối ưu 2(n)

                Log.d(TAG, "onPositiveButtonClick: " + phieuMuonList2.toString());
                if (firstDate.equals(lastDate)) {
                    tvRangeDate.setText("các phiếu mượn trong ngày " + firstDate);
                } else {
                    tvRangeDate.setText("các phiếu mượn từ " + firstDate + " tới " + lastDate);
                }
                tvDoanhThu.setText("Doanh thu: " + sum);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void findView(View view) {
        this.tvChooseDate = view.findViewById(R.id.tv_frmDoanhThu_chooseDate);
        this.tvRangeDate = view.findViewById(R.id.tv_frmDoanhThu_rangeDate);
        this.tvDoanhThu = view.findViewById(R.id.tv_frmDoanhThu_doanhThu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }
}