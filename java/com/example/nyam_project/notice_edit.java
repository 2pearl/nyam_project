package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class notice_edit extends AppCompatActivity {

    EditText pname,pcontents;
    Button button1;
    int post_num,Cuser_id,Cuser_authority,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);

        Intent intent=getIntent();
        pname=(EditText) findViewById(R.id.nNameE);//제목
        pcontents=(EditText)findViewById(R.id.ncontentsE);//본문

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        //user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        pname.setText(intent.getExtras().getString("post_name"));
        pcontents.setText(intent.getExtras().getString("post_contents"));



        button1 = (Button) findViewById(R.id.buttonEN) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {//입력값 가져온다

                String pnameS=pname.getText().toString();
                String pcontentsS=pcontents.getText().toString();

                if(pnameS.equals("")){
                    Toast.makeText(notice_edit.this,"제목을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }

                else if(pcontentsS.equals(""))   {
                    Toast.makeText(notice_edit.this,"내용을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();

                    HashMap<String,Object> newpost=new HashMap();

                    Notice_board j=new Notice_board(post_num,pnameS,pcontentsS,Cuser_id); //String people_num, String user_gender, String post_date, String post_name, String post_contents

                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/NoticeBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);


                    Intent iiintent= new Intent(getApplicationContext(), notice_show_list.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                    //startActivity(new Intent(notice_edit.this,notice_show_list.class));
                }
            }
        });


    }

}


