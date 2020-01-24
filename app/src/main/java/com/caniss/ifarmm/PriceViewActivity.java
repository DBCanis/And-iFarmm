package com.caniss.ifarmm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class PriceViewActivity extends AppCompatActivity {
    Button mSave;
    private RecyclerView mRecyclerView;
    private PriceAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private List<Price> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_view);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);
        mSave = findViewById(R.id.btn_save);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("upload");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("upload");

        mUploads = new ArrayList<>();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savefile();
            }
        });

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Price upload = postSnapshot.getValue(Price.class);
                    mUploads.add(upload);
                }

                mAdapter = new PriceAdapter(PriceViewActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PriceViewActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void savefile() {
        if (mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(PriceActivity.this, "Saving Successful", Toast.LENGTH_LONG).show();
                    //Upload upload = new Upload(mProduct.getText().toString(),mPrice.getText().toString().trim(),taskSnapshot.getStorage().getDownloadUrl().toString());

                    //String uploadId = mDatabaseRef.push().getKey();
                    //mDatabaseRef.child(uploadId).setValue(upload);
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                    while (!urlTask.isSuccessful());

                    Uri downloadUrl = urlTask.getResult();

                    Price upload = new Price(mProduct.getText().toString(),mPrice.getText().toString().trim(),downloadUrl+"");

                    String uploadId = mDatabaseRef.push().getKey();

                    mDatabaseRef.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PriceViewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        }else {
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
        }
    }
}

