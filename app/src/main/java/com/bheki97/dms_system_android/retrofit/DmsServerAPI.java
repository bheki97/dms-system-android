package com.bheki97.dms_system_android.retrofit;

import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.dto.LoginDto;
import com.bheki97.dms_system_android.dto.RegisterDto;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DmsServerAPI {
    String PREFIX = "/api";


    @POST(PREFIX +"/disaster")
    Call<DisasterDto> reportDisaster(@Body DisasterDto dto);
    Call<DisasterDto[]> getAllDisasters();

    @POST(PREFIX+"/register")
    Call<RegisterDto> registerNewAccount(@Body RegisterDto dto);

    @POST(PREFIX+"/login")
    Call<UserDetailsHolder> login(@Body LoginDto dto);


}
