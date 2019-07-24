package com.example.qrattandanceapp.mymodel;

public class StudentsModel {
    String name, emailId, password, mNo, registrationNo, course;

    public StudentsModel() {
    }

    public StudentsModel(String name, String emailId, String password, String mNo, String registrationNo, String course) {
        this.password = password;
        this.name = name;
        this.emailId = emailId;
        this.mNo = mNo;
        this.registrationNo = registrationNo;
        this.course = course;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getmNo() {
        return mNo;
    }

    public void setmNo(String mNo) {
        this.mNo = mNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "StudentsModel{" +
                "name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", mNo='" + mNo + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", course='" + course + '\'' +
                '}';
    }

    public void setCourse(String course) {
        this.course = course;
    }
}