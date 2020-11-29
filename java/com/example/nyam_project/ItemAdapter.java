package com.example.nyam_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Join_board> jb;
    LayoutInflater mLayoutInflater = null;

    public ItemAdapter(Context context, ArrayList<Join_board> data) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        jb = new ArrayList<Join_board>();

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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent,false);

        TextView pname = (TextView)convertView.findViewById(R.id.postName);
        pname.setText(jb.get(position).getpost_name());
        return convertView;
    }
}
