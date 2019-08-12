package com.example.qrattandanceapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qrattandanceapp.fragment.StudentScanFragment;
import com.example.qrattandanceapp.mymodel.ScanDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SimpleDateFormat sdf, stf;
    String email, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        database = FirebaseDatabase.getInstance();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        stf = new SimpleDateFormat("HH:mm:ss");
        myRef = database.getReference("Attendance:");
        email = getIntent().getStringExtra("email");
        getSupportActionBar().hide();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(Result result) {
        date = sdf.format(new Date());
        time = stf.format(new Date());
        if (result.getText().equals(date)) {
            String id = myRef.push().getKey();
            ScanDataModel dataModel = new ScanDataModel(email, date, time);
            myRef.child(id).setValue(dataModel);
            onBackPressed();
        }
        else Toast.makeText(this, "Wrong QR Code", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
