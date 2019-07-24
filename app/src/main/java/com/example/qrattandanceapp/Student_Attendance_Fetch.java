package com.example.qrattandanceapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Student_Attendance_Fetch extends AppCompatActivity {
    private TextView mTextMessage;
    DatabaseReference reference;
    StringBuilder builder = new StringBuilder();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    Toast.makeText(getApplicationContext(), "Scan Code", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Student_Attendance_Fetch.this, StudentModule.class));
                    return true;
                case R.id.navigation_attandance:
                    Toast.makeText(getApplicationContext(), "Check Attandance", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance__fetch);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        reference = FirebaseDatabase.getInstance().getReference("Attendance:");
        getData();
    }

    public void getData() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                builder.append(dataSnapshot.getValue(String.class)+"\n");
                showData(builder);
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
                Toast.makeText(Student_Attendance_Fetch.this, "failed to read value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(StringBuilder builder) {
        mTextMessage.setText(builder);
    }
}