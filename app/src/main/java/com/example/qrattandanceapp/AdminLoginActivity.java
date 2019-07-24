package com.example.qrattandanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AdminLoginActivity extends AppCompatActivity {
    EditText email, password;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verifyCode;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        getSupportActionBar().hide();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(AdminLoginActivity.this, "verification failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verifyCode = s;
                Toast.makeText(AdminLoginActivity.this, "OTP send successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminLoginActivity.this, OtpActivity.class);
                intent.putExtra("OTP", verifyCode);
                startActivity(intent);
                Toast.makeText(AdminLoginActivity.this, "verify code ="+verifyCode, Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

    public void sendSms(View view) {
        String number = "+91 "+email.getText().toString();
        if (number.length()!=14){
            email.setError("Enter 10 digit valid mobile number");
        }
        else
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, AdminLoginActivity.this, mCallback);
    }
}