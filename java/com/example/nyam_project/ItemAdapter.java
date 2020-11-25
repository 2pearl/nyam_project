package com.example.nyam_project;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Join_board> jb;
    LayoutInflater mLayoutInflater = null;

    public ItemAdapter(Context context, ArrayList<Join_board> data) {
        this.mContext = context;
        //this.jb = data;
        mLayoutInflater = LayoutInflater.from(mContext);

        jb = new ArrayList<Join_board>();

        Log.d("data size",String.valueOf(data.size()));
        for(int i=0;i<data.size();i++){
            Join_board n=data.get(i);
            jb.add(n);
        }

    }

    public ItemAdapter(){}

    public ItemAdapter(share_show_list context, ArrayList<Share_board> slist) {
    }

    public void addItem(Join_board j){
        jb.add(j);
    }

    @Override
    public int getCount() {
        return jb.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Join_board getItem(int position) {
        return jb.get(position);
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
            TextView pname = (TextView)convertView.findViewById(R.id.postName);



       // }
        //Join_board item=jb.get(position);
       // else{
            //pname=(TextView)convertView.getTag();
        //}

        pname.setText(jb.get(position).getpost_name());


        return convertView;
    }

}
