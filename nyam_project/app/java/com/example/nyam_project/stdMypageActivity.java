package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class stdMypageActivity extends AppCompatActivity {
    private static final String TAG = "stdMypageActivity";

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdmypage);

        findViewById(R.id.stdpwchangeButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdwithdrawButton).setOnClickListener(onClickListener);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
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
                    myStartActivity(changePWActivity.class, Cuser_id, Cuser_authority);
                    break;
                case R.id.stdwithdrawButton:
                    myStartActivity(WithdrawActivity.class, Cuser_id, Cuser_authority);
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