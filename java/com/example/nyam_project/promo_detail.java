package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class promo_detail extends AppCompatActivity {
    int post_num,Cuser_id,Cuser_authority,user_id;
    String postName,postContents,postKind,resName,resPhone,resAddr;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);

        Intent intent=getIntent();

        TextView post_name=(TextView)findViewById(R.id.textViewT_P);
        TextView post_contents=(TextView)findViewById(R.id.textViewEC_P);
        TextView post_kind=(TextView)findViewById(R.id.textViewEK_P);

        TextView res_name=(TextView)findViewById(R.id.textViewEN_P);
        TextView res_phone=(TextView)findViewById(R.id.textViewEPN_P);
        TextView res_addr=(TextView)findViewById(R.id.textViewEA_P);


        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);

        postName=intent.getExtras().getString("post_name");
        post_name.setText(postName);
        postContents=intent.getExtras().getString("post_contents");
        post_contents.setText(postContents);
        postKind=intent.getExtras().getString("post_kind");
        post_kind.setText(postKind);

        resName=intent.getStringExtra("res_name");
        res_name.setText(resName);
        resPhone=intent.getStringExtra("res_phone");
        res_phone.setText(resPhone);
        resAddr=intent.getStringExtra("res_addr");
        res_addr.setText(resAddr);

        Button edit=(Button)findViewById(R.id.buttonM_P);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_id==user_id){//작성자만 수정가능
                    Intent intent= new Intent(getApplicationContext(), promo_edit.class);

                    intent.putExtra("Cuser_id",Cuser_id);
                    intent.putExtra("Cuser_authority",Cuser_authority);

                    intent.putExtra("user_id",user_id);
                    intent.putExtra("post_num",post_num);
                    intent.putExtra("post_name",postName);
                    intent.putExtra("post_contents",postContents);
                    intent.putExtra("post_kind",postKind);

                    intent.putExtra("res_name",resName);
                    intent.putExtra("res_phone",resPhone);
                    intent.putExtra("res_addr",resAddr);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button delB=(Button)findViewById(R.id.buttonD_P);
        delB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // startActivity(new Intent(join_detail.this,join_show_list.class));
                if(Cuser_id==user_id){//작성자만 삭제가능
                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("/PromoBoard/"+post_num).removeValue();
                    startActivity(new Intent(promo_detail.this,promo_show_list.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageButton backP = (ImageButton)findViewById(R.id.back_detail_P);
        backP.setOnClickListener(new View.OnClickListener(){
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
