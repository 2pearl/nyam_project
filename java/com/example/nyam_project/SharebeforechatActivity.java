package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SharebeforechatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforchatting);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.enterchatBtn).setOnClickListener(onClickListener);

        Intent intent=getIntent();

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

    }

    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.enterchatBtn:
                    enterchat();
                    break;
            }
        }
    };

    private void enterchat() {

        String nickname = ((EditText) findViewById(R.id.enterchatnicknameEditText)).getText().toString();
        String phone_num = ((EditText) findViewById(R.id.enterchatphonenumEditText)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message_user").child(nickname).child(phone_num);

        myStartActivity(ShareChatActivity.class, nickname, phone_num, Cuser_id, Cuser_authority);
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c, String nickname, String phone_num, int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        intent.putExtra("nickname", nickname);
        intent.putExtra("phone num", phone_num);
        startActivity(intent);
    }

}
