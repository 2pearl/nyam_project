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

public class activity_writing_share extends AppCompatActivity {

    Spinner Skindspinner;
    Spinner Swayspinner;

    String selectedSkind;
    String selectedSway;

    EditText pname,pcontents,splace;
    Button button1;
    int Cuser_id,Cuser_authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_share);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);


        Skindspinner = findViewById(R.id.spinner6);
        ArrayAdapter skindAdapter = ArrayAdapter.createFromResource(this, R.array.Skind, R.layout.spinner_item);
        skindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Skindspinner.setAdapter(skindAdapter); //어댑터에 연결해줍니다.


        Swayspinner = findViewById(R.id.spinner7);
        ArrayAdapter swayAdapter = ArrayAdapter.createFromResource(this, R.array.Sway, R.layout.spinner_item);
        swayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Swayspinner.setAdapter(swayAdapter); //어댑터에 연결해줍니다.

        pname=(EditText) findViewById(R.id.sname);//제목
        pcontents=(EditText)findViewById(R.id.scontents);//본문
        splace=(EditText)findViewById(R.id.saddr);

        button1 = (Button) findViewById(R.id.button3) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {//입력값 가져온다

                selectedSkind = Skindspinner.getSelectedItem().toString();
                selectedSway = Swayspinner.getSelectedItem().toString();

                String pnameS=pname.getText().toString();
                String pcontentsS=pcontents.getText().toString();
                String splaceS=splace.getText().toString();

                if(pnameS.equals("")){
                    Toast.makeText(activity_writing_share.this,"제목을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(splaceS.equals("")){
                    Toast.makeText(activity_writing_share.this,"장소를 입력해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(activity_writing_share.this,"내용을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                    GlobalVariable bk = (GlobalVariable) getApplication();

                    int post_num= bk.getboardkey();
                    bk.setboardkey(post_num+1);

                    HashMap<String,Object> newpost=new HashMap();


                    Share_board j=new Share_board(post_num,pnameS,pcontentsS,selectedSkind,selectedSway,splaceS,Cuser_id); //String people_num, String user_gender, String post_date, String post_name, String post_contents

                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/ShareBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);

                    Intent iiiintent= new Intent(getApplicationContext(), share_show_list.class);
                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    startActivity(iiiintent);
                    //startActivity(new Intent(activity_writing_share.this,share_show_list.class));
                }


            }
        });


    }

}


