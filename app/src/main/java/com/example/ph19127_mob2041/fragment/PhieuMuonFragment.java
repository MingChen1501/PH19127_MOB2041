package com.example.ph19127_mob2041.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ph19127_mob2041.R;
import com.example.ph19127_mob2041.adapter.PhieuMuonAdapter;
import com.example.ph19127_mob2041.dao.PhieuMuonDAO;
import com.example.ph19127_mob2041.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PhieuMuonFragment extends Fragment {

    private List<PhieuMuon> phieuMuonList;
    private RecyclerView rcvPhieuMuon;
    private RecyclerView.LayoutManager layoutManager;
    private PhieuMuonDAO phieuMuonDAO;
    private PhieuMuonAdapter phieuMuonAdapter;
    FloatingActionButton fab;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        fab = view.findViewById(R.id.fabAddPhieuMuon);

        layoutManager = new LinearLayoutManager(view.getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);

        phieuMuonDAO = new PhieuMuonDAO(view.getContext());
        phieuMuonList = phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(view.getContext(), phieuMuonList, phieuMuonDAO);
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);
//        Log.d("list phieu muon", phieuMuonList.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }
}