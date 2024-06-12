package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class share_edit extends AppCompatActivity {

    Spinner Skindspinner;
    Spinner Swayspinner;

    String selectedSkind;
    String selectedSway;
    int post_num,Cuser_id,Cuser_authority,user_id;
    EditText pname,pcontents,splace;
    Button button1;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), share_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_edit);

        Intent intent=getIntent();

        Skindspinner = findViewById(R.id.Eskind);
        ArrayAdapter skindAdapter = ArrayAdapter.createFromResource(this, R.array.Skind, R.layout.spinner_item);
        skindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Skindspinner.setAdapter(skindAdapter);


        Swayspinner = findViewById(R.id.Esway);
        ArrayAdapter swayAdapter = ArrayAdapter.createFromResource(this, R.array.Sway, R.layout.spinner_item);
        swayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Swayspinner.setAdapter(swayAdapter);

        pname=(EditText) findViewById(R.id.Esname);
        pcontents=(EditText)findViewById(R.id.Escontents);
        splace=(EditText)findViewById(R.id.Esplace);

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        pname.setText(intent.getExtras().getString("post_name"));
        pcontents.setText(intent.getExtras().getString("post_contents"));
        splace.setText(intent.getExtras().getString("share_place"));

        button1 = (Button) findViewById(R.id.Eseditb) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedSkind = Skindspinner.getSelectedItem().toString();
                selectedSway = Swayspinner.getSelectedItem().toString();

                String pnameS=pname.getText().toString();
                String pcontentsS=pcontents.getText().toString();
                String splaceS=splace.getText().toString();

                if(pnameS.equals("")){
                    Toast.makeText(share_edit.this,"제목을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(splaceS.equals("")){
                    Toast.makeText(share_edit.this,"장소를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(share_edit.this,"내용을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();

                    HashMap<String,Object> newpost=new HashMap();

                    Share_board j=new Share_board(post_num,pnameS,pcontentsS,selectedSkind,selectedSway,splaceS,user_id);
                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/ShareBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);

                    Intent iiintent= new Intent(getApplicationContext(),share_show_list.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                }
            }
        });
    }
}