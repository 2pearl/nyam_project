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

public class search_promo extends AppCompatActivity {

    ListView plistView;
    ArrayList<Promo_board> plist=new ArrayList<>();
    p_ItemAdapter IAdapter;
    int Cuser_id,Cuser_authority;
    String searchName;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), promo_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_promo);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
        searchName=intent.getExtras().getString("search_name");


        this.initDB();

        plistView = (ListView)findViewById(R.id.ResultList_P);
        IAdapter = new p_ItemAdapter(this,plist);
        plistView.setAdapter(IAdapter);

        plistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent iintent= new Intent(getApplicationContext(), promo_detail.class);

                iintent.putExtra("Cuser_id",Cuser_id);
                iintent.putExtra("Cuser_authority",Cuser_authority);

                iintent.putExtra("user_id",plist.get(position).user_id);
                iintent.putExtra("post_num",plist.get(position).getPostnum());
                iintent.putExtra("post_name",plist.get(position).getPostname());
                iintent.putExtra("post_contents",plist.get(position).getContents());
                iintent.putExtra("post_kind",plist.get(position).getPostkind());
                iintent.putExtra("res_name",plist.get(position).getRes_name());
                iintent.putExtra("res_phone",plist.get(position).getRes_phone());
                iintent.putExtra("res_addr",plist.get(position).getRes_addr());

                startActivity(iintent);
            }
        });
        ImageButton SbackN = (ImageButton)findViewById(R.id.searchback_P);
        SbackN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iiiintent= new Intent(getApplicationContext(), promo_show_list.class);
                iiiintent.putExtra("Cuser_id",Cuser_id);
                iiiintent.putExtra("Cuser_authority",Cuser_authority);
                startActivity(iiiintent);
            }
        });
    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("PromoBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String post_name=ds.child("post_name").getValue(String.class);

                    if(post_name.contains(searchName)){

                        int user_id=ds.child("user_id").getValue(Integer.class);
                        int post_num = ds.child("post_num").getValue(Integer.class);
                        String post_contents = ds.child("post_contents").getValue(String.class);
                        String post_kind=ds.child("post_kind").getValue(String.class);
                        String res_name=ds.child("res_name").getValue(String.class);
                        String res_phone=ds.child("res_phone").getValue(String.class);
                        String res_addr=ds.child("res_addr").getValue(String.class);

                        Promo_board p=new Promo_board(post_num, post_name, post_contents,post_kind,user_id,res_name,res_phone,res_addr);

                        plist.add(p);
                        IAdapter.notifyDataSetChanged();
                        IAdapter.addItem(p);
                    }
                }
                if(plist.size()==0){
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