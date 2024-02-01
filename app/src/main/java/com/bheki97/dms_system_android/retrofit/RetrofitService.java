package com.bheki97.dms_system_android.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;
    private static RetrofitService instance;


    private RetrofitService() {
        initializeRetrofit();

    }
    public static RetrofitService getInstance(){
        if(instance==null){
            instance = new RetrofitService();
        }

        return instance;
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.8.107:8080")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
