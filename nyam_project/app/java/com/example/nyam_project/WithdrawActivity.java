package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class WithdrawActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawuser);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.withdrawuserButton).setOnClickListener(onClickListener);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
    }

    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.withdrawuserButton:
                    withdraw();
                    break;
            }
        }
    };

    private void withdraw() {
        mAuth.getCurrentUser().delete();
        myStartActivity(MainActivity.class, Cuser_id, Cuser_authority);

        String phone_num = ((EditText) findViewById(R.id.PhonenumWDEditText)).getText().toString();

        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

        mDatabase.getReference().child("res_user").child(phone_num).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startToast("삭제 완료");
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                startToast("삭제 실패");
            }
        });

        mDatabase.getReference().child("std_user").child(phone_num).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startToast("삭제 완료");
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                startToast("삭제 실패");
            }
        });


    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    private void myStartActivity(Class c,int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(intent);
    }
}