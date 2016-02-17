package com.xem.mzbemployeeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.ServicedoneDetail;

import java.util.List;

/**
 * Created by xuebing on 15/8/13.
 */
public class ServiceDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ServicedoneDetail> mList;

    public ServiceDetailAdapter(Context context, List<ServicedoneDetail> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getAction().equals(1)){
            return 0;
        }else {
            return 1;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ViewHolderTwo viewHoldertwo;

        if (getItemViewType(position) == 1){
            if (convertView == null){
                viewHolder = new ViewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.e3_servicedetail_cell_two,null);

                viewHolder.fuxm = (TextView) convertView.findViewById(R.id.fu_xm);
                viewHolder.fugw = (TextView) convertView.findViewById(R.id.fu_gu);
                viewHolder.fumls = (TextView) convertView.findViewById(R.id.fu_mls);
                viewHolder.fusj = (TextView) convertView.findViewById(R.id.fu_sj);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.fuxm.setText(mList.get(position).getName());
            viewHolder.fugw.setText(mList.get(position).getFirst());
            viewHolder.fumls.setText(mList.get(position).getSecond());
            viewHolder.fusj.setText(mList.get(position).getOpen());
        }else {

            if (convertView == null){
                viewHoldertwo = new ViewHolderTwo();
                LayoutInflater layoutInflater1 = LayoutInflater.from(context);
                convertView = layoutInflater1.inflate(R.layout.e3_servicedetail_cell,null);

                viewHoldertwo.gmxm = (TextView) convertView.findViewById(R.id.gm_xm);
                viewHoldertwo.gmzx = (TextView) convertView.findViewById(R.id.gm_zx);
                viewHoldertwo.gmfx = (TextView) convertView.findViewById(R.id.gm_fx);
                viewHoldertwo.gmsj = (TextView) convertView.findViewById(R.id.gm_sj);
                convertView.setTag(viewHolder);
            }else {
                viewHoldertwo = (ViewHolderTwo) convertView.getTag();
            }
            viewHoldertwo.gmxm.setText(mList.get(position).getName());
            viewHoldertwo.gmzx.setText(mList.get(position).getFirst());
            viewHoldertwo.gmfx.setText(mList.get(position).getSecond());
            viewHoldertwo.gmsj.setText(mList.get(position).getOpen());
        }

        return convertView;
    }



    private static class ViewHolder{
        TextView fuxm;
        TextView fugw;
        TextView fumls;
        TextView fusj;

    }

    private static class ViewHolderTwo{
        TextView gmxm;
        TextView gmzx;
        TextView gmfx;
        TextView gmsj;
    }


}
