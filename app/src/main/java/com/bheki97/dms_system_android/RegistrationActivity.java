package com.bheki97.dms_system_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.bheki97.dms_system_android.databinding.ActivityRegistrationBinding;
import com.bheki97.dms_system_android.dto.RegisterDto;
import com.bheki97.dms_system_android.exception.UiException;
import com.bheki97.dms_system_android.retrofit.DmsServerAPI;
import com.bheki97.dms_system_android.retrofit.RetrofitService;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    private DmsServerAPI dmsServerAPI;
    private MaterialAlertDialogBuilder dialogBuilder;
    private AlertDialog dialog;

    boolean isPasswordValid;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private  static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isPasswordValid =false;
        dmsServerAPI = RetrofitService.getInstance().getRetrofit().create(DmsServerAPI.class);

        addOnClickForLoginTxtBtn();
        addOnClickForRegisterBtn();
        addOnTextChangeForPasswordEdit();
    }

    private void addOnTextChangeForPasswordEdit() {
        binding.pwdTxtEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String data = s.toString();
                isPasswordValid = true;

                boolean evaluater = data.matches(".*[0-9].*");
                if(evaluater){
                    binding.valNumeric.setTextColor(Color.GREEN);
                }else{
                    binding.valNumeric.setTextColor(Color.RED);
                }
                isPasswordValid = isPasswordValid && evaluater;

                //check if at least contains one Lowercase
                evaluater = data.matches(".*[a-z].*");
                if(evaluater){
                    binding.valLowercase.setTextColor(Color.GREEN);
                }else{
                    binding.valLowercase.setTextColor(Color.RED);
                }
                isPasswordValid = isPasswordValid && evaluater;


                //check if at least contains one Uppercase
                evaluater = data.matches(".*[A-Z].*");
                if(evaluater){
                    binding.valUppercase.setTextColor(Color.GREEN);
                }else{
                    binding.valUppercase.setTextColor(Color.RED);
                }
                isPasswordValid = isPasswordValid && evaluater;


                //check if at least contains one Special Character
                evaluater = data.matches(".*[^a-zA-Z0-9].*");
                if(evaluater){
                    binding.valSpecChar.setTextColor(Color.GREEN);
                }else{
                    binding.valSpecChar.setTextColor(Color.RED);
                }
                isPasswordValid = isPasswordValid && evaluater;


                //check if at least contains one Special Character
                evaluater = data.length()>8;
                if(evaluater){
                    binding.valCharLen.setTextColor(Color.GREEN);
                }else{
                    binding.valCharLen.setTextColor(Color.RED);
                }
                isPasswordValid = isPasswordValid && evaluater;

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addOnClickForRegisterBtn() {
        binding.registerBtn.setOnClickListener( v ->{
            try {
                validateFields();

            } catch (UiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            RegisterDto dto =  new RegisterDto();
            dto.setFirstname(binding.fNameEditTxt.getText().toString());
            dto.setLastname(binding.lNameEditTxt.getText().toString());
            dto.setEmail(binding.emailTxtEdit.getText().toString());
            dto.setCellNo(binding.celNoTxtEdit.getText().toString());
            dto.setPassword(binding.pwdTxtEdit.getText().toString());
            sendRegisterRequest(dto);

        });

    }

    private void sendRegisterRequest(RegisterDto dto) {
        dmsServerAPI.registerNewAccount(dto).enqueue(new Callback<RegisterDto>() {
            @Override
            public void onResponse(Call<RegisterDto> call, Response<RegisterDto> response) {
                dialogBuilder = new MaterialAlertDialogBuilder(RegistrationActivity.this)
                        .setMessage("Thank you for registration on the DMS Platform. You can now " +
                                "login to start reporting disasters.")
                        .setTitle("Registration Success")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                finish();
                            }
                        });
                dialog = dialogBuilder.create();
                dialog.show();

            }

            @Override
            public void onFailure(Call<RegisterDto> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this,"Failed Register, Please try again",
                        Toast.LENGTH_SHORT).show();
                binding.pwdTxtEdit.setText("");
                binding.celNoTxtEdit.setText("");
                binding.emailTxtEdit.setText("");
                binding.fNameEditTxt.setText("");
                binding.lNameEditTxt.setText("");
            }
        });
    }

    private void validateFields() throws UiException {
        String field;
        field = binding.fNameEditTxt.getText().toString();
        if(field.length()<2){
            throw new UiException("Firstname cannot contain less then 2 characters");
        }

        field = binding.lNameEditTxt.getText().toString();
        if(field.length()<2){
            throw new UiException("Lastname cannot contain less then 2 characters");
        }

        field = binding.emailTxtEdit.getText().toString();
        if(!EMAIL_PATTERN.matcher(field).matches()){
            throw new UiException("Email should be in a correct format: example@dms.com");
        }

        field = binding.celNoTxtEdit.getText().toString();
        if(field.length()<10){
            throw new UiException("CellNo cannot contain less then 10 characters");
        }

        if(!isPasswordValid){
            throw new UiException("Please enter a valid password. Follow the guide ");
        }




    }

    private void addOnClickForLoginTxtBtn() {
        binding.loginTxtBtn.setOnClickListener( v->{
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });
    }
}