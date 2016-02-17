package com.xem.mzbcustomerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.entity.GridViewDetail;

import java.util.ArrayList;

/**
 * Created by xuebing on 15/11/25.
 */
public class TextAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<GridViewDetail> list;

    public TextAdapter(Context mContext,ArrayList<GridViewDetail> list){
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? null:list.get(6%position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.mygrid_item,null);
        }
        if (position < list.size()){
            GridViewDetail obj=list.get(position);
            ((ImageView)convertView.findViewById(R.id.images)).setImageResource(obj.images);
            ((TextView)convertView.findViewById(R.id.tex)).setText(obj.desc);
        }
        return convertView;
    }
}