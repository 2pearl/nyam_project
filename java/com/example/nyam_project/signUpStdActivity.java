package com.example.nyam_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class signUpStdActivity extends AppCompatActivity {
    private static final String TAG = "SignUpStdActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_std);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpBtn).setOnClickListener(onClickListener);
        findViewById(R.id.addimageBtn).setOnClickListener(onClickListener);
     //   findViewById(R.id.idCheckBtn).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
     //   FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.signUpBtn:
                    signUp();
                    break;
                case R.id.addimageBtn:
                    myStartActivity(uploadStdCardActivity.class);
                    break;

            }
        }
    };

    private void signUp(){
        String email = ((EditText) findViewById(R.id.stdemailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.stdpasswordEditText)).getText().toString();
        String name = ((EditText) findViewById(R.id.stdnameEditText)).getText().toString();
        String stdNum = ((EditText) findViewById(R.id.stdNumEditText)).getText().toString();
        String phoneNum = ((EditText) findViewById(R.id.stdphoneNumEditText)).getText().toString();
        RadioGroup gender = (RadioGroup) findViewById(R.id.genderRadioGroup);
        int genderID = gender.getCheckedRadioButtonId();
        RadioButton genderBtn = (RadioButton) findViewById(genderID);
        String genderIs = genderBtn.getText().toString();
        String authority = "1";
        int assign_cnt = 0;

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

        if(email.length() == 0 || password.length() == 0 || name.length() == 0 || stdNum.length() == 0 || phoneNum.length() == 0){
            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
        }
        else {


          //  FirebaseUser checkuser = mAuth.getCurrentUser();

  //          checkuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
    //            @Override
     //           public void onComplete(@NonNull Task<Void> task) {
     //               if (task.isSuccessful()) {
     //                   Toast.makeText(getApplicationContext(), "인증 메일을 전송했습니다.", Toast.LENGTH_LONG).show();
     //               } else {
     //                   Toast.makeText(getApplicationContext(), "인증 메일 전송에 오류가 있습니다.", Toast.LENGTH_LONG).show();
     //               }
     //           }
     //       });



          //  Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("std_user").child(phoneNum);

            DatabaseReference myRef2 = myRef.child("email");
            DatabaseReference myRef4 = myRef.child("name");
            DatabaseReference myRef5 = myRef.child("std num");
            DatabaseReference myRef6 = myRef.child("phone num");
            DatabaseReference myRef7 = myRef.child("gender");
            DatabaseReference myRef8 = myRef.child("authority");
            DatabaseReference myRef9 = myRef.child("assign count");

            //myRef.setValue(email);
            myRef2.setValue(email);
            myRef4.setValue(name);
            myRef5.setValue(stdNum);
            myRef6.setValue(phoneNum);
            myRef7.setValue(genderIs);
            myRef8.setValue(authority);
            myRef9.setValue(assign_cnt);

            myStartActivity(MainActivity.class);
        }
    }

 //   private void idCheck(){
 //       String email = ((EditText)findViewById(R.id.stdemailEditText)).getText().toString();

//        if(email.length()>0) {
  //          FirebaseUser checkuser = mAuth.getCurrentUser();

//            checkuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
  //              @Override
    //            public void onComplete(@NonNull Task<Void> task) {
  //                  if (task.isSuccessful()) {
  //                      Toast.makeText(getApplicationContext(), "인증 메일을 전송했습니다", Toast.LENGTH_LONG).show();
  //                  } else {
  //                      Toast.makeText(getApplicationContext(), "인증 메일 전송에 오류가 있습니다", Toast.LENGTH_LONG).show();
  //                  }
  //              }
 //           });
 //       }
 //       else{
  //          Toast.makeText(getApplicationContext(), "Email을 입력해주세요", Toast.LENGTH_LONG).show();
 //       }
 //   }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}