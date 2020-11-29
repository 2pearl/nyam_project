package com.example.nyam_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class n_ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Notice_board> nb;
    LayoutInflater mLayoutInflater = null;

    public n_ItemAdapter(Context context, ArrayList<Notice_board> data) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        nb = new ArrayList<Notice_board>();

        for(int i=0;i<data.size();i++){
            Notice_board n=data.get(i);
            nb.add(n);
        }
    }

    public n_ItemAdapter(){}
    public void addItem(Notice_board n){
        nb.add(n);
    }

    @Override
    public int getCount() {
        return nb.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Notice_board getItem(int position) {
        return nb.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent,false);

        TextView nname = (TextView)convertView.findViewById(R.id.postName);
        nname.setText(nb.get(position).getPostname());
        return convertView;
    }
}
