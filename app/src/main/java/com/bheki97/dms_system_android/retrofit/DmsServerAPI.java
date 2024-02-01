package com.bheki97.dms_system_android.retrofit;

import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.dto.LoginDto;
import com.bheki97.dms_system_android.dto.RegisterDto;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DmsServerAPI {
    String PREFIX = "/api";


    @POST(PREFIX +"/disaster")
    Call<DisasterDto> reportDisaster(@Body DisasterDto dto);
    @GET(PREFIX+"/disaster")
    Call<DisasterDto[]> getAllDisasters();

    @GET(PREFIX+"/disaster/{reporterId}")
    Call<DisasterDto[]> getAllMyReportedDisasters(@Path("reporterId")long reporterId);

    @POST(PREFIX+"/register")
    Call<RegisterDto> registerNewAccount(@Body RegisterDto dto);

    @POST(PREFIX+"/login")
    Call<UserDetailsHolder> login(@Body LoginDto dto);



}
