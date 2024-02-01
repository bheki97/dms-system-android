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
            try{
                validateLoginDetails();
            }catch(UiException exc){
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            LoginDto dto = new LoginDto();

//            sendLoginRequest(dto);

            UserDetailsHolder.build(new UserDetailsHolder(1,"abc@gmail.com","Bheki","Mautjana","+27760794703",""));
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        });
    }

    private void validateLoginDetails()throws UiException {
        if(binding.emailTxtEdit.getText().toString().isEmpty()){
            throw new UiException("Email Field is empty!!");
        }
        if(binding.pwdTxtEdit.getText().toString().isEmpty()){
            throw new UiException("Password Field is empty!!");
        }

    }

    private void sendLoginRequest(LoginDto dto) {
        dmsServerAPI.login(dto).enqueue(new Callback<UserDetailsHolder>() {
            @Override
            public void onResponse(Call<UserDetailsHolder> call, Response<UserDetailsHolder> response) {
                Toast.makeText(MainActivity.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                UserDetailsHolder.build(response.body());
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<UserDetailsHolder> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Login error, Please try again",Toast.LENGTH_SHORT).show();
                binding.emailTxtEdit.setText("");
                binding.pwdTxtEdit.setText("");
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