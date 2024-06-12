package com.example.nyam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.firstloginButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdsignupButton).setOnClickListener(onClickListener);
        findViewById(R.id.ressignupButton).setOnClickListener(onClickListener);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.firstloginButton:
                    myStartActivity(LoginActivity.class, Cuser_id, Cuser_authority);
                    break;
                case R.id.ressignupButton:
                    myStartActivity(signUpResActivity.class, Cuser_id, Cuser_authority);
                    break;
                case R.id.stdsignupButton:
                    myStartActivity(signUpStdActivity.class, Cuser_id, Cuser_authority);
                    break;
            }
        }
    };

    private void myStartActivity(Class c,int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(intent);
    }
}