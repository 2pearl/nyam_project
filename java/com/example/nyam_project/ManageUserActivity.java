package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ListView listView;
    List mList = new ArrayList<>();
    ArrayAdapter adapter;
    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageuser);

        mAuth = FirebaseAuth.getInstance();
        listView=findViewById(R.id.assignmem);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        listView.setAdapter(adapter);
        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("std_user");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot:snapshot.getChildren()){
                    int assign_count = fileSnapshot.child("assign count").getValue(Integer.class);
                    String authority = fileSnapshot.child("authority").getValue(String.class);
                    String email = fileSnapshot.child("email").getValue(String.class);
                    String gender = fileSnapshot.child("gender").getValue(String.class);
                    String name = fileSnapshot.child("name").getValue(String.class);
                    String phone_num = fileSnapshot.child("phone num").getValue(String.class);
                    String std_num = fileSnapshot.child("std num").getValue(String.class);

                    if(assign_count>=1) {
                        mList.add("이름 : " + name + "\n성별 : " + gender + "\n학번 : " + std_num+"\n이메일 : " + email + "\n전화번호 : " + phone_num);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.withdrawBtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.withdrawBtn:
                    myStartActivity(AssignWithdrawActivity.class, Cuser_id, Cuser_authority);
                    break;
            }
        }
    };

    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c,int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(intent);
    }
}
