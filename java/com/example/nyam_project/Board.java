package com.example.nyam_project;

public class Board {

    String post_name;
    int post_num;
    String post_contents;
    int user_id;

    public Board(){}
    public Board(int post_num,String post_name,String post_contents,int user_id){
        this.post_num=post_num;
        this.post_name=post_name;
        this.post_contents=post_contents;
        this.user_id=user_id;
    }
}
