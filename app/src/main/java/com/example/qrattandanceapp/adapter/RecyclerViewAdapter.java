package com.example.qrattandanceapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qrattandanceapp.R;
import com.example.qrattandanceapp.mymodel.StudentsDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Activity context;
    List<StudentsDataModel> list;
//    Map<Integer, Bitmap> imagesMap = new HashMap<>();
    Bitmap bmp;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    final long ONE_MEGABYTE = 1024 * 1024;


    public RecyclerViewAdapter(Activity context, List<StudentsDataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.recycler_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentsDataModel dataModel = list.get(position);
        holder.email.setText(dataModel.getEmailId());
        holder.name.setText(dataModel.getName());
        holder.mNo.setText(dataModel.getmNo());
        holder.regNo.setText(dataModel.getRegistrationNo());
        holder.course.setText(dataModel.getCourse());
//        holder.imageView.setImageDrawable(Bitmap.createBitmap(dataModel.getImage()));
        Toast.makeText(context, "url="+dataModel.getImage(), Toast.LENGTH_SHORT).show();
        Glide.with(context).load(dataModel.getImage()).into(holder.imageView);
//        if (imagesMap.containsKey(position)) {
//            holder.imageView.setImageBitmap(imagesMap.get(position));
//        }
//        else {
//            downloadImage(dataModel.getEmailId(), holder.imageView, position);
//        }

    }

    private void downloadImage(final String emailId, final ImageView img, final int pos) {
        mStorageRef.child("profile_images").child(emailId)
                .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                profImg.setImageBitmap(Bitmap.createBitmap(bmp));
//                img.setImageBitmap(bmp);
//                imagesMap.put(pos, bmp);
                //Toast.makeText(getActivity(), "" + bmp, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView email, name, mNo, regNo, course;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            email = view.findViewById(R.id.email);
            name = view.findViewById(R.id.name);
            mNo = view.findViewById(R.id.mobileNo);
            regNo = view.findViewById(R.id.registration_number);
            course = view.findViewById(R.id.course_id);
            imageView = view.findViewById(R.id.image);
        }
    }
}
