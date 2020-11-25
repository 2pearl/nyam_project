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

public class share_detail extends AppCompatActivity {
    int post_num,Cuser_id,Cuser_authority,user_id;
    String postName,postContents,shareKind,shareWay,sharePlace;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        Intent intent=getIntent();

        TextView post_name=(TextView)findViewById(R.id.textViewT_S);
        TextView post_contents=(TextView)findViewById(R.id.textViewEC_S);
        TextView share_kind=(TextView)findViewById(R.id.textViewEK_S);
        TextView share_way=(TextView)findViewById(R.id.textViewEW_S);
        TextView share_place=(TextView)findViewById(R.id.textViewEP_S);


        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        postName=intent.getExtras().getString("post_name");
        post_name.setText(postName);
        postContents=intent.getExtras().getString("post_contents");
        post_contents.setText(postContents);
        shareKind=intent.getExtras().getString("share_kind");
        share_kind.setText(shareKind);
        shareWay=intent.getExtras().getString("share_way");
        share_way.setText(shareWay);
        sharePlace=intent.getExtras().getString("share_place");
        share_place.setText(sharePlace);

        Button edit=(Button)findViewById(R.id.buttonM_S);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Cuser_id==user_id){//작성자만 수정가능
                    Intent iintent= new Intent(getApplicationContext(), share_edit.class);

                    iintent.putExtra("Cuser_id",Cuser_id);
                    iintent.putExtra("Cuser_authority",Cuser_authority);

                    iintent.putExtra("post_num",post_num);
                    iintent.putExtra("post_name",postName);
                    iintent.putExtra("post_contents",postContents);
                    iintent.putExtra("share_kind",shareKind);
                    iintent.putExtra("share_way",shareWay);
                    iintent.putExtra("share_place",sharePlace);

                    startActivity(iintent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button delB=(Button)findViewById(R.id.buttonD_S);
        delB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // startActivity(new Intent(join_detail.this,join_show_list.class));
                if(Cuser_id==user_id){//작성자만 삭제가능
                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("/ShareBoard/"+post_num).removeValue();
                    startActivity(new Intent(share_detail.this,share_show_list.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_LONG).show();
                }


            }
        });

        ImageButton backS = (ImageButton)findViewById(R.id.back_detail_S);
        backS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myStartActivity(share_show_list.class,Cuser_id,Cuser_authority);
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
