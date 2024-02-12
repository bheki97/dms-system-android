package com.bheki97.dms_system_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bheki97.dms_system_android.databinding.ActivityMainBinding;
import com.bheki97.dms_system_android.dto.LoginDto;
import com.bheki97.dms_system_android.exception.UiException;
import com.bheki97.dms_system_android.retrofit.DmsServerAPI;
import com.bheki97.dms_system_android.retrofit.RetrofitService;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DmsServerAPI dmsServerAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dmsServerAPI = RetrofitService.getInstance().getRetrofit().create(DmsServerAPI.class);

        addOnClickForRegisterTxtBtn();
        addOnClickForLoginBtn();

    }

    private void addOnClickForLoginBtn() {
        binding.loginBtn.setOnClickListener( v->{
            LoginDto dto;
            try{
                 dto = validateLoginDetails();
            }catch(UiException exc){
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            sendLoginRequest(dto);


        });
    }

    private LoginDto validateLoginDetails()throws UiException {

        LoginDto dto =new LoginDto();

        String data;
        if((data = binding.emailTxtEdit.getText().toString()).isEmpty()){
            throw new UiException("Email Field is empty!!");
        }

        dto.setUsername(data);
        if((data = binding.pwdTxtEdit.getText().toString()).isEmpty()){
            throw new UiException("Password Field is empty!!");
        }
        dto.setPassword(data);

        return dto;

    }

    private void sendLoginRequest(LoginDto dto) {
        dmsServerAPI.login(dto).enqueue(new Callback<UserDetailsHolder>() {
            @Override
            public void onResponse(Call<UserDetailsHolder> call, Response<UserDetailsHolder> response) {

                if(response.code()==200){
                    Toast.makeText(MainActivity.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                    UserDetailsHolder.build(response.body());
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }else{

                    String msg;

                    try {
                        msg = response.errorBody().string();
                    } catch (IOException e) {
                        msg ="Error occurred, Please try again!";
                    }

                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UserDetailsHolder> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Could not connect to Server",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addOnClickForRegisterTxtBtn() {
        binding.registerTxtBtn.setOnClickListener( v ->{
            startActivity(new Intent(this,RegistrationActivity.class));
            finish();
        });

    }
}