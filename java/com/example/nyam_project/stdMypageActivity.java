package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class stdMypageActivity extends AppCompatActivity {
    private static final String TAG = "stdMypageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdmypage);

        findViewById(R.id.stdpwchangeButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdwithdrawButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stdpwchangeButton:
                    myStartActivity(changePWActivity.class);
                    break;
                case R.id.stdwithdrawButton:
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