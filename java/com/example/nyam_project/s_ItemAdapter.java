package com.example.nyam_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class s_ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Share_board> sb;
    LayoutInflater mLayoutInflater = null;

    public s_ItemAdapter(Context context, ArrayList<Share_board> data) {
        this.mContext = context;
        //this.jb = data;
        mLayoutInflater = LayoutInflater.from(mContext);

        sb = new ArrayList<Share_board>();

        Log.d("data size",String.valueOf(data.size()));
        for(int i=0;i<data.size();i++){
            Share_board s=data.get(i);
            sb.add(s);
        }

    }

    public s_ItemAdapter(){}
    public void addItem(Share_board s){
        sb.add(s);
    }

    @Override
    public int getCount() {
        return sb.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Share_board getItem(int position) {
        return sb.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TextView pname;//=null;
        //View rowView=convertView;
        //mLayoutInflater = LayoutInflater.from(mContext);
        //Context context=parent.getContext();

        //if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent,false);
            View view = mLayoutInflater.inflate(R.layout.list_item, null);
            TextView sname = (TextView)convertView.findViewById(R.id.postName);


       // }
        //Join_board item=jb.get(position);
       // else{
            //pname=(TextView)convertView.getTag();
        //}

        sname.setText(sb.get(position).getPost_name());


        return convertView;
    }

}
