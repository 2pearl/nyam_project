package com.example.nyam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ask_writing_comment extends AppCompatActivity {

    int post_num,Cuser_id,Cuser_authority;
    String postName,postContents;

    public void onBackPressed(){
        Intent iiiintent= new Intent(getApplicationContext(), Q_show_list.class);
        iiiintent.putExtra("Cuser_id",Cuser_id);
        iiiintent.putExtra("Cuser_authority",Cuser_authority);
        startActivity(iiiintent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_writingcomment);


        Intent intent = getIntent();

        Cuser_id=intent.getIntExtra("Cuser_id",0);
        Cuser_authority=intent.getIntExtra("Cuser_authority",0);
        post_num=intent.getIntExtra("post_num",0);
        postName=intent.getExtras().getString("post_name");
        postContents=intent.getExtras().getString("post_contents");
        String postComment=intent.getExtras().getString("post_comment");

        EditText comment=(EditText)findViewById(R.id.Acomment);

        Button commentB = (Button) findViewById(R.id.commentB) ;
        commentB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String commentS=comment.getText().toString();

                if (commentS.equals("")) {
                    Toast.makeText(getApplicationContext(), "입력되지 않은 곳이 있습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
                    HashMap<String,Object> newpost=new HashMap();

                    Q_board j=new Q_board(post_num,postName,postContents,commentS,Cuser_id);
                    Map<String,Object> userValue=j.toMap();

                    newpost.put("/QBoard/"+post_num,userValue);
                    Database.updateChildren(newpost);

                    Intent iiiintent= new Intent(getApplicationContext(), Q_show_list.class);
                    iiiintent.putExtra("Cuser_id",Cuser_id);
                    iiiintent.putExtra("Cuser_authority",Cuser_authority);
                    startActivity(iiiintent);
                }
            }
        });

    }
}
