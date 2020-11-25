package com.example.nyam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_join extends AppCompatActivity {

    ListView jlistView;
    ArrayList<Join_board> jlist=new ArrayList<>();
    ItemAdapter IAdapter;
    int Cuser_id,Cuser_authority;
    String searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_join);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
        searchName=intent.getExtras().getString("search_name");


        this.initDB();

        jlistView = (ListView)findViewById(R.id.ResultList_J);
        IAdapter = new ItemAdapter(this,jlist);
        jlistView.setAdapter(IAdapter);

        jlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent iintent= new Intent(getApplicationContext(), join_detail.class);

                iintent.putExtra("Cuser_id",Cuser_id);
                iintent.putExtra("Cuser_authority",Cuser_authority);

                iintent.putExtra("user_id",jlist.get(position).user_id);
                iintent.putExtra("post_num",jlist.get(position).getpost_num());
                iintent.putExtra("post_name",jlist.get(position).getpost_name());
                iintent.putExtra("post_contents",jlist.get(position).getpost_contents());

                iintent.putExtra("post_date",jlist.get(position).getPost_date());
                iintent.putExtra("people_num",jlist.get(position).getPeople_num());
                iintent.putExtra("user_gender",jlist.get(position).getUser_gender());

                startActivity(iintent);
            }
        });


    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("JoinBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String post_name=ds.child("post_name").getValue(String.class);

                    if(post_name.contains(searchName)){
                        int user_id=ds.child("user_id").getValue(Integer.class);
                        String people_num = ds.child("people_num").getValue(String.class);
                        String post_contents = ds.child("post_contents").getValue(String.class);
                        // = ds.child("post_name").getValue(String.class);
                        int post_num = ds.child("post_num").getValue(Integer.class);
                        String post_date = ds.child("post_date").getValue(String.class);
                        String user_gender = ds.child("user_gender").getValue(String.class);

                        Join_board j=new Join_board(post_num,people_num,user_gender, post_date, post_name, post_contents,user_id);
                        jlist.add(j);
                        IAdapter.notifyDataSetChanged();
                        IAdapter.addItem(j);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "검색결과가 없습니다", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void myStartActivity(Class c,int Cuser_id, int Cuser_authority) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Cuser_id",Cuser_id);
        intent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(intent);
    }

}