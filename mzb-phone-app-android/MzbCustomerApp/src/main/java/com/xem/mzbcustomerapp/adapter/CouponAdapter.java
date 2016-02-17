package com.xem.mzbcustomerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.activity.B1_BrandDetailActivity;
import com.xem.mzbcustomerapp.entity.CouponData;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/10/30.
 */
public class CouponAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater = null;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private List<CouponData> data;

    public CouponAdapter(Context context, List<CouponData> data) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CouponData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Get the row id associated with the specified position in the
        // list.
        // 获取在列表中与指定索引对应的行id
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.privilege_fragment_cell, null);
            holder.logo = (CircleImageView) convertView.findViewById(R.id.logo);
            holder.branch = (TextView) convertView.findViewById(R.id.branch);
            holder.cell_img = (ImageView) convertView.findViewById(R.id.cell_img);
            holder.cell_text = (TextView) convertView.findViewById(R.id.cell_text);
            holder.cell_text_two = (TextView) convertView.findViewById(R.id.cell_text_two);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String uri = LoadImgUtil.loadbigImg(data.get(position).getPlogo());
        imageLoader.displayImage(uri, holder.logo);
        holder.branch.setText(data.get(position).getPname());
        String buri = LoadImgUtil.loadbigImg(data.get(position).getPic());
        imageLoader.displayImage(buri, holder.cell_img);
        holder.cell_text.setText(data.get(position).getName());
//        holder.cell_text.setText("sssssssssssssssssssssssss");
        holder.cell_text_two.setText(data.get(position).getSdate()+"至" + data.get(position).getEdate());
        View brand=(LinearLayout)convertView.findViewById(R.id.brand);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, B1_BrandDetailActivity.class);
                intent.putExtra("ppid",data.get(position).getPpid());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        public CircleImageView logo;
        public TextView branch;  //美疗师
        public ImageView cell_img;
        public TextView cell_text;
        public TextView cell_text_two;
    }

}



