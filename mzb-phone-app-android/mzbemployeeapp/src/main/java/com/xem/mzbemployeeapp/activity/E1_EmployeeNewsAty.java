package com.xem.mzbemployeeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.model.NewsInfo;
import com.xem.mzbemployeeapp.utils.CommLogin;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.SwipeMenu;
import com.xem.mzbemployeeapp.views.SwipeMenuCreator;
import com.xem.mzbemployeeapp.views.SwipeMenuItem;
import com.xem.mzbemployeeapp.views.SwipeMenuListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xuebing on 15/7/10.
 */
public class E1_EmployeeNewsAty extends MzbActivity {


    private List<NewsInfo> data;
    private MzbAdapter adapter;
    private SwipeMenuListView serveLv;
    private SimpleDateFormat sdf,format;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.employee_news_aty);
        new TitleBuilder(this).setTitleText("员工消息").setLeftImage(R.mipmap.top_view_back).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.getCachedIsexp(E1_EmployeeNewsAty.this) == null) {
                    intent2Aty(HomeActivity.class);
                }else{
                    intent2Aty(ExperienceActivity.class);
                }
                finish();
            }
        });
        JPushInterface.resumePush(getApplicationContext());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format = new SimpleDateFormat("MM-dd HH:mm");

//        E1_EmployeeNewsAty
        if (Config.getCachedIsexp(E1_EmployeeNewsAty.this) != null){
            new CommLogin(E1_EmployeeNewsAty.this).DoLogin(A0_ExpAty.str, application);
        }else {
            new CommLogin(E1_EmployeeNewsAty.this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
        }
        serveLv = (SwipeMenuListView) findViewById(R.id.e2_infolist);
        if (Config.getCachedUid(E1_EmployeeNewsAty.this) != null) {
            data = new Select().from(NewsInfo.class).where("type in (?,?,?,?) and uid=? order by SendTime desc",
                    getResources().getString(R.string.e3_num_one),
                    getResources().getString(R.string.e3_num_two),
                    getResources().getString(R.string.e3_num_three),
                    "200009",
                    Config.getCachedUid(E1_EmployeeNewsAty.this)).execute();

            if (data.size() == 0) {
                RelativeLayout ground = (RelativeLayout) findViewById(R.id.e1_employee_ground);
                View grounds = View.inflate(E1_EmployeeNewsAty.this, R.layout.ground_info_back, null);
                ground.addView(grounds);
//            ground.setBackgroundResource(R.mipmap.e1_noinfo_ground);

            } else {
                adapter = new MzbAdapter(E1_EmployeeNewsAty.this);
                serveLv.setAdapter(adapter);
                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        SwipeMenuItem shareItem = new SwipeMenuItem(getApplicationContext());
                        shareItem.setBackground(new ColorDrawable(Color.rgb(0xbc, 0x31, 0x72)));
                        shareItem.setWidth(dp2px(70));
                        shareItem.setTitle("删除");
                        shareItem.setTitleSize(15);
                        shareItem.setTitleColor(Color.WHITE);
                        menu.addMenuItem(shareItem);
                    }
                };
                // set creator
                serveLv.setMenuCreator(creator);
                serveLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position, SwipeMenu menu, int index) {

//                    data.remove(index);
                        data.get(position).delete();
                        data = new Select().from(NewsInfo.class).where("type in (?,?,?,?) and uid=? order by SendTime desc",
                                getResources().getString(R.string.e3_num_one),
                                getResources().getString(R.string.e3_num_two),
                                getResources().getString(R.string.e3_num_three),
                                "200009",
                                Config.getCachedUid(E1_EmployeeNewsAty.this)).execute();
                        adapter.notifyDataSetChanged();
                    }
                });
                serveLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //打开自定义的Activity
                        Intent i = new Intent(E1_EmployeeNewsAty.this, WebviewAty.class);
                        try {
                            JSONObject jsonObject = new JSONObject(data.get(position).extras);
                            if (jsonObject.length() != 0) {
                                if (jsonObject.isNull("url")) {
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        i.putExtra("extra", data.get(position).extras);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (Config.getCachedIsexp(this) == null) {
                intent2Aty(HomeActivity.class);
            } else {
                intent2Aty(ExperienceActivity.class);
            }
        }
        return super.onKeyDown(keyCode, event);
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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            if (data.get(position).type.equals("200001")) {
                holder.img.setImageResource(R.mipmap.mendian);
                holder.name.setText(getResources().getString(R.string.e3_title_one));
            } else if (data.get(position).type.equals("200002")) {
                holder.img.setImageResource(R.mipmap.shengri);
                holder.name.setText(getResources().getString(R.string.e3_title_two));
            } else if (data.get(position).type.equals("200003")) {
                holder.img.setImageResource(R.mipmap.info37);
                holder.name.setText(getResources().getString(R.string.e3_title_three));
            } else if (data.get(position).type.equals("200009")) {
                holder.img.setImageResource(R.mipmap.yuyue_img);
                holder.name.setText("137跟进提醒");
            }else{
                holder.img.setImageResource(R.mipmap.yuyue_img);
                holder.name.setText(getResources().getString(R.string.e3_title_five));
            }

            holder.detail.setText(data.get(position).detail);
            try {
                date = sdf.parse(data.get(position).sendTime);
                holder.time.setText(format.format(date));
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
