package com.bheki97.dms_system_android.recycler.my_reports;



import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bheki97.dms_system_android.R;
import com.bheki97.dms_system_android.databinding.HolderDisasterUpdateBinding;
import com.bheki97.dms_system_android.databinding.HolderMyReportBinding;
import com.bheki97.dms_system_android.dto.DisasterDto;
import com.bheki97.dms_system_android.dto.ReportDto;
import com.bheki97.dms_system_android.dto.ReporterDto;
import com.bheki97.dms_system_android.enums.DisasterType;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyReportsHolder extends RecyclerView.ViewHolder  {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());


    private DisasterDto dto;
    private HolderMyReportBinding binding;


    public MyReportsHolder(@NonNull HolderMyReportBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(DisasterDto dto){
        this.dto = dto;
        setReportDate(dto.getReport().getReportDate());
        setDisasterType(dto.getType());
        setDisasterImage(dto.getImgFileContent());
        setStatus(dto.getReport());

    }

    private void setStatus(ReportDto report) {

        if(report.getTechnicianAttendDate()==null){
            binding.statusImg.setImageDrawable(
                    ResourcesCompat.getDrawable(binding.getRoot().getResources(), R.drawable.dangerous,null)
            );
            binding.statusTxt.setText("UNATTENDED");
            binding.statusTxt.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.unattended));
            return ;
        }
        if(report.getCompleteDate()==null){
            binding.statusImg.setImageDrawable(
                    ResourcesCompat.getDrawable(binding.getRoot().getResources(), R.drawable.attending,null)
            );
            binding.statusTxt.setText("ATTENDING");
            binding.statusTxt.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.attending));
            return ;
        }
        

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





}
