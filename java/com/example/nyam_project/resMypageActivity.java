package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class resMypageActivity extends AppCompatActivity {
    private static final String TAG = "resMypageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resmypage);

        findViewById(R.id.respwchangeButton).setOnClickListener(onClickListener);
        findViewById(R.id.reswithdrawButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.respwchangeButton:
                    myStartActivity(changePWActivity.class);
                    break;
                case R.id.reswithdrawButton:
                    myStartActivity(WithdrawActivity.class);
                    break;
            }
        }
    };

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}