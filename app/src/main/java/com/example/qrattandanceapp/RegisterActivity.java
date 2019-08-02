package com.example.qrattandanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText userName, email, pass, phone, regNo;
    Spinner courseName;
    String[] courseList = {"None", "Andriod", "python", "DataStructure"};
    String name, emailId, password, mNo, registrationNo, course;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Button registerBtn;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        userName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobileNo);
        progressDialog = new ProgressDialog(this);
        regNo = findViewById(R.id.regestrationNo);
        pass = findViewById(R.id.password);
        courseName = findViewById(R.id.course);
        registerBtn = findViewById(R.id.registerbtn);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Students:");

        courseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = courseList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((TextView) courseName.getSelectedView()).setError("please select course");
                return;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, courseList);
        courseName.setAdapter(adapter);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_Data();
            }
        });
    }

    private void firebaseRegister(final String emailId, final String password) {
        mAuth.createUserWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            storeDataInFirebase(myRef);
                        } else
                            Toast.makeText(RegisterActivity.this, "Can't register", Toast.LENGTH_SHORT).show();
                    }
                });
        progressDialog.dismiss();
    }

    public void updateUI(FirebaseUser user) {
        Intent intent = new Intent(RegisterActivity.this, StudentModule.class);
        startActivity(intent);
        finish();
    }

    public void validate_Data() {
        name = userName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            userName.setError("please enter your name");
            return;
        }
        emailId = email.getText().toString();
        emailId = emailId.toLowerCase();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            email.setError("Enter Valid Email ID");
            return;
        }
        password = pass.getText().toString();
        if (!isValidPassword(password)) {
            pass.setError("Please enter a strong password which will contain lower case ,upper case,numeric and special symbols.");
            return;
        }
        mNo = phone.getText().toString();
        if (!Patterns.PHONE.matcher(mNo).matches()) {
            phone.setError("Enter 10 digit valid mobile number");
            return;
        }
        registrationNo = regNo.getText().toString();
        if (TextUtils.isEmpty(registrationNo)) {
            regNo.setError("please enter your registration number");
            return;
        }
        if (course.equals("None")) {
            ((TextView) courseName.getSelectedView()).setError("please select course");
            Toast.makeText(this, "please select course", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering Please wait...");
        progressDialog.show();
        firebaseRegister(emailId, password);
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void storeDataInFirebase(DatabaseReference myRef) {
        StudentsDataModel studentsModel = new StudentsDataModel(name, emailId, password, mNo, registrationNo, course);
        String id = myRef.push().getKey();
        myRef.child(id).setValue(studentsModel);
    }
}