package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AssignWithdrawActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_admin);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.assignuseroutButton).setOnClickListener(onClickListener);
    }

    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.assignuseroutButton:
                    withdraw_assign();
                    break;
            }
        }
    };

    private void withdraw_assign() {
        //mAuth.getCurrentUser().delete();

        String phone_num = ((EditText) findViewById(R.id.PhoneassignuserEditText)).getText().toString();

        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

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

        myStartActivity(ManageUserActivity.class, Cuser_id, Cuser_authority);
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