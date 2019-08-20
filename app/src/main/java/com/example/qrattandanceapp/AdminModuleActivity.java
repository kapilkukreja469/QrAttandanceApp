package com.example.qrattandanceapp;

import android.content.Intent;
import android.os.Bundle;
import com.example.qrattandanceapp.fragment.AllStudentAttendanceFragment;
import com.example.qrattandanceapp.fragment.AllStudentProfileFragment;
import com.example.qrattandanceapp.fragment.StudentProfileFragment;
import com.example.qrattandanceapp.fragment.DailyReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminModuleActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    Button logout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_daily:
                    setFragment(new DailyReportFragment());
                    return true;
                case R.id.navigation_student:
                    setFragment(new AllStudentAttendanceFragment());
                    return true;
                case R.id.navigation_Students_Profile:
                    setFragment(new AllStudentProfileFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_module);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        logout = findViewById(R.id.logout);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().hide();
        setFragment(new DailyReportFragment());
        mAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Kindly logout to get Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Atteandance","onPause");
        mAuth.signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Atteandance","onStop");
        mAuth.signOut();
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.adminfragment, fragment)
                .commit();
    }
}
