package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


public class join_detail extends AppCompatActivity {

    int post_num,Cuser_id,Cuser_authority,user_id;
    String postName;
    String postContents;
    String postDate;
    String peopleNum;
    String peopleGen;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), join_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_detail);

        Intent intent=getIntent();

        TextView post_name=(TextView)findViewById(R.id.textViewT_J);
        TextView post_contents=(TextView)findViewById(R.id.textViewEC_J);
        TextView post_date=(TextView)findViewById(R.id.textViewED_J);
        TextView people_num=(TextView)findViewById(R.id.textViewEPN_J);
        TextView user_gender=(TextView)findViewById(R.id.textViewEG_J);

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        postName=intent.getExtras().getString("post_name");
        post_name.setText(postName);
        postContents=intent.getExtras().getString("post_contents");
        post_contents.setText(postContents);
        postDate=intent.getExtras().getString("post_date");
        post_date.setText(postDate);
        peopleNum=intent.getExtras().getString("people_num");
        people_num.setText(peopleNum);
        peopleGen=intent.getExtras().getString("user_gender");
        user_gender.setText(peopleGen);

        Button edit=(Button)findViewById(R.id.buttonM_J);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(Cuser_id==user_id){
                    Intent iintent= new Intent(getApplicationContext(), join_edit.class);

                    iintent.putExtra("Cuser_id",Cuser_id);
                    iintent.putExtra("Cuser_authority",Cuser_authority);

                    iintent.putExtra("post_num",post_num);
                    iintent.putExtra("post_name",postName);
                    iintent.putExtra("post_contents",postContents);
                    iintent.putExtra("post_date",postDate);
                    iintent.putExtra("people_num",peopleNum);
                    iintent.putExtra("user_gender",peopleGen);

                    startActivity(iintent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Button delB=(Button)findViewById(R.id.buttonD_J);
        delB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_id==user_id){

                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("/JoinBoard/"+post_num).removeValue();
                    myStartActivity(join_show_list.class, Cuser_id, Cuser_authority);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button gochat=(Button)findViewById(R.id.buttonR_J);
        gochat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myStartActivity(JoinbeforechatActivity.class, Cuser_id, Cuser_authority);
            }
        });

        ImageButton backJ = (ImageButton)findViewById(R.id.back_detail_J);
        backJ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iiiintent= new Intent(getApplicationContext(), join_show_list.class);
                iiiintent.putExtra("Cuser_id",Cuser_id);
                iiiintent.putExtra("Cuser_authority",Cuser_authority);
                startActivity(iiiintent);
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
