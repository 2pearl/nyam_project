package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class activity_writing_promo extends AppCompatActivity {

    Spinner Pkindspinner;
    String selectedPkind;

    EditText pname,pcontents,presname,presphone,presaddr;
    Button button1;
    int Cuser_id,Cuser_authority;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), promo_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_promo);

        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);


        Pkindspinner = findViewById(R.id.spinner5);
        ArrayAdapter pkindAdapter = ArrayAdapter.createFromResource(this, R.array.Pkind, R.layout.spinner_item);
        pkindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Pkindspinner.setAdapter(pkindAdapter);

        pname=(EditText) findViewById(R.id.pname2);
        pcontents=(EditText)findViewById(R.id.pcontents2);
        presname=(EditText)findViewById(R.id.resname);
        presphone=(EditText)findViewById(R.id.resphone);
        presaddr=(EditText)findViewById(R.id.resaddr);


        button1 = (Button) findViewById(R.id.button2) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pnameS=pname.getText().toString();
                String pcontentsS=pcontents.getText().toString();
                selectedPkind = Pkindspinner.getSelectedItem().toString();
                String resnameS=presname.getText().toString();
                String resphoneS=presphone.getText().toString();
                String resaddrS=presaddr.getText().toString();

                if(selectedPkind.equals(null)){
                    Toast.makeText(activity_writing_promo.this,"종류를 선택하세요",Toast.LENGTH_SHORT).show();
                }
                else if(resaddrS.equals("")){
                    Toast.makeText(activity_writing_promo.this,"위치를 선택해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(pnameS.equals("")){
                    Toast.makeText(activity_writing_promo.this,"제목을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(resphoneS.equals("")){
                    Toast.makeText(activity_writing_promo.this,"전화번호를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pcontentsS.equals(""))   {
                    Toast.makeText(activity_writing_promo.this,"내용을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(resnameS.equals("")){
                    Toast.makeText(activity_writing_promo.this,"이름을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }

                else{
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("postCount");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int post_num=snapshot.child("num").getValue(Integer.class);
                            HashMap<String,Integer> num=new HashMap<String, Integer>();
                            num.put("num", post_num+1);
                            rootRef.setValue(num);

                            HashMap<String,Object> newpost=new HashMap();

                            Promo_board j=new Promo_board(post_num,pnameS,pcontentsS,selectedPkind,Cuser_id,resnameS,resphoneS,resaddrS);
                            Map<String,Object> userValue=j.toMap();

                            newpost.put("/PromoBoard/"+post_num,userValue);
                            DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                            Database.updateChildren(newpost);

                            Intent iiiintent= new Intent(getApplicationContext(), promo_show_list.class);
                            iiiintent.putExtra("Cuser_id",Cuser_id);
                            iiiintent.putExtra("Cuser_authority",Cuser_authority);
                            startActivity(iiiintent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}
