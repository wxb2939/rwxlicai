package com.xem.mzbcustomerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.entity.GroupItem;

import java.util.List;

/**
 * Created by xuebing on 15/11/27.
 */
public class SelectXmGroupAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<GroupItem> lists;
    int last_item;
    private int selectedPosition = -1;
    public SelectXmGroupAdapter(Context context,List<GroupItem> lists){
        this.context = context;
        this.lists = lists;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return lists.size();
    }
    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.mylist_item, null);
            holder = new ViewHolder();
            holder.textView =(TextView)convertView.findViewById(R.id.textview);
            holder.layout=(LinearLayout)convertView.findViewById(R.id.colorlayout);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
// 设置选中效果
        if(selectedPosition == position)
        {
            holder.textView.setTextColor(Color.BLUE);
            holder.layout.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.textView.setTextColor(Color.WHITE);
            holder.layout.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.textView.setText(lists.get(position).getName());
        holder.textView.setTextColor(Color.BLACK);
        int sleNum = lists.get(position).getSelNum();
        if (sleNum > 0){
            holder.num.setText(sleNum+"");
        }else {
            holder.num.setText("");
        }

        return convertView;
    }
    public static class ViewHolder{
        public TextView textView;
        public TextView num;
        public LinearLayout layout;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}