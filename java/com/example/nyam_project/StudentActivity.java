package com.example.nyam_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity {

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdmainpage);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.stdjoinboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdshareboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdpromoboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdnoticeboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdqaboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.stdmypageButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stdjoinboardButton:
                    myStartActivity(join_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.stdshareboardButton:
                    myStartActivity(share_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.stdpromoboardButton:
                    myStartActivity(promo_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.stdnoticeboardButton:
                    myStartActivity(notice_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.stdqaboardButton:
                    myStartActivity(Q_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.stdmypageButton:
                    myStartActivity(stdMypageActivity.class,Cuser_id,Cuser_authority);
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
