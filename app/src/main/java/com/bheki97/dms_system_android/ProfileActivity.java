package com.bheki97.dms_system_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.bheki97.dms_system_android.databinding.ActivityProfileBinding;
import com.bheki97.dms_system_android.dto.ReporterDto;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;

public class ProfileActivity extends AppCompatActivity {

    private UserDetailsHolder userHolder;
    private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userHolder = UserDetailsHolder.getInstance();
        setProfileFields();
    }

    private void setProfileFields() {
        String name = convertToInitCap(userHolder.getFirstname()+" "+ userHolder.getLastname());
        binding.reporterNameTxt.setText(name);


        binding.emailTxt.setText(userHolder.getEmail());
        binding.cellNoTxt.setText(userHolder.getCellNo());
    }



    private String convertToInitCap(String name){
        String names[] = name.split(" ");
        name = "";
        String val;
        for(int i=0; i<names.length;i++){
            val = names[i];
            val = val.toLowerCase();
            val  = Character.toUpperCase(val.charAt(0))+val.substring(1);
            name  += val +" ";
        }
        return name.trim();
    }
}