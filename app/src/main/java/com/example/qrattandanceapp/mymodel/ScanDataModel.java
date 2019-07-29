package com.example.qrattandanceapp.mymodel;

public class ScanDataModel {
    String mailid,date,time;

    public ScanDataModel(String mailid, String date,String time) {
        this.mailid = mailid;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
