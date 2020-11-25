package com.example.nyam_project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Q_board extends Board implements Serializable {

    String post_comment;

    Q_board(int post_num, String post_name, String post_contents,String post_comment,int user_id){

        super(post_num,post_name,post_contents,user_id);

        this.post_num =post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;
        this.post_comment=post_comment;
        this.user_id=user_id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("user_id",user_id);
        result.put("post_num", post_num);
        result.put("post_name", post_name);
        result.put("post_contents", post_contents);
        result.put("post_comment", post_comment);

        return result;
    }

    public int getnum(){
        return post_num;
    }
    public String getName(){
        return post_name;
    }
    public String getContents(){
        return post_contents;
    }
    public String getPost_comment(){return post_comment;}
}
