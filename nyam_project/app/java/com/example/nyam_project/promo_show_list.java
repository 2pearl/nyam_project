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

public class promo_show_list extends AppCompatActivity {


    ListView plistView;
    ArrayList<Promo_board> plist=new ArrayList<>();
    p_ItemAdapter IAdapter;
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
        setContentView(R.layout.activity_promo_show_list);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);


        this.initDB();

        sname=(EditText)findViewById(R.id.SearchContent_P);

        plistView = (ListView)findViewById(R.id.plistView);
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

        Button promo_write=(Button)findViewById(R.id.promo_write);
        promo_write.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_authority==2){
                    Intent iiintent= new Intent(getApplicationContext(), activity_writing_promo.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageButton PtoMain=(ImageButton)findViewById(R.id.PromolistToMain);
        PtoMain.setOnClickListener(new View.OnClickListener(){
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

        Button Psearch = (Button)findViewById(R.id.buttonSearch_P);
        Psearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String searchnameS=sname.getText().toString();

                if(searchnameS.equals("")){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent iiiintent= new Intent(getApplicationContext(), search_promo.class);

                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    iiiintent.putExtra("search_name",searchnameS);
                    startActivity(iiiintent);
                }
            }
        });
    }

    public void initDB(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("PromoBoard");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    int user_id=ds.child("user_id").getValue(Integer.class);
                    int post_num = ds.child("post_num").getValue(Integer.class);
                    String post_name = ds.child("post_name").getValue(String.class);
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