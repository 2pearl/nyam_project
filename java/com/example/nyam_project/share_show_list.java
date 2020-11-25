package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class share_show_list extends AppCompatActivity {


    ListView slistView;
    ArrayList<Share_board> slist=new ArrayList<>();
    s_ItemAdapter IAdapter;
    int Cuser_id,Cuser_authority;

    EditText sname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_show_list);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);


        this.initDB();

        sname=(EditText)findViewById(R.id.SearchContent_S);

        slistView = (ListView)findViewById(R.id.slistView);
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

        Button Ssearch = (Button)findViewById(R.id.buttonSearch_S);
        Ssearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String searchnameS=sname.getText().toString();

                if(searchnameS==""||searchnameS==null){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent iiiintent= new Intent(getApplicationContext(), search_share.class);

                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    iiiintent.putExtra("search_name",searchnameS);
                    startActivity(iiiintent);
                }
            }
        });



    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ShareBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    int user_id=ds.child("user_id").getValue(Integer.class);
                    String people_num = ds.child("people_num").getValue(String.class);
                    String post_contents = ds.child("post_contents").getValue(String.class);
                    String post_name = ds.child("post_name").getValue(String.class);
                    int post_num = ds.child("post_num").getValue(Integer.class);
                    String share_kind = ds.child("share_kind").getValue(String.class);
                    String share_way = ds.child("share_way").getValue(String.class);
                    String share_place = ds.child("share_place").getValue(String.class);


                    Share_board s=new Share_board(post_num, post_name, post_contents,share_kind, share_way, share_place,user_id);
                    slist.add(s);
                    IAdapter.notifyDataSetChanged();
                    IAdapter.addItem(s);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button share_write=(Button)findViewById(R.id.share_write);
        share_write.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //startActivity(new Intent(share_show_list.this,activity_writing_share.class));
                if(Cuser_authority==1){
                    Intent iiintent= new Intent(getApplicationContext(), activity_writing_share.class);

                    iiintent.putExtra("user_id",Cuser_id);
                    iiintent.putExtra("user_authority",Cuser_authority);

                    startActivity(iiintent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageButton StoMain=(ImageButton)findViewById(R.id.SharelistToMain);
        StoMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switch (Cuser_authority){
                    case 0:
                        myStartActivity(AdminActivity.class,Cuser_id,Cuser_authority);
                        break;
                    case 1:
                        myStartActivity(StudentActivity.class,Cuser_id,Cuser_authority);
                        break;
                    case 2:
                        myStartActivity(RestaurantActivity.class,Cuser_id,Cuser_authority);
                        break;
                }
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
