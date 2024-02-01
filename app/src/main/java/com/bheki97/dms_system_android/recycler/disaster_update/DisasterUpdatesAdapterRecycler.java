package com.bheki97.dms_system_android.recycler.disaster_update;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bheki97.dms_system_android.databinding.HolderDisasterUpdateBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;

import java.util.ArrayList;
import java.util.List;

public class DisasterUpdatesAdapterRecycler extends RecyclerView.Adapter<DisasterUpdateHolder> {


    private List<DisasterDto> disasters;

    public List<DisasterDto> getDisasters() {
        return disasters;
    }

    public void setDisasters(List<DisasterDto> disasters) {
        this.disasters = disasters;
    }

    public DisasterUpdatesAdapterRecycler() {
        disasters = new ArrayList<>();
    }

    public DisasterUpdatesAdapterRecycler(List<DisasterDto> disasters) {
        this.disasters = disasters;
    }

    @NonNull
    @Override
    public DisasterUpdateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderDisasterUpdateBinding binding = HolderDisasterUpdateBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);

        return new DisasterUpdateHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DisasterUpdateHolder holder, int position) {
        holder.bind(disasters.get(position));
    }

    @Override
    public int getItemCount() {
        return disasters.size();
    }
}
