package com.xem.mzbemployeeapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;

import java.util.ArrayList;

/**
 * Created by wellke on 2015/12/6.
 */
public class TimeSelectAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context context;
    int selectedPosition = -1;
    public TimeSelectAdapter(ArrayList<String> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView= LayoutInflater.from(context).inflate(R.layout.time_sele_item,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.text_item);
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.colorlayout);
        textView.setText(list.get(position));
        // 设置选中效果
        if(selectedPosition == position)
        {
            textView.setTextColor(Color.BLUE);
            layout.setBackgroundColor(Color.LTGRAY);
        } else {
           textView.setTextColor(Color.WHITE);
           layout.setBackgroundColor(Color.TRANSPARENT);
        }
        textView.setTextColor(Color.BLACK);
        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

}
