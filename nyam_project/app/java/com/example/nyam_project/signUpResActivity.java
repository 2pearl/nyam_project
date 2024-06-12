package com.example.nyam_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpResActivity extends AppCompatActivity {
    private static final String TAG = "SignUpResActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_res);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.ressignUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.resEmailCheckBtn).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ressignUpButton:
                    ressignUp();
                    break;
                case R.id.resEmailCheckBtn:
                    emailCheck();
                    break;

            }
        }
    };

    private void ressignUp(){
        String email = ((EditText)findViewById(R.id.resemailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.respasswordEditText)).getText().toString();
        String res_name = ((EditText)findViewById(R.id.resbusinessEditText)).getText().toString();
        String name = ((EditText)findViewById(R.id.resnameEditText)).getText().toString();
        String phone_num = ((EditText)findViewById(R.id.resphonenumEditText)).getText().toString();
        String address = ((EditText)findViewById(R.id.resaddressEditText)).getText().toString();
        String business_num = ((EditText)findViewById(R.id.resbusinessnumEditText)).getText().toString();
        String authority = "2";

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //성공했을 때 UI logic
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //실패했을 때 UI logic
                        }
                        // ...
                    }
                });

        if(email.length() == 0 || res_name.length() == 0 || name.length() == 0 || phone_num.length() == 0 || address.length() == 0 || business_num.length() == 0){
            Toast.makeText(getApplicationContext(), "빈 칸 없이 작성해 주세요.", Toast.LENGTH_SHORT).show();
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(),"비밀번호는 6자리 이상 입력해 주세요.",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("res_user").child(phone_num);

            DatabaseReference myRef2 = myRef.child("email");
            DatabaseReference myRef4 = myRef.child("res name");
            DatabaseReference myRef5 = myRef.child("name");
            DatabaseReference myRef6 = myRef.child("phone num");
            DatabaseReference myRef7 = myRef.child("address");
            DatabaseReference myRef8 = myRef.child("business num");
            DatabaseReference myRef9 = myRef.child("authority");

            myRef2.setValue(email);
            myRef4.setValue(res_name);
            myRef5.setValue(name);
            myRef6.setValue(phone_num);
            myRef7.setValue(address);
            myRef8.setValue(business_num);
            myRef9.setValue(authority);

            myStartActivity(MainActivity.class);
        }
    }
    private void emailCheck() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(signUpResActivity.this, "이메일이 발송되었습니다. 메일함을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        } else
                            Log.d(TAG, "not");
                    }
                });
    }
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}