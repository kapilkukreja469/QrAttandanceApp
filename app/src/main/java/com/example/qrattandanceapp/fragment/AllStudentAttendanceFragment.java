package com.example.qrattandanceapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.adapter.ListViewAdapter;
import com.example.qrattandanceapp.mymodel.ScanDataModel;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllStudentAttendanceFragment extends Fragment {
    DatabaseReference reference1, reference2;
    ListView listView;
    ScanDataModel scanDataModel = new ScanDataModel();
    StudentsDataModel studentsDataModel = new StudentsDataModel();
    ArrayList<ScanDataModel> mylist = new ArrayList<>();
    List<String> studentList = new ArrayList<>();
    String student;
    ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;
    Spinner spinner;

    public AllStudentAttendanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference1 = FirebaseDatabase.getInstance().getReference("Students:");
        reference2 = FirebaseDatabase.getInstance().getReference("Attendance:");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        studentList.add("- - - Select Student Name - - -");
        loadSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_student_attendance, container, false);
        listView = v.findViewById(R.id.listView);
        spinner = v.findViewById(R.id.spinner);
        return v;

    }

    public void loadSpinner() {
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                studentsDataModel = dataSnapshot.getValue(StudentsDataModel.class);
                studentList.add(studentsDataModel.getEmailId());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, studentList);
                spinner.setAdapter(adapter);
                progressDialog.dismiss();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            mylist.clear();
                            listView.setAdapter(null);
                            student = studentList.get(position);
                            getData();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
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

    public void getData() {
        reference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                scanDataModel = dataSnapshot.getValue(ScanDataModel.class);
                String uId = scanDataModel.getMailid();
                progressDialog.dismiss();
                if (uId.equals(student)) {
                    mylist.add(scanDataModel);
                    ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), mylist);
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