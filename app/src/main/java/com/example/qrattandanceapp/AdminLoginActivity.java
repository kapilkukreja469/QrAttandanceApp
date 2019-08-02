package com.example.qrattandanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AdminLoginActivity extends AppCompatActivity {
    EditText password;
    TextView userId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verifyCode, aPass;
    Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        aPass = "apsmind@2019";
        userId = findViewById(R.id.aUserId);
        password = findViewById(R.id.aPassword);
        login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login process initialize...");
        getSupportActionBar().hide();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    password.setError("please enter password");
                } else if (pass.equals(aPass)) {
                    progressDialog.show();
//                    sendSms();
                    startActivity(new Intent(getApplicationContext(), AdminModule.class));
                } else
                    Toast.makeText(AdminLoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
            }
        });
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
                Toast.makeText(AdminLoginActivity.this, "verify code =" + verifyCode, Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

    public void sendSms() {
        String number = "+91 " + userId.getText().toString();
        if (number.length() != 14) {
            userId.setError("Enter 10 digit valid mobile number");
        } else
            PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, AdminLoginActivity.this, mCallback);
        progressDialog.dismiss();
    }
}