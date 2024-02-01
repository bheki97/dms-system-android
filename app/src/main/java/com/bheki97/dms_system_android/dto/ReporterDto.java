package com.bheki97.dms_system_android.dto;

public class ReporterDto {

    private long reporterId;
    private String firstname;
    private String lastname;
    private String cellNo;
    private String email;

    public ReporterDto() {
    }

    public ReporterDto(long reporterId, String firstname, String lastname, String cellNo, String email) {
        this.reporterId = reporterId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cellNo = cellNo;
        this.email = email;
    }

    public long getReporterId() {
        return reporterId;
    }

    public void setReporterId(long reporterId) {
        this.reporterId = reporterId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ReporterDto{" +
                "reporterId=" + reporterId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", cellNo='" + cellNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
