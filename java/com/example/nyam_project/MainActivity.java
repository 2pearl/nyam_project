package com.example.nyam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.firstloginButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdsignupButton).setOnClickListener(onClickListener);
        findViewById(R.id.ressignupButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.firstloginButton:
                    myStartActivity(LoginActivity.class);
                    break;
                case R.id.ressignupButton:
                    myStartActivity(signUpResActivity.class);
                    break;
                case R.id.stdsignupButton:
                    myStartActivity(signUpStdActivity.class);
                    break;
                //case R.id.findIDButton:
                // myStartActivity(FindIDActivity.class);
                //signUpStdActivity.class
                //break;
                //case R.id.findPWButton:
                // myStartActivity(FindPWActivity.class);
                //signUpResActivity.class
                // break;
            }
        }
    };

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}