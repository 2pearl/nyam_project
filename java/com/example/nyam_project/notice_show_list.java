package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notice_show_list extends AppCompatActivity {

    ListView nlistView;
    ArrayList<Notice_board> nlist=new ArrayList<>();
    n_ItemAdapter IAdapter;
    int Cuser_id,Cuser_authority;

    EditText sname;

    public void onBackPressed(){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_show_list);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        this.initDB();

        sname=(EditText)findViewById(R.id.SearchContent_N);

        nlistView = (ListView)findViewById(R.id.nlistView);
        IAdapter = new n_ItemAdapter(this,nlist);
        nlistView.setAdapter(IAdapter);

        nlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iintent= new Intent(getApplicationContext(), notice_detail.class);

                iintent.putExtra("Cuser_id",Cuser_id);
                iintent.putExtra("Cuser_authority",Cuser_authority);

                iintent.putExtra("user_id",nlist.get(position).user_id);
                iintent.putExtra("post_num",nlist.get(position).post_num);
                iintent.putExtra("post_name",nlist.get(position).post_name);
                iintent.putExtra("post_contents",nlist.get(position).post_contents);


                startActivity(iintent);
            }
        });

        Button notice_write=(Button)findViewById(R.id.notice_write);
        notice_write.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_authority==0){
                    Intent iiintent= new Intent(getApplicationContext(),activity_writing_notice.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageButton NtoMain=(ImageButton)findViewById(R.id.NoticelistToMain);
        NtoMain.setOnClickListener(new View.OnClickListener(){
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
        Button Nsearch = (Button)findViewById(R.id.buttonSearch_N);
        Nsearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String searchnameS=sname.getText().toString();

                if(searchnameS.equals("")){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent iiiintent= new Intent(getApplicationContext(), search_notice.class);

                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    iiiintent.putExtra("search_name",searchnameS);
                    startActivity(iiiintent);
                }
            }
        });
    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("NoticeBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    int user_id=ds.child("user_id").getValue(Integer.class);
                    String post_contents = ds.child("post_contents").getValue(String.class);
                    String post_name = ds.child("post_name").getValue(String.class);
                    int post_num = ds.child("post_num").getValue(Integer.class);

                    Notice_board n=new Notice_board(post_num, post_name, post_contents,user_id);
                    nlist.add(n);
                    IAdapter.notifyDataSetChanged();
                    IAdapter.addItem(n);
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
