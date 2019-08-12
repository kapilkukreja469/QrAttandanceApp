package com.example.qrattandanceapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProfileFragment extends Fragment {

    DatabaseReference reference;
    TextView nameTxt, emailTxt, mNoTxt, registrationNoTxt, courseIdTxt;
    private OnFragmentInteractionListener mListener;
    StudentsDataModel studentsModel = new StudentsDataModel();
    ProgressDialog progressDialog;
    final long ONE_MEGABYTE = 1024 * 1024;

    public StudentProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("Students:");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        getData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_profile, container, false);
        nameTxt = v.findViewById(R.id.name);
        emailTxt = v.findViewById(R.id.email);
        mNoTxt = v.findViewById(R.id.mobileNo);
        registrationNoTxt = v.findViewById(R.id.regestrationNo);
        courseIdTxt = v.findViewById(R.id.course_id);
        return v;
    }

    public void getData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                studentsModel = dataSnapshot.getValue(StudentsDataModel.class);
                String id = studentsModel.getEmailId();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                progressDialog.dismiss();
                if (id.equals(currentUser.getEmail())) {
                    nameTxt.setText(studentsModel.getName() + "");
                    emailTxt.setText(studentsModel.getEmailId() + "");
                    mNoTxt.setText("+91 "+studentsModel.getmNo() + "");
                    registrationNoTxt.setText(studentsModel.getRegistrationNo() + "");
                    courseIdTxt.setText(studentsModel.getCourse() + "");
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
