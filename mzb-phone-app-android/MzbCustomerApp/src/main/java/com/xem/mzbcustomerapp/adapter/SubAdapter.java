package com.xem.mzbcustomerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.entity.GoodsSubCateData;

import java.util.List;

/**
 * Created by xuebing on 15/10/24.
 */
public class SubAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<GoodsSubCateData> subCates;
    public SubAdapter(Context context, List<GoodsSubCateData> subCates) {
        this.context = context;
        this.subCates = subCates;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return subCates.size();
    }
    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return getItem(position);
    }
    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        final int location=position;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.sublist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview1);
//            viewHolder.textnum = (TextView) convertView.findViewById(R.id.num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(subCates.get(position).getName());
        viewHolder.textView.setTextColor(Color.BLACK);
//        viewHolder.textnum.setText(subCates.get(position).getCount());

        return convertView;
    }
    public static class ViewHolder {
        public TextView textView;
        public TextView textnum;
    }
}
