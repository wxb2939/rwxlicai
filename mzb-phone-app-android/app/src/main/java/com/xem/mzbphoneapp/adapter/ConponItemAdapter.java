package com.xem.mzbphoneapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.Coupon;
import com.xem.mzbphoneapp.utils.CompareTime;

import java.util.List;

/**
 * Created by xuebing on 15/7/25.
 */
public class ConponItemAdapter extends BaseAdapter {

    List<Coupon> list;
    Context context;
    LayoutInflater mInflater;

    public ConponItemAdapter(Context c, List<Coupon> list){
        this.context = c;
        this.list = list;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.coupon_item, null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.pname = (TextView) convertView.findViewById(R.id.pname);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.time_over = (TextView) convertView.findViewById(R.id.time_over);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getPic(), holder.img);
        holder.pname.setText(list.get(position).getPname());
        holder.name.setText(list.get(position).getName());
        holder.time.setText(list.get(position).getEdate());
        if (CompareTime.compare(list.get(position).getEdate())){
            holder.time_over.setText("已过期");
        }

        return convertView;
    }

    class Holder
    {
        ImageView img;
        TextView pname;
        TextView name;
        TextView time;
        TextView time_over;
    }
}
