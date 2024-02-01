package com.bheki97.dms_system_android.userdetails;

public class UserDetailsHolder {

    private long userId;
    private String email;
    private String firstname;
    private String lastname;
    private String cellNo;
    private String jwtToken;

    private static UserDetailsHolder instance;

    public static void build(UserDetailsHolder holder) {
        instance = holder;
    }

    public UserDetailsHolder() {
    }

    public UserDetailsHolder(long userId, String email, String firstname, String lastname, String cellNo,String jwtToken) {
        this.userId = userId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cellNo = cellNo;
        this.jwtToken = jwtToken;
    }

    public static UserDetailsHolder getInstance(){

        if(instance==null){
            instance = new UserDetailsHolder(1,"abc@gmail.com",
                    "Bheki","Mautjana","+27760794703","");
        }
        return instance;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "UserDetailsHolder{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", cellNo='" + cellNo + '\'' +
                '}';
    }
}
