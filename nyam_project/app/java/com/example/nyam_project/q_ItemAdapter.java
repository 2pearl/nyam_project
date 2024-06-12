package com.example.nyam_project;

import android.content.Context;
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
        mLayoutInflater = LayoutInflater.from(mContext);

        qb = new ArrayList<Q_board>();

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


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent,false);
        TextView nname = (TextView)convertView.findViewById(R.id.postName);
        nname.setText(qb.get(position).getName());

        return convertView;
    }
}
