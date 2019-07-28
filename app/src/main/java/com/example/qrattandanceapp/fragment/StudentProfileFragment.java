package com.example.qrattandanceapp.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.example.qrattandanceapp.mymodel.StudentsModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference reference;
    TextView nameTxt, emailTxt, mNoTxt, registrationNoTxt, courseIdTxt;
    private OnFragmentInteractionListener mListener;
    StudentsModel studentsModel=new StudentsModel();
    public StudentProfileFragment() {
    }

    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        reference = FirebaseDatabase.getInstance().getReference("Students:");
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_profile, container, false);
        nameTxt = v.findViewById(R.id.name);
        emailTxt = v.findViewById(R.id.email);
        mNoTxt = v.findViewById(R.id.mobileNo);
        registrationNoTxt = v.findViewById(R.id.registration_number);
        courseIdTxt = v.findViewById(R.id.course_id);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                studentsModel=dataSnapshot.getValue(StudentsModel.class);
                nameTxt.setText(studentsModel.getName()+"");
                emailTxt.setText(studentsModel.getEmailId()+"");
                mNoTxt.setText(studentsModel.getmNo()+"");
                registrationNoTxt.setText(studentsModel.getRegistrationNo()+"");
                courseIdTxt.setText(studentsModel.getCourse()+"");
                Toast.makeText(getActivity(),""+studentsModel, Toast.LENGTH_SHORT).show();
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
