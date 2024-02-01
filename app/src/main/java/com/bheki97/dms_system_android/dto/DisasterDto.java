package com.bheki97.dms_system_android.dto;

import com.bheki97.dms_system_android.enums.DisasterType;

public class DisasterDto {

    private long disasterId;
    private String imgFileName;
    private DisasterType type;
    private String location;

    private String imgFileContent;
    private String disasterDesc;
    private double longitude;
    private double latitude;
    private ReporterDto reporter;
    private ReportDto report;

    public DisasterDto() {
    }

    public ReportDto getReport() {
        return report;
    }

    public void setReport(ReportDto report) {
        this.report = report;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DisasterType getType() {
        return type;
    }

    public void setType(DisasterType type) {
        this.type = type;
    }

    public long getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(long disasterId) {
        this.disasterId = disasterId;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getImgFileContent() {
        return imgFileContent;
    }

    public void setImgFileContent(String imgFileContent) {
        this.imgFileContent = imgFileContent;
    }

    public String getDisasterDesc() {
        return disasterDesc;
    }

    public void setDisasterDesc(String disasterDesc) {
        this.disasterDesc = disasterDesc;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ReporterDto getReporter() {
        return reporter;
    }

    public void setReporter(ReporterDto reporter) {
        this.reporter = reporter;
    }

    @Override
    public String toString() {
        return "DisasterDto{" +
                "disasterId=" + disasterId +
                ", imgFileName='" + imgFileName + '\'' +
                ", type=" + type +
                ", location='" + location + '\'' +
                ", imgFileContent='" + imgFileContent + '\'' +
                ", disasterDesc='" + disasterDesc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", reporter=" + reporter.toString() +
                '}';
    }
}
