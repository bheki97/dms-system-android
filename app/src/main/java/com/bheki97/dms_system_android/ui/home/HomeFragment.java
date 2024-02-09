package com.bheki97.dms_system_android.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bheki97.dms_system_android.databinding.FragmentHomeBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.recycler.disaster_update.DisasterUpdatesAdapterRecycler;
import com.bheki97.dms_system_android.retrofit.DmsServerAPI;
import com.bheki97.dms_system_android.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DmsServerAPI dmsServerAPI;
    private DisasterUpdatesAdapterRecycler updatesAdapterRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dmsServerAPI = RetrofitService.getInstance().getRetrofit().create(DmsServerAPI.class);
        binding.reportsRecycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        updatesAdapterRecycler = new DisasterUpdatesAdapterRecycler();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDisasterUpdates();
    }

    private void fetchDisasterUpdates() {
        dmsServerAPI.getAllDisasters().enqueue(new Callback<DisasterDto[]>() {
            @Override
            public void onResponse(Call<DisasterDto[]> call, Response<DisasterDto[]> response) {

                if(response.code()==200){
                    if(response.body()!=null){
                        List<DisasterDto> list = new ArrayList<>();
                        Collections.addAll(list, response.body());

                        if(list.isEmpty()){
                            binding.nothingHereLayout.setVisibility(View.VISIBLE);
                        }
                        updatesAdapterRecycler.setDisasters(list);
                        binding.reportsRecycler.setAdapter(updatesAdapterRecycler);
                    }
                }
            }

            @Override
            public void onFailure(Call<DisasterDto[]> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(binding.getRoot().getContext(), "Failed to Fetch Updates",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}