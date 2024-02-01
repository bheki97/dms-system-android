package com.bheki97.dms_system_android.recycler.my_reports;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bheki97.dms_system_android.databinding.HolderMyReportBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;

import java.util.ArrayList;
import java.util.List;

public class MyReportsAdapterRecycler extends RecyclerView.Adapter<MyReportsHolder> {


    private List<DisasterDto> disasters;

    public List<DisasterDto> getDisasters() {
        return disasters;
    }

    public void setDisasters(List<DisasterDto> disasters) {
        this.disasters = disasters;
    }

    public MyReportsAdapterRecycler() {
        disasters = new ArrayList<>();
    }

    public MyReportsAdapterRecycler(List<DisasterDto> disasters) {
        this.disasters = disasters;
    }

    @NonNull
    @Override
    public MyReportsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderMyReportBinding binding = HolderMyReportBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);

        return new MyReportsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReportsHolder holder, int position) {
        holder.bind(disasters.get(position));
    }

    @Override
    public int getItemCount() {
        return disasters.size();
    }
}
