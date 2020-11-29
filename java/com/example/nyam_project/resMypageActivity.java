package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class resMypageActivity extends AppCompatActivity {
    private static final String TAG = "resMypageActivity";

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resmypage);

        findViewById(R.id.respwchangeButton).setOnClickListener(onClickListener);
        findViewById(R.id.reswithdrawButton).setOnClickListener(onClickListener);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);
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
                    myStartActivity(changePWActivity.class, Cuser_id, Cuser_authority);
                    break;
                case R.id.reswithdrawButton:
                    myStartActivity(WithdrawActivity.class, Cuser_id, Cuser_authority);
                    break;
            }
        }
    };

    private void myStartActivity(Class c,int user_id,int user_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",user_id);
        intent.putExtra("Cuser_authority",user_authority);
        startActivity(intent);
    }
}