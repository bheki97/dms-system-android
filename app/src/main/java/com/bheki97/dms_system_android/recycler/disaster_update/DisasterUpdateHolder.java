package com.bheki97.dms_system_android.recycler.disaster_update;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bheki97.dms_system_android.databinding.HolderDisasterUpdateBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.dto.ReporterDto;
import com.bheki97.dms_system_android.enums.DisasterType;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisasterUpdateHolder extends RecyclerView.ViewHolder  {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());


    private DisasterDto dto;
    private HolderDisasterUpdateBinding binding;


    public DisasterUpdateHolder(@NonNull HolderDisasterUpdateBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(DisasterDto dto){
        this.dto = dto;
        setReporterName(dto.getReporter());
        setReportDate(dto.getReportDto().getReportDate());
        setDisasterType(dto.getType());
        setDisasterImage(dto.getImgFileContent());

    }

    private void setDisasterImage(String imgFileContent) {
        byte[] bytArr = Base64.decode(imgFileContent,Base64.NO_WRAP);
        Bitmap bitMap = BitmapFactory.decodeByteArray(bytArr,0,bytArr.length);
        binding.disasterImg.setImageBitmap(bitMap);
    }

    private void setDisasterType(DisasterType type) {
        String strType;
        switch (type){
            case ABNORMAL_POWER_OUTAGE:
            strType = "Abnormal Power Outage";
                break;
            case POTHOLE:
            strType = "Pothole";
                break;
            case ELECTRIC_CABLE_THEFT:
            strType = "Electric Cable Theft";
                break;
            case CABBAGE_COLLECTION_DELAY:
            strType = "Cabbage Collection Delay";
                break;
            case PUBLIC_BUILDING_VANDALIZING:
            strType = "Public Building Vandalizing";
                break;
            case SEWAGE_BLOCK:
            strType = "Sewage Block";
                break;
            case WATER_PIPE_LEAK:
            strType = "Water pipe leak";
                break;
            default:
                strType = "Unknown";
        }
        binding.typeTxt.setText(strType);
    }

    private void setReportDate(Timestamp reportDate) {
        Date date = new Date(reportDate.getTime());
        binding.reportDateTxt.setText(DATE_FORMATTER.format(date));
    }

    private void setReporterName(ReporterDto reporter) {

        String name = convertToInitCap(reporter.getFirstname())+" "+ convertToInitCap(reporter.getLastname());
        binding.reporterNameTxt.setText(name);
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
