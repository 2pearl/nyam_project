package com.example.nyam_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class p_ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Promo_board> pb;
    LayoutInflater mLayoutInflater = null;

    public p_ItemAdapter(Context context, ArrayList<Promo_board> data) {
        this.mContext = context;
        //this.jb = data;
        mLayoutInflater = LayoutInflater.from(mContext);

        pb = new ArrayList<Promo_board>();

        Log.d("data size",String.valueOf(data.size()));
        for(int i=0;i<data.size();i++){
            Promo_board p=data.get(i);
            pb.add(p);
        }

    }

    public p_ItemAdapter(){}
    public void addItem(Promo_board p){
        pb.add(p);
    }

    @Override
    public int getCount() {
        return pb.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Promo_board getItem(int position) {
        return pb.get(position);
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

        pname.setText(pb.get(position).getPostname());


        return convertView;
    }

}
