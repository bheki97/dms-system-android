package com.bheki97.dms_system_android.ui.gallery;

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

import com.bheki97.dms_system_android.databinding.FragmentGalleryBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.recycler.my_reports.MyReportsAdapterRecycler;
import com.bheki97.dms_system_android.retrofit.DmsServerAPI;
import com.bheki97.dms_system_android.retrofit.RetrofitService;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private DmsServerAPI dmsServerAPI;
    private MyReportsAdapterRecycler reportsAdapterRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dmsServerAPI = RetrofitService.getInstance().getRetrofit().create(DmsServerAPI.class);
        binding.myReportsRecycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        reportsAdapterRecycler = new MyReportsAdapterRecycler();



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMyReports();
    }

    private void fetchMyReports() {
        dmsServerAPI.getAllMyReportedDisasters(UserDetailsHolder.getInstance().getUserId()).enqueue(
                new Callback<DisasterDto[]>() {
                    @Override
                    public void onResponse(Call<DisasterDto[]> call, Response<DisasterDto[]> response) {
                        if(response.code()==200){
                            Toast.makeText(getContext(),"successful",Toast.LENGTH_SHORT).show();
                            List<DisasterDto> list = new ArrayList<>();
                            Collections.addAll(list, response.body());

                            if(list.isEmpty()){
                                binding.nothingHereLayout.setVisibility(View.VISIBLE);
                            }
                            reportsAdapterRecycler.setDisasters(list);
                            binding.myReportsRecycler.setAdapter(reportsAdapterRecycler);
                        }else{
                            Toast.makeText(getContext(),"Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DisasterDto[]> call, Throwable t) {
                        Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}