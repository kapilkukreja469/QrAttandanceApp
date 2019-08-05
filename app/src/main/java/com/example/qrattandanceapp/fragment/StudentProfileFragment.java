package com.example.qrattandanceapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrattandanceapp.OnFragmentInteractionListener;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.StudentModule;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class StudentProfileFragment extends Fragment {

    DatabaseReference reference;
    TextView nameTxt, emailTxt, mNoTxt, registrationNoTxt, courseIdTxt;
    private OnFragmentInteractionListener mListener;
    StudentsDataModel studentsModel = new StudentsDataModel();
    ProgressDialog progressDialog;
    final long ONE_MEGABYTE = 1024 * 1024;
//    Button dp;
//    private StorageReference mStorageRef;

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
//        dp = v.findViewById(R.id.dp);
        mNoTxt = v.findViewById(R.id.mobileNo);
        registrationNoTxt = v.findViewById(R.id.regestrationNo);
//        mStorageRef = FirebaseStorage.getInstance().getReference();
        courseIdTxt = v.findViewById(R.id.course_id);
//        dp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 1);
//            }
//        });
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap image = (Bitmap) extras.get("data");
//            StudentModule.profImg.setImageBitmap(image);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data2 = baos.toByteArray();
//            uploadImage(data2);
//        }
//    }

//    private void uploadImage(byte[] data) {
//        StudentModule.proBar.setVisibility(View.VISIBLE);
//        UploadTask uploadTask = mStorageRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//                Log.d("upload_img", exception.getMessage());
//                Toast.makeText(getContext(), "Error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                StudentModule.proBar.setVisibility(View.GONE);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                // ...
//                Log.d("upload_img", "image uploaded successfully");
//                Toast.makeText(getContext(), "Images uploaded successfuly", Toast.LENGTH_SHORT).show();
//                StudentModule.proBar.setVisibility(View.GONE);
//            }
//        });
//    }

//    private void downloadImage() {
//        StudentModule.proBar.setVisibility(View.VISIBLE);
//
//        mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                // Data for "images/island.jpg" is returns, use this as needed
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                StudentModule.profImg.setImageBitmap(Bitmap.createBitmap(bmp));
//                StudentModule.proBar.setVisibility(View.GONE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                StudentModule.proBar.setVisibility(View.GONE);
//                Toast.makeText(getContext(), "Error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
