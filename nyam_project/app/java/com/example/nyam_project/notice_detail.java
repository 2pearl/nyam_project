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

public class notice_detail extends AppCompatActivity {
    int post_num,Cuser_id,Cuser_authority,user_id;
    String postName;
    String postContents;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), notice_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent intent=getIntent();

        TextView post_name=(TextView)findViewById(R.id.textViewT_N);
        TextView post_contents=(TextView)findViewById(R.id.textViewEC_N);

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        postName=intent.getExtras().getString("post_name");
        postContents=intent.getExtras().getString("post_contents");

        post_name.setText(postName);
        post_contents.setText(postContents);

        Button edit=(Button)findViewById(R.id.buttonM_N);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_id==user_id){
                    Intent intent= new Intent(getApplicationContext(), notice_edit.class);

                    intent.putExtra("Cuser_id",Cuser_id);
                    intent.putExtra("Cuser_authority",Cuser_authority);

                    intent.putExtra("post_num",post_num);
                    intent.putExtra("post_name",postName);
                    intent.putExtra("post_contents",postContents);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button delB=(Button)findViewById(R.id.buttonD_N);
        delB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_id==user_id){
                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("/NoticeBoard/"+post_num).removeValue();
                    myStartActivity(notice_show_list.class,Cuser_id,Cuser_authority);

                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ImageButton backN = (ImageButton)findViewById(R.id.back_detail_N);
        backN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myStartActivity(notice_show_list.class,Cuser_id,Cuser_authority);
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
