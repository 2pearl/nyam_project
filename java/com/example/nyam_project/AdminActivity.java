package com.example.nyam_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmainpage);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.adjoinboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.adshareboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.adpromoboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.adnoticeboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.adqaboardButton).setOnClickListener(onClickListener);
        findViewById(R.id.admypageButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.adjoinboardButton:
                    myStartActivity(join_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.adshareboardButton:
                    myStartActivity(share_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.adpromoboardButton:
                    myStartActivity(promo_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.adnoticeboardButton:
                    myStartActivity(notice_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.adqaboardButton:
                    myStartActivity(Q_show_list.class,Cuser_id,Cuser_authority);
                    break;
                case R.id.admypageButton:
                    myStartActivity(adminMypageActivity.class,Cuser_id,Cuser_authority);
                    break;
            }
        }
    };

    /*private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/
    private void myStartActivity(Class c,int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(intent);
    }
}
