package com.example.qrattandanceapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.ScanCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentScanFragment extends Fragment {
    public static TextView scanResult, email,datetxt;
    Button scanBtn;
    private FirebaseAuth mAuth;
    String emailId;
    SimpleDateFormat sdf;
    private OnFragmentInteractionListener mListener;

    public StudentScanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailId = currentUser.getEmail();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_scan, container, false);
        scanResult = v.findViewById(R.id.scanResult);
        email = v.findViewById(R.id.user);
        datetxt=v.findViewById(R.id.txt);
        scanBtn = v.findViewById(R.id.scan);
        email.setText("Welcome  "+emailId);
        datetxt.setText("Today's Date  "+sdf.format(new Date()));
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanCode.class);
                intent.putExtra("email", emailId);
                startActivity(intent);
            }
        });
        return v;
    }
}