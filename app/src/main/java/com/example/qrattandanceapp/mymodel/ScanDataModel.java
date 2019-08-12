package com.example.qrattandanceapp.mymodel;

public class ScanDataModel {
    String mailid,date,time,date_mailid;

    public ScanDataModel() {
    }

    public ScanDataModel(String mailid, String date, String time) {
        this.mailid = mailid;
        this.date = date;
        this.time = time;
        date_mailid = date+"_"+mailid;
    }

    public String getDate_mailid() {
        return date_mailid;
    }

    public void setDate_mailid(String date_mailid) {
        this.date_mailid = date_mailid;
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

    @Override
    public String toString() {
        return "ScanDataModel{" +
                "mailid='" + mailid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", date_mailid='" + date_mailid + '\'' +
                '}';
    }
}
