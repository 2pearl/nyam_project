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


public class ask_detail extends AppCompatActivity{

    int post_num,Cuser_id,Cuser_authority,user_id;
    String postName,postContents,postComment;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), Q_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_detail);

        Intent intent=getIntent();
        TextView post_name=(TextView)findViewById(R.id.textviewT_A);
        TextView post_contents=(TextView)findViewById(R.id.Acontents);
        TextView postcomment=(TextView)findViewById(R.id.textView22);

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);

        user_id=intent.getIntExtra("user_id",0);
        post_num=intent.getIntExtra("post_num",0);
        postName=intent.getExtras().getString("post_name");
        postContents=intent.getExtras().getString("post_contents");
        postComment=intent.getExtras().getString("post_comment");

        post_name.setText(postName);
        post_contents.setText(postContents);
        postcomment.setText(postComment);

        Button delA = (Button) findViewById(R.id.buttonD_A) ;
        delA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Cuser_id==user_id){
                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("/QBoard/"+post_num).removeValue();

                    Intent iiiintent= new Intent(getApplicationContext(), Q_show_list.class);
                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    startActivity(iiiintent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button addCom = (Button) findViewById(R.id.Addcomment) ;
        addCom.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Cuser_authority==0){
                    Intent intent= new Intent(getApplicationContext(), ask_writing_comment.class);

                    intent.putExtra("Cuser_id",Cuser_id);
                    intent.putExtra("Cuser_authority",Cuser_authority);

                    intent.putExtra("post_num",post_num);
                    intent.putExtra("post_name",postName);
                    intent.putExtra("post_contents",postContents);
                    intent.putExtra("post_comment",postComment);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageButton backQ = (ImageButton)findViewById(R.id.back_detail_Q);
        backQ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iiiintent= new Intent(getApplicationContext(), Q_show_list.class);
                iiiintent.putExtra("Cuser_id",Cuser_id);
                iiiintent.putExtra("Cuser_authority",Cuser_authority);
                startActivity(iiiintent);
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
