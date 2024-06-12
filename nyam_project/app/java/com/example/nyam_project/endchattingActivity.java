package com.example.nyam_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class endchattingActivity extends AppCompatActivity {

    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endchatting);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.assginBtn).setOnClickListener(onClickListener);
        findViewById(R.id.endchatBtn).setOnClickListener(onClickListener);


        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.assginBtn:
                    myStartActivity(assignActivity.class, Cuser_id, Cuser_authority);
                    break;
                case R.id.endchatBtn:
                    myStartActivity(StudentActivity.class, Cuser_id, Cuser_authority);
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
