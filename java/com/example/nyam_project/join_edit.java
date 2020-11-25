package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class join_edit extends AppCompatActivity {

    Spinner sexspinner;
    Spinner pspinner;

    String selectedSex;
    String selectedPcount;
    int post_num,Cuser_id,Cuser_authority;
    EditText pname,pdate,pcontents;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_edit);

        sexspinner = findViewById(R.id.gspinner);//성별
        ArrayAdapter sexAdapter = ArrayAdapter.createFromResource(this, R.array.Sex, R.layout.spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexspinner.setAdapter(sexAdapter); //어댑터에 연결해줍니다.

        pspinner = findViewById(R.id.Ejpeople);//같이 먹을 인원
        ArrayAdapter pAdapter = ArrayAdapter.createFromResource(this, R.array.Pcount, R.layout.spinner_item);
        pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pspinner.setAdapter(pAdapter); //어댑터에 연결해줍니다.


        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);


        post_num=intent.getIntExtra("post_num",0);
        pname=(EditText)findViewById(R.id.EJName);//제목
        pdate=(EditText)findViewById(R.id.Ejdate);//날짜
        pcontents=(EditText)findViewById(R.id.Ejcontents);//본문

        pname.setText(intent.getExtras().getString("post_name"));
        pdate.setText(intent.getExtras().getString("post_date"));
        pcontents.setText(intent.getExtras().getString("post_contents"));

        button1 = (Button) findViewById(R.id.Ejbutton) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {//입력값 가져온다

                selectedSex = sexspinner.getSelectedItem().toString();
                selectedPcount = pspinner.getSelectedItem().toString();
                String pnameS=pname.getText().toString();
                String pdateS=pdate.getText().toString();
                String pcontentsS=pcontents.getText().toString();



                if(selectedSex.equals(null)){
                    Toast.makeText(join_edit.this,"성별을 선택하세요",Toast.LENGTH_LONG).show();
                }
                else if(selectedPcount.equals(null)){
                    Toast.makeText(join_edit.this,"인원수를 선택해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(pnameS.equals("")){
                    Toast.makeText(join_edit.this,"제목을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(pdateS.equals("")){
                    Toast.makeText(join_edit.this,"날짜를 입력해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(join_edit.this,"내용을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();


                    HashMap<String,Object> newpost=new HashMap();

                    Join_board j=new Join_board(post_num,selectedPcount,selectedSex,pdateS,pnameS,pcontentsS,Cuser_id); //String people_num, String user_gender, String post_date, String post_name, String post_contents

                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/JoinBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);


                    Intent iiintent= new Intent(getApplicationContext(), join_show_list.class);
                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);
                    startActivity(iiintent);

                }

            }
        });

    }
}
