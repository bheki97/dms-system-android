package com.bheki97.dms_system_android;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.Manifest;

import com.bheki97.dms_system_android.databinding.ActivityReporterBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.dto.ReporterDto;
import com.bheki97.dms_system_android.enums.DisasterType;
import com.bheki97.dms_system_android.exception.UiException;
import com.bheki97.dms_system_android.retrofit.DmsServerAPI;
import com.bheki97.dms_system_android.retrofit.RetrofitService;
import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporterActivity extends AppCompatActivity {

    private ActivityReporterBinding binding;
    private DmsServerAPI dmsServerAPI;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private FusedLocationProviderClient locationProviderClient;
    private File image;


    private double longitude = 0;
    private double latitude = 0;
    private Bitmap  imageContent = null;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result ->
            {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    imageContent = (Bitmap) data.getExtras().get("data");


                    setActiveImage();
                    Toast.makeText(ReporterActivity.this,"captured image",Toast.LENGTH_SHORT).show();
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReporterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.disaster_types, android.R.layout.simple_spinner_item);
        binding.typeSelect.setAdapter(adapter);

        image = new File(getCacheDir(), "disaster-image.png");

        binding.captureLayout.setOnClickListener(this::setOnClickForCaptureLayout);
        binding.reportBtn.setOnClickListener(this::setOnClickForReportBtn);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        dmsServerAPI = RetrofitService.getInstance().getRetrofit().create(DmsServerAPI.class);
    }




    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE
        );
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void startCameraIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(cameraIntent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start the camera intent
                startCameraIntent();
            } else {
                // Camera permission denied, inform the user and close the activity
                // You can show a message or dialog to inform the user
                Toast.makeText(ReporterActivity.this,"You don't have access to the camera",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setOnClickForReportBtn(View view) {
        requestForLocation();
        try {
            validateForm();
        } catch (UiException e) {
            Toast.makeText(this,e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String typeStr = binding.typeSelect.getText().toString();
        typeStr = typeStr.replace(" ","_");
        typeStr = typeStr.toUpperCase();
        DisasterType type = DisasterType.valueOf(typeStr);


        DisasterDto dto = new DisasterDto();
        dto.setType(type);
        dto.setDisasterDesc(binding.descEdit.getText().toString());
        ReporterDto reporterDto = new ReporterDto();
        reporterDto.setReporterId(UserDetailsHolder.getInstance().getUserId());
        dto.setReporter(reporterDto);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);

        try {
            dto.setLocation(getLocationCity());
        } catch (IOException e) {
            Toast.makeText(this,"Could not get location!, try again",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        if(imageContent!=null){
            String imgStringContent =  Base64.encodeToString(bitmapToByteArray(imageContent),Base64.NO_WRAP);
            String imgFileName = "disaster.png";
            dto.setImgFileContent(imgStringContent);
            dto.setImgFileName(imgFileName);

        }


        submitDisasterDto(dto);

    }

    private String getLocationCity() throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Address addr = geocoder.getFromLocation(latitude,longitude,1).get(0);

        return addr.getSubLocality()+ " "+ addr.getLocality();
    }

    private void validateForm() throws UiException {
        String typeStr = binding.typeSelect.getText().toString();
        if(typeStr.isEmpty()){
            throw new UiException("Please to select the the Disaster Type for Clarity");
        }

        String desc = binding.descEdit.getText().toString();
        if(desc.isEmpty()){
            throw new UiException("Please try to explain the disaster for clarity");
        }

        if(latitude==0.0 || longitude==0.0){
            throw new UiException("Failed: unable to access your location, please try gain");
        }
    }

    private void confirmReportDisaster(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        builder.setTitle("Reported Confirmation");
        builder.setMessage("Thank you for reporting the disaster we will keep you update with the Progress." +
                " You track the disaster of the my history tab ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.create().show();
    }

    private void submitDisasterDto(DisasterDto dto) {
        dmsServerAPI.reportDisaster(dto).enqueue(new Callback<DisasterDto>() {

            @Override
            public void onResponse(Call<DisasterDto> call, Response<DisasterDto> response) {
                if(response.code()==200){
                    Toast.makeText(ReporterActivity.this,"Disaster successfully Reported",Toast.LENGTH_SHORT).show();

                    confirmReportDisaster();
                    imageContent = null;
                    setActiveImage();
                    binding.typeSelect.clearComposingText();
                    binding.descEdit.setText("");

                }else{
                    try {
                        Toast.makeText(ReporterActivity.this,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(ReporterActivity.this,"error occurred",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<DisasterDto> call, Throwable t) {
                Toast.makeText(ReporterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestForLocation(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    (new String[]{Manifest.permission.ACCESS_FINE_LOCATION}),LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        Task<Location> location = locationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,null);
        location.addOnSuccessListener(locate->{
            latitude = locate.getLatitude();
            longitude= locate.getLongitude();


        });
    }

    private void setOnClickForCaptureLayout(View view) {
        if (checkCameraPermission()) {
            // Permissions are granted, start the camera intent
            startCameraIntent();
        } else {
            // Permissions are not granted, request them
            requestCameraPermission();
        }

    }
    private void setActiveImage() {

        if(imageContent==null){
            binding.disasterImg.setImageResource(R.drawable.dms_high_resolution_logo);
        }else{
            binding.disasterImg.setImageBitmap(imageContent);
        }

    }
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


}