package com.example.qrattandanceapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.qrattandanceapp.fragment.StudentAttendanceFragment;
import com.example.qrattandanceapp.fragment.StudentProfileFragment;
import com.example.qrattandanceapp.fragment.StudentScanFragment;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentModuleActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final int REQUEST_CAMERA = 111;
    Button logout;
    ProgressBar proBar;
    private long back = 0;
    CircleImageView profImg;
    final long ONE_MEGABYTE = 1024 * 1024;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    DatabaseReference myRef;
    private StorageReference mStorageRef;

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
        profImg = findViewById(R.id.profImg);
        proBar = findViewById(R.id.proBar);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Profile pic Uploading...");
        myRef = database.getReference("Students:");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef = mStorageRef.child("profile_images").child(currentUser.getEmail());
        setFragment(new StudentScanFragment());
        logout = findViewById(R.id.logout);
        downloadImage();

        if (ContextCompat.checkSelfPermission(StudentModuleActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentModuleActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(StudentModuleActivity.this, LoginActivity.class));
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            progressDialog.show();
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            profImg.setImageBitmap(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data2 = baos.toByteArray();
            proBar.setVisibility(View.VISIBLE);
            uploadImage(data2);
        }
    }

    private void uploadImage(byte[] data) {
        proBar.setVisibility(View.VISIBLE);
        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("upload_img", exception.toString());
                Toast.makeText(StudentModuleActivity.this, "Error : " + exception, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("upload_img", "image uploaded successfully");
                Toast.makeText(StudentModuleActivity.this, "Images uploaded successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("tags", uri.toString());
                StudentsDataModel dataModel=new StudentsDataModel(currentUser.getEmail(),uri.toString());
                Toast.makeText(StudentModuleActivity.this,dataModel+"", Toast.LENGTH_SHORT).show();
                myRef.child(currentUser.getUid()).child("url").setValue(uri.toString());
            }
        });
    }

    private void downloadImage() {
        proBar.setVisibility(View.VISIBLE);
        mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profImg.setImageBitmap(Bitmap.createBitmap(bmp));
                proBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                proBar.setVisibility(View.GONE);
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
        back = System.currentTimeMillis();
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
