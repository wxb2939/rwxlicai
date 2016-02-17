package com.xem.mzbphoneapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.NotificationAty;
import com.xem.mzbphoneapp.atys.WebviewAty;
import com.xem.mzbphoneapp.model.NewsInfo;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.swipemenulistview.SwipeMenu;
import com.xem.swipemenulistview.SwipeMenuCreator;
import com.xem.swipemenulistview.SwipeMenuItem;
import com.xem.swipemenulistview.SwipeMenuListView;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xuebing on 15/6/29.
 */
public class E1_infoFg02 extends Fragment {

    private List<NewsInfo> data;
    private MzbAdapter adapter;
    private SwipeMenuListView serveLv;
    private View footView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        footView = inflater.inflate(R.layout.e1_info_fg, container, false);
        serveLv = (SwipeMenuListView) footView.findViewById(R.id.e1_infolist);
        if (Config.getCachedUid(getActivity()) != null) {
            data = new Select().from(NewsInfo.class).where("type in (?,?) and uid=? order by SendTime desc",
                    getResources().getString(R.string.e2_num_one),
                    getResources().getString(R.string.e2_num_two),
                    Config.getCachedUid(getActivity())).execute();


            if (data.size() == 0) {
                footView = inflater.inflate(R.layout.ground_info_back, container, false);
            } else {

                adapter = new MzbAdapter(getActivity());
                serveLv.setAdapter(adapter);


                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {

                        SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xbc, 0x31, 0x72)));
                        deleteItem.setWidth(dp2px(70));
                        deleteItem.setTitle("删除");
                        deleteItem.setTitleSize(15);
                        deleteItem.setTitleColor(Color.WHITE);
                        menu.addMenuItem(deleteItem);
                    }
                };
                // set creator
                serveLv.setMenuCreator(creator);

                serveLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position, SwipeMenu menu, int index) {

//                        data.remove(index);
                        data.get(position).delete();
                        data = new Select().from(NewsInfo.class).where("type in (?,?) and uid=? order by SendTime desc",
                                getResources().getString(R.string.e2_num_one),
                                getResources().getString(R.string.e2_num_two),
                                Config.getCachedUid(getActivity())).execute();
                        adapter.notifyDataSetChanged();

                    }
                });

                serveLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //打开自定义的Activity
                        Intent i = new Intent(getActivity(), WebviewAty.class);
                        i.putExtra("extra", data.get(position).extras);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
            }
        }
        return footView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView detail;
        public TextView time;
    }

    public class MzbAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MzbAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.e1_info_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.e1_info_img);
                holder.name = (TextView) convertView.findViewById(R.id.e1_info_name);
                holder.detail = (TextView) convertView.findViewById(R.id.e1_info_detail);
                holder.time = (TextView) convertView.findViewById(R.id.e1_time);
                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            if (data.get(position).type.equals("100001")) {
                holder.img.setImageResource(R.mipmap.sys_img);
                holder.name.setText("美之伴"); //优惠活动
            } else {
                holder.img.setImageResource(R.mipmap.sys_img);  //美容常识
                holder.name.setText("美之伴");
            }

            holder.detail.setText(data.get(position).detail);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
            String dstr = data.get(position).sendTime;
            try {
                Date date1 = sdf1.parse(dstr);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                holder.time.setText(sdf.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
