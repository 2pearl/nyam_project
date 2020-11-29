package com.example.nyam_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JoinChatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button button;
    private EditText editText;
    private ListView listView;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private String name, chat_msg, chat_user;

    private DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference().child("Join message");

    int Cuser_id,Cuser_authority;

    //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.chatoutBtn).setOnClickListener(onClickListener);
        findViewById(R.id.appointmentBtn).setOnClickListener(onClickListener);

        listView=findViewById(R.id.chatlist);
        button=findViewById(R.id.sendchatBtn);
        editText=findViewById(R.id.editTextchatcontents);


        Intent intent=getIntent();
        Cuser_id=intent.getIntExtra("user_id",0);
        Cuser_authority=intent.getIntExtra("user_authority",0);
        String nickname = intent.getExtras().getString("nickname");
        String phone_num = intent.getExtras().getString("phone num");


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);


        name = nickname+("(")+phone_num+(")\n");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Map<String, Object> map = new HashMap<String, Object>();

                String key=reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("name",name);
                objectMap.put("text",editText.getText().toString());

                root.updateChildren(objectMap);
                editText.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){
                chatConversation(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){
                chatConversation(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private void chatConversation(DataSnapshot dataSnapshot){
        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_user=(String)((DataSnapshot)i.next()).getValue();
            chat_msg=(String)((DataSnapshot)i.next()).getValue();

            arrayAdapter.add(chat_user+"    : "+chat_msg);
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chatoutBtn:
                    afterchatout();
                    break;
                case R.id.appointmentBtn:
                    appointment();
                    break;
            }
        }
    };

    private void afterchatout(){
        myStartActivity(endchattingActivity.class, Cuser_id, Cuser_authority);

        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

        mDatabase.getReference().child("Join message").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
            }
        });

        mDatabase.getReference().child("Join appointment").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
            }
        });
    }

    private void appointment(){
        String place = ((EditText) findViewById(R.id.appointPlaceEditText)).getText().toString();
        String date = ((EditText) findViewById(R.id.appointDateEditText)).getText().toString();
        String time = ((EditText) findViewById(R.id.appointTimeEditText)).getText().toString();
        TextView appointmentTV = (TextView)findViewById(R.id.appointmentTextView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Join appointment").child("appoint");

        DatabaseReference myRef2 = myRef.child("place");
        DatabaseReference myRef3 = myRef.child("date");
        DatabaseReference myRef4 = myRef.child("time");

        myRef2.setValue(place);
        myRef3.setValue(date);
        myRef4.setValue(time);

        DatabaseReference myRefget = database.getReference("Join appointment");

        myRefget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot:snapshot.getChildren()){
                    String applace = fileSnapshot.child("place").getValue(String.class);
                    String apdate = fileSnapshot.child("date").getValue(String.class);
                    String aptime = fileSnapshot.child("time").getValue(String.class);

                    appointmentTV.setText("★장소: "+applace+"　★날짜: "+apdate+"　★시간: "+aptime);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
