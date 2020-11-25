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

public class activity_writing_notice extends AppCompatActivity {

    EditText pname,pcontents;
    Button button1;
    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_notice);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        //제목, 행사종류, 내용
        pname=(EditText) findViewById(R.id.nname);//제목
        pcontents=(EditText)findViewById(R.id.ncontents);//본문


            button1 = (Button) findViewById(R.id.button7) ;
            button1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {//입력값 가져온다

                    String pnameS=pname.getText().toString();
                    String pcontentsS=pcontents.getText().toString();

                     if(pnameS.equals("")){
                        Toast.makeText(activity_writing_notice.this,"제목을 입력해 주세요.",Toast.LENGTH_LONG).show();
                    }

                    else if(pcontentsS.equals(""))   {
                        Toast.makeText(activity_writing_notice.this,"내용을 입력해 주세요.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                        GlobalVariable bk = (GlobalVariable) getApplication();

                        int post_num= bk.getboardkey();
                        bk.setboardkey(post_num+1);

                        HashMap<String,Object> newpost=new HashMap();


                        Notice_board j=new Notice_board(post_num,pnameS,pcontentsS,Cuser_id); //String people_num, String user_gender, String post_date, String post_name, String post_contents

                        Map<String,Object> userValue=j.toMap();

                        newpost.put("/NoticeBoard/"+post_num,userValue);
                        Database.updateChildren(newpost);

                        Intent iiiintent= new Intent(getApplicationContext(), notice_show_list.class);
                        iiiintent.putExtra("Cuser_id",Cuser_id);
                        iiiintent.putExtra("Cuser_authority",Cuser_authority);
                        startActivity(iiiintent);


                        //startActivity(new Intent(activity_writing_notice.this,notice_show_list.class));
                    }
                }
            });


        }

    }


