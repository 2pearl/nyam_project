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

public class promo_edit extends AppCompatActivity {

    Spinner Pkindspinner;
    String selectedPkind;

    int post_num,Cuser_id,Cuser_authority,user_id;
    EditText pname,pcontents,presname,presphone,presaddr;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_edit);

        Intent intent=getIntent();

        Pkindspinner = findViewById(R.id.Epkind);
        ArrayAdapter pkindAdapter = ArrayAdapter.createFromResource(this, R.array.Pkind, R.layout.spinner_item);
        pkindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Pkindspinner.setAdapter(pkindAdapter); //어댑터에 연결해줍니다.


        pname=(EditText) findViewById(R.id.EpName);//제목
        pcontents=(EditText)findViewById(R.id.Epcontents);//본문

        presname=(EditText)findViewById(R.id.EpresName);
        presphone=(EditText)findViewById(R.id.Epresphone);
        presaddr=(EditText)findViewById(R.id.Epresaddr);

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);

        pname.setText(intent.getExtras().getString("post_name"));
        pcontents.setText(intent.getExtras().getString("post_contents"));
        presname.setText(intent.getExtras().getString("res_name"));
        presphone.setText(intent.getExtras().getString("res_phone"));
        presaddr.setText(intent.getExtras().getString("res_addr"));

        button1 = (Button) findViewById(R.id.Epeditb) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {//입력값 가져온다

                String pnameS=pname.getText().toString();
                String pcontentsS=pcontents.getText().toString();
                selectedPkind = Pkindspinner.getSelectedItem().toString();
                String resnameS=presname.getText().toString();
                String resphoneS=presphone.getText().toString();
                String resaddrS=presaddr.getText().toString();

                if(selectedPkind.equals(null)){
                    Toast.makeText(promo_edit.this,"종류를 선택하세요",Toast.LENGTH_LONG).show();
                }
                else if(resaddrS.equals("")){
                    Toast.makeText(promo_edit.this,"위치를 선택해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(pnameS.equals("")){
                    Toast.makeText(promo_edit.this,"제목을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(resphoneS.equals("")){
                    Toast.makeText(promo_edit.this,"전화번호를 입력해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(promo_edit.this,"내용을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
                else if(resnameS.equals("")){
                    Toast.makeText(promo_edit.this,"이름을 입력해 주세요",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                    HashMap<String,Object> newpost=new HashMap();


                    Promo_board j=new Promo_board(post_num,pnameS,pcontentsS,selectedPkind,Cuser_id,resnameS,resphoneS,resaddrS);
                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/PromoBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);

                    Intent iiintent= new Intent(getApplicationContext(),promo_show_list.class);

                    iiintent.putExtra("Cuser_id",Cuser_id);
                    iiintent.putExtra("Cuser_authority",Cuser_authority);

                    startActivity(iiintent);
                    //startActivity(new Intent(promo_edit.this,promo_show_list.class));
                }
            }
        });

    }

}
