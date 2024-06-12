package com.example.nyam_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_share extends AppCompatActivity {

    ListView slistView;
    ArrayList<Share_board> slist=new ArrayList<>();
    s_ItemAdapter IAdapter;
    int Cuser_id,Cuser_authority;
    String searchName;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), share_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_share);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
        searchName=intent.getExtras().getString("search_name");


        this.initDB();

        slistView = (ListView)findViewById(R.id.ResultList_S);
        IAdapter = new s_ItemAdapter(this,slist);
        slistView.setAdapter(IAdapter);

        slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iintent= new Intent(getApplicationContext(), share_detail.class);

                iintent.putExtra("Cuser_id",Cuser_id);
                iintent.putExtra("Cuser_authority",Cuser_authority);

                iintent.putExtra("user_id",slist.get(position).user_id);
                iintent.putExtra("post_num",slist.get(position).getPost_num());
                iintent.putExtra("post_name",slist.get(position).getPost_name());
                iintent.putExtra("post_contents",slist.get(position).getContents());

                iintent.putExtra("share_kind",slist.get(position).getKind());
                iintent.putExtra("share_way",slist.get(position).getWay());
                iintent.putExtra("share_place",slist.get(position).getPlace());

                startActivity(iintent);
            }
        });
        ImageButton SbackN = (ImageButton)findViewById(R.id.searchback_S);
        SbackN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iiiintent= new Intent(getApplicationContext(), share_show_list.class);
                iiiintent.putExtra("Cuser_id",Cuser_id);
                iiiintent.putExtra("Cuser_authority",Cuser_authority);
                startActivity(iiiintent);
            }
        });
    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ShareBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String post_name = ds.child("post_name").getValue(String.class);
                    if(post_name.contains(searchName)) {
                        int user_id = ds.child("user_id").getValue(Integer.class);
                        String post_contents = ds.child("post_contents").getValue(String.class);
                        int post_num = ds.child("post_num").getValue(Integer.class);
                        String share_kind = ds.child("share_kind").getValue(String.class);
                        String share_way = ds.child("share_way").getValue(String.class);
                        String share_place = ds.child("share_place").getValue(String.class);


                        Share_board s = new Share_board(post_num, post_name, post_contents, share_kind, share_way, share_place, user_id);
                        slist.add(s);
                        IAdapter.notifyDataSetChanged();
                        IAdapter.addItem(s);
                    }
                }
                if(slist.size()==0){
                    Toast.makeText(getApplicationContext(), "검색결과가 없습니다", Toast.LENGTH_SHORT).show();
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