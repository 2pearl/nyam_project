package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class adminMypageActivity extends AppCompatActivity {
    private static final String TAG = "adminMypageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmypage);

        findViewById(R.id.managememBtn).setOnClickListener(onClickListener);
        findViewById(R.id.adchangepwBtn).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.managememBtn:
                myStartActivity(manageMemActivity.class);
                break;
                case R.id.adchangepwBtn:
                    myStartActivity(changePWActivity.class);
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