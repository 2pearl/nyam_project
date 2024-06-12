package com.example.nyam_project;

import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Join_board extends Board implements Serializable {

    String people_num;
    int post_num;
    String user_gender;
    String post_date;

    public Join_board(){
        super();
    }

    public Join_board(int post_num,String people_num, String user_gender, String post_date, String post_name, String post_contents,int user_id){
        super(post_num,post_name,post_contents,user_id);

        this.people_num=people_num;
        this.post_num =post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;
        this.post_date=post_date;
        this.user_gender=user_gender;
        this.user_id=user_id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("user_id",user_id);
        result.put("post_name", post_name);
        result.put("post_contents", post_contents);
        result.put("post_num", post_num);
        result.put("people_num", people_num);
        result.put("post_date", post_date);
        result.put("user_gender", user_gender);

        return result;
    }
    public String getPeople_num(){
        return people_num;
    }
    public String getUser_gender(){
        return user_gender;
    }
    public String getPost_date(){
        return post_date;
    }
    public int getpost_num(){
        return post_num;
    }
    public String getpost_name(){
        return post_name;
    }
    public String getpost_contents(){
        return post_contents;
    }


}
