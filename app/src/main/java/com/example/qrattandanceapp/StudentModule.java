package com.example.qrattandanceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.qrattandanceapp.fragment.StudentAttendanceFragment;
import com.example.qrattandanceapp.fragment.StudentProfileFragment;
import com.example.qrattandanceapp.fragment.StudentScanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentModule extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final int REQUEST_CAMERA = 111;
    Button logout;
    private long back=0;
    private FirebaseAuth mAuth;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    setFragment(new StudentScanFragment());
                    return true;
                case R.id.navigation_attandance:
                    setFragment(new StudentAttendanceFragment());
                    return true;
                case R.id.navigation_profile:
                    setFragment(new StudentProfileFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_module);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        setFragment(new StudentScanFragment());
        logout = findViewById(R.id.logout);

        if (ContextCompat.checkSelfPermission(StudentModule.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentModule.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(StudentModule.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show();
        if (back + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        back=System.currentTimeMillis();
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

    @Override
    public void onFragment(View v) {

    }

    @Override
    public void onFragment(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
