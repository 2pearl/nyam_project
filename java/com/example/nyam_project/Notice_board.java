package com.example.nyam_project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notice_board extends Board implements Serializable {


    Notice_board(int post_num, String post_name, String post_contents,int user_id){

        super(post_num,post_name,post_contents,user_id);

        this.post_num =post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;
        this.user_id=user_id;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("user_id",user_id);
        result.put("post_num", post_num);
        result.put("post_name", post_name);
        result.put("post_contents", post_contents);

        return result;
    }

    public int getPostnum(){
        return post_num;
    }
    public String getPostname(){
        return post_name;
    }
    public String getcontents(){
        return post_contents;
    }
}
