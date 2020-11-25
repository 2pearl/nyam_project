package com.example.nyam_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class q_ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Q_board> qb;
    LayoutInflater mLayoutInflater = null;

    public q_ItemAdapter(Context context, ArrayList<Q_board> data) {
        this.mContext = context;
        //this.jb = data;
        mLayoutInflater = LayoutInflater.from(mContext);

        qb = new ArrayList<Q_board>();

        Log.d("data size",String.valueOf(data.size()));
        for(int i=0;i<data.size();i++){
            Q_board n=data.get(i);
            qb.add(n);
        }

    }

    public q_ItemAdapter(){}
    public void addItem(Q_board n){
        qb.add(n);
    }

    @Override
    public int getCount() {
        return qb.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Q_board getItem(int position) {
        return qb.get(position);
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
            TextView nname = (TextView)convertView.findViewById(R.id.postName);


       // }
        //Join_board item=jb.get(position);
       // else{
            //pname=(TextView)convertView.getTag();
        //}

        nname.setText(qb.get(position).getName());


        return convertView;
    }

}
