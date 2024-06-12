package com.example.nyam_project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Share_board extends Board implements Serializable {

    String share_kind;
    String share_way;
    String share_place;

    Share_board(int post_num, String post_name, String post_contents,String share_kind, String share_way, String share_place, int user_id){

        super(post_num,post_name,post_contents,user_id);

        this.post_num =post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;

        this.share_kind=share_kind;
        this.share_way=share_way;
        this.share_place=share_place;

        this.user_id=user_id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("user_id",user_id);
        result.put("post_num", post_num);
        result.put("post_name", post_name);
        result.put("post_contents", post_contents);

        result.put("share_kind", share_kind);
        result.put("share_way", share_way);
        result.put("share_place", share_place);

        return result;
    }

    public int getPost_num(){
        return post_num;
    }

    public String getPost_name(){
        return post_name;
    }

    public String getContents(){
        return post_contents;
    }

    public String getKind(){
        return share_kind;
    }

    public String getWay(){
        return share_way;
    }

    public String getPlace(){
        return share_place;
    }
}
