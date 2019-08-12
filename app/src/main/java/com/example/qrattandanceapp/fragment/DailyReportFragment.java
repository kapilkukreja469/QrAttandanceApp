package com.example.qrattandanceapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.adapter.ListViewAdapter;
import com.example.qrattandanceapp.mymodel.ScanDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyReportFragment extends Fragment {
    DatabaseReference reference;
    ListView listView;
    ScanDataModel scanDataModel;
    ArrayList<ScanDataModel> mylist = new ArrayList<>();
    ProgressDialog progressDialog;
    EditText date;
    Button btn;
    Calendar c;
    private OnFragmentInteractionListener mListener;

    public DailyReportFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("Attendance:");
        scanDataModel = new ScanDataModel();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily_report, container, false);
        listView = v.findViewById(R.id.listView);
        date = v.findViewById(R.id.date);
        btn = v.findViewById(R.id.getData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();
                listView.setAdapter(null);
                progressDialog.show();
                getData();
            }
        });
        return v;
    }

    public void getData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                scanDataModel = dataSnapshot.getValue(ScanDataModel.class);
                String id = scanDataModel.getDate();
                progressDialog.dismiss();
                if (id.equals(date.getText().toString())) {
                    mylist.add(scanDataModel);
                    ListViewAdapter listViewAdapter=new ListViewAdapter(getContext(),mylist);
                    listView.setAdapter(listViewAdapter);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "failed to read value", Toast.LENGTH_SHORT).show();
            }
        });
    }

}