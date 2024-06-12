package com.example.nyam_project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Promo_board extends Board implements Serializable {

    String post_kind;
    String res_name;
    String res_phone;
    String res_addr;

    Promo_board(int post_num, String post_name, String post_contents,String post_kind,int user_id,String res_name, String res_phone, String res_addr){

        super(post_num,post_name,post_contents,user_id);

        this.post_num =post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;
        this.user_id=user_id;

        this.post_kind=post_kind;
        this.res_name=res_name;
        this.res_phone=res_phone;
        this.res_addr=res_addr;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("user_id",user_id);
        result.put("post_num", post_num);
        result.put("post_name", post_name);
        result.put("post_contents", post_contents);
        result.put("post_kind", post_kind);

        result.put("res_name", res_name);
        result.put("res_phone", res_phone);
        result.put("res_addr", res_addr);
        return result;
    }
     public int getPostnum(){
        return post_num;
     }

     public String getPostname(){
        return post_name;
     }

     public String getContents(){
        return post_contents;
     }

     public String getPostkind(){
        return post_kind;
     }

     public String getRes_name() {
        return res_name;
    }

     public String getRes_phone() {
        return res_phone;
    }

     public String getRes_addr() {
        return res_addr;
    }
}
