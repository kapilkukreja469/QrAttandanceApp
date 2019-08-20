package com.example.qrattandanceapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.adapter.RecyclerViewAdapter;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllStudentProfileFragment extends Fragment {

    DatabaseReference reference, mref;
    TextView nameTxt, emailTxt, mNoTxt, registrationNoTxt, courseIdTxt;
    private OnFragmentInteractionListener mListener;
    StudentsDataModel studentsModel = new StudentsDataModel();
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<StudentsDataModel> list = new ArrayList<>();
    String key, value;
    private StorageReference mStorageRef;

    public AllStudentProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("Students:");
        mref = FirebaseDatabase.getInstance().getReference("profilePic");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_student_profile, container, false);
        nameTxt = v.findViewById(R.id.name);
        emailTxt = v.findViewById(R.id.email);
        mNoTxt = v.findViewById(R.id.mobileNo);
        registrationNoTxt = v.findViewById(R.id.registration_number);
        courseIdTxt = v.findViewById(R.id.course_id);
        recyclerView = v.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        return v;
    }

    public List<StudentsDataModel> getData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                studentsModel = dataSnapshot.getValue(StudentsDataModel.class);
                studentsModel.setImage(dataSnapshot.child("url").getValue().toString());
                progressDialog.dismiss();
//                Log.d("url","setData="+dataSnapshot.toString());
//                Log.d("url","setData="+studentsModel);
                list.add(studentsModel);
                Log.d("url","listData="+list.toString());

                recyclerViewAdapter.notifyDataSetChanged();
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
        return list;
    }
}