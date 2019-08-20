package com.example.qrattandanceapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.ScanQrCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentScanFragment extends Fragment {
    public TextView emailtxt, datetxt,txt;
    public Button scanBtn;
    private FirebaseAuth mAuth;
    String emailId, date;
    SimpleDateFormat sdf;
    FirebaseUser currentUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Attendance:");
    String date_gmail;
    private OnFragmentInteractionListener mListener;

    public StudentScanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        emailId = currentUser.getEmail();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_scan, container, false);
        emailtxt = v.findViewById(R.id.user);
        datetxt = v.findViewById(R.id.txt);
        txt = v.findViewById(R.id.txt2);
        scanBtn = v.findViewById(R.id.scan);
        date = sdf.format(new Date());
        date_gmail = (date + "_" + emailId);
        emailtxt.setText("Welcome  " + emailId);
        datetxt.setText("Today's Date  " + date);
        checkAttendance();
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanQrCode.class);
                intent.putExtra("email", emailId);
                startActivity(intent);
            }
        });
        return v;
    }

    private void checkAttendance() {
        final Query query = myRef.orderByChild("date_mailid").equalTo(date + "_" + emailId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", dataSnapshot.toString());
                if (dataSnapshot.getValue() != null) {
                txt.setVisibility(View.VISIBLE);
                } else {
                    scanBtn.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.toString());
            }
        });
    }
}