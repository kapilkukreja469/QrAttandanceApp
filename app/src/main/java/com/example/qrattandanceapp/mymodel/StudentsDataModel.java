package com.example.qrattandanceapp.mymodel;

public class StudentsDataModel {
    String name, emailId, password, mNo, registrationNo, course, image;

    public StudentsDataModel() {
    }

    public StudentsDataModel(String emailId, String image) {
        this.emailId = emailId;
        this.image = image;
    }

    public StudentsDataModel(String name, String emailId, String password, String mNo, String registrationNo, String course) {
        this.password = password;
        this.name = name;
        this.emailId = emailId;
        this.mNo = mNo;
        this.registrationNo = registrationNo;
        this.course = course;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String url) {
        this.image = url;
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

    public void setCourse(String course) {
        this.course = course;
    }


    @Override
    public String toString() {
        return "StudentsDataModel{" +
                "name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", mNo='" + mNo + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", course='" + course + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}