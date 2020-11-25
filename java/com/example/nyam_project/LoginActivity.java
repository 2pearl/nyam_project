package com.example.nyam_project;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    int user_id,user_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        //findViewById(R.id.findIDButton).setOnClickListener(onClickListener);
        findViewById(R.id.findPWButton).setOnClickListener(onClickListener);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton:
                    login();
                    break;
                //case R.id.findIDButton:
                //    myStartActivity(FindIDActivity.class);
                //    break;
                case R.id.findPWButton:
                    //myStartActivity(FindPWActivity.class);
                    break;
            }
        }
    };

    private void login(){
        String email = ((EditText) findViewById(R.id.EmailEditText)).getText().toString();
        String PW = ((EditText) findViewById(R.id.PasswordEditText)).getText().toString();
        String phone_num=((EditText) findViewById(R.id.PhoneEditText)).getText().toString();



        if(email.length()>0&&PW.length()>0){
            mAuth.signInWithEmailAndPassword(email, PW)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인에 성공했습니다");

                                DatabaseReference databaseReference;

                                databaseReference = FirebaseDatabase.getInstance().getReference("res_user");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        Iterator<DataSnapshot> child=dataSnapshot.getChildren().iterator();

                                        while(child.hasNext()){
                                            if(child.next().getKey().equals(phone_num)){
                                                myStartActivity(RestaurantActivity.class,Integer.parseInt(phone_num),2);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError){

                                    }
                                });

                                databaseReference = FirebaseDatabase.getInstance().getReference("std_user");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        Iterator<DataSnapshot> child=dataSnapshot.getChildren().iterator();

                                        while(child.hasNext()){
                                            if(child.next().getKey().equals(phone_num)){
                                                myStartActivity(StudentActivity.class,Integer.parseInt(phone_num),1);
                                                Log.d("확인",phone_num);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError){

                                    }
                                });

                                databaseReference = FirebaseDatabase.getInstance().getReference("admin_user");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot){
                                        Iterator<DataSnapshot> child=dataSnapshot.getChildren().iterator();

                                        while(child.hasNext()){
                                            if(child.next().getKey().equals(phone_num)){
                                                myStartActivity(AdminActivity.class,Integer.parseInt(phone_num),0);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError){

                                    }
                                });
                            }
                            else{
                                if(task.getException()!=null){
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        }
        else{
            startToast("ID 또는 PW를 입력해 주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c,int user_id,int user_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",user_id);
        intent.putExtra("Cuser_authority",user_authority);
        startActivity(intent);
    }
}