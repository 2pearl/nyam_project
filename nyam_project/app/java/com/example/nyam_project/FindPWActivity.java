package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FindPWActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.findpwfinishButton).setOnClickListener(onClickListener);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.findpwfinishButton:
                    findPW();
                    break;
            }
        }
    };

    private void findPW() {
        String email = ((EditText) findViewById(R.id.editTextemailfindpw)).getText().toString();
        String phone_num = ((EditText) findViewById(R.id.editTextfindpwphonenum)).getText().toString();


        if(email.length()>0&&phone_num.length()>0) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startToast("비밀번호 재설정 메일을 전송했습니다");
                        myStartActivity(LoginActivity.class);
                    }
                }
            });
        }
        else{
            startToast("Email 또는 전화번호를 입력해 주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
