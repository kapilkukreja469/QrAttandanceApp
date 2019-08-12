package com.example.qrattandanceapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.mymodel.ScanDataModel;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<ScanDataModel> {
    Context context;
    Activity activity;
    ArrayList<ScanDataModel> list;
    ScanDataModel scanDataModel;

    public ListViewAdapter(Context context, ArrayList<ScanDataModel> list) {
        super(context, R.layout.list_style, list);
        this.context = context;
        activity = (Activity) context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_style, parent, false);
        TextView textView1, textView2,textView3;
        textView1 = view.findViewById(R.id.date);
        textView2 = view.findViewById(R.id.time);
        textView3 = view.findViewById(R.id.userName);
        scanDataModel = list.get(position);
        textView1.setText(getDate(scanDataModel.getDate()));
        textView2.setText(scanDataModel.getTime());
        textView3.setText(scanDataModel.getMailid());
        return view;
    }
    public String getDate(String date) {
        String day, year, month;
        year = date.substring(6, 10);
        month = date.substring(3, 5);
        day = date.substring(0, 2);
        switch (month) {
            case "01" : month = "Jan";break;
            case "02" : month = "Feb";break;
            case "03" : month = "Mar";break;
            case "04" : month = "Apr";break;
            case "05" : month = "May";break;
            case "06" : month = "Jun";break;
            case "07" : month = "Jul";break;
            case "08" : month = "Aug";break;
            case "09" : month = "Sep";break;
            case "10" : month = "Oct";break;
            case "11" : month = "Nov";break;
            case "12" : month = "Dec";break;
        }
        date = day + "-" + month + "-" + year;
        return date;
    }
}
