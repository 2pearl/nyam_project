package com.example.nyam_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class uploadStdCardActivity extends AppCompatActivity {

    ImageView iv;

    Uri imgUri;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_std_card);

        iv = findViewById(R.id.ivStdCard);
        findViewById(R.id.searchStdCardBtn).setOnClickListener(onClickListener);
        findViewById(R.id.uploadRecheckBtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.searchStdCardBtn:
                    clickSelect(v);
                    break;
                case R.id.uploadRecheckBtn:
                    clickUpload(v);
                    break;
            }
        }
    };
    public void clickSelect(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    imgUri= data.getData();
                    Glide.with(this).load(imgUri).into(iv);
                }
                break;
        }
    }
    public void clickUpload(View view) {
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String filename= sdf.format(new Date())+ ".png";

        StorageReference imgRef= firebaseStorage.getReference("uploads/"+filename);

        UploadTask uploadTask =imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadStdCardActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
