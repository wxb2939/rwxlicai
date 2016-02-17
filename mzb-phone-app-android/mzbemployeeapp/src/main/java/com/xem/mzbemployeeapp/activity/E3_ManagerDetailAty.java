package com.xem.mzbemployeeapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.CalendarData;
import com.xem.mzbemployeeapp.entity.CustdetailData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/3.
 */
public class E3_ManagerDetailAty extends MzbActivity {

    @InjectView(R.id.detail_img)
    ImageView detailImg;
    @InjectView(R.id.detail_name)
    TextView detailName;
    @InjectView(R.id.detail_phone)
    TextView detailPhone;
    @InjectView(R.id.detail_num)
    TextView detailNum;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_right)
    ImageView selectRight;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.detail_list)
    PullToRefreshListView listView;
    @InjectView(R.id.ground)
    RelativeLayout ground;


    private Date date;
    private String branid;
    private String custid;
    private String dayStr;
    private List<CalendarData> data;
    private int curPage = 0;
    private View footView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.e3_managerdetail_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("会员137详情").setLeftImage(R.mipmap.top_view_back).setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(E3_ManagerDetailAty.this, Dialog137Activity.class), 0);
            }
        });
        branid = getIntent().getStringExtra("branid");
        custid = getIntent().getStringExtra("custid");
//        date = new Date();
        date=E2_Client137ManagerAty.date;
        if (date == null){
            date = new Date();
        }
        data = new ArrayList<CalendarData>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(E3_ManagerDetailAty.this,E3_Manager137ModifyAty.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("key", data.get(position-1));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                refreshDate(curPage + 1);
            }
        });

        footView = View.inflate(E3_ManagerDetailAty.this, R.layout.footview_loading, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getManagerDetail();
        refreshDate(0);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshDate(0);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_CANCELED:
                break;

            case RESULT_OK:
                switch (data.getIntExtra("age", -1)) {
                    case 1:
                        Intent brand = new Intent(E3_ManagerDetailAty.this, E3_Add137ManagerAty.class);
                        brand.putExtra("branid", branid);
                        brand.putExtra("custid",custid);
                        startActivity(brand);
                        break;
                    case 2:
                        ManagerUnfocus();
                        finish();
                        break;
                    default:
                        break;
                }
        }

    }

    @OnClick({R.id.select_right, R.id.select_left, R.id.titlebar_iv_left})
    public void click(View v) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()) {

            case R.id.select_left:
                c.add(c.MONTH, -1);
                date = c.getTime();
                if (date.getTime() <= System.currentTimeMillis()){
                    selectRight.setBackgroundResource(R.mipmap.left_new);
                    selectRight.setClickable(true);
                }
                getManagerDetail();
                refreshDate(0);
                break;
            case R.id.select_right:
                c.add(c.MONTH, 1);
                date = c.getTime();
                if (date.getTime() > System.currentTimeMillis()){
                    selectRight.setBackgroundResource(R.mipmap.right_none);
                    selectRight.setClickable(false);
                    c.add(c.MONTH, -1);
                    date = c.getTime();
                    break;
                }
                getManagerDetail();
                refreshDate(0);
                break;
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月");
        dayStr = sdf.format(date);
        selectTitle.setText(sdf1.format(date));
        getCalendarManagerList(num);
    }


    //3.13.6	客户计划日历
    public void getCalendarManagerList(final int page) {
        RequestParams params1 = new RequestParams();
//        { branid:1, empid:1，custid:1，date:“2015-7-4”，begin:0，count:10}
        params1.put("branid", branid);
        params1.put("empid", Config.getCachedBrandEmpid(this).toString());
        params1.put("custid", custid);
        params1.put("date", dayStr);
        params1.put("begin", page + "");
        params1.put("count", 10 + "");


        RequestUtils.ClientTokenPost(E3_ManagerDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_CALENDAR, params1, new NetCallBack() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            data.clear();
                        }
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CalendarData calendarData = gson.fromJson(object.toString(), CalendarData.class);
                            data.add(calendarData);
                        }
                        curPage = data.size();
                        add2removeFooterView();
                        listView.onRefreshComplete();

                    } else {
                        listView.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
            }
        });
    }

//3.13.3	取消关注

    public void ManagerUnfocus() {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("empid", Config.getCachedBrandEmpid(this).toString());
        params1.put("custid", custid);
        RequestUtils.ClientTokenPost(E3_ManagerDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_UNFOCUS, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("取消关注成功");
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    //3.13.5	客户计划详情
    public void getManagerDetail() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String daySt = sdf.format(date);
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("empid", Config.getCachedBrandEmpid(this).toString());
        params1.put("custid", custid);
        params1.put("date", daySt);

        RequestUtils.ClientTokenPost(E3_ManagerDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_CUSTDETAIL, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        CustdetailData custdetailData = gson.fromJson(obj.getJSONObject("data").toString(), CustdetailData.class);
                        imageLoader.displayImage(custdetailData.getPic(), detailImg);
                        detailName.setText(custdetailData.getName());
                        detailPhone.setText(custdetailData.getTel());
                        detailNum.setText(custdetailData.getCount().toString());
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }

    private void add2removeFooterView() {

        ListView lv = listView.getRefreshableView();
        adapter.notifyDataSetChanged();

        if (data.size() % 10 == 0 && data.size() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }


    // ViewHolder静态类
    static class ViewHolder {
        public TextView dcTime;
        public TextView dcType;
        public TextView dcFinished;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CalendarData getItem(int position) {
            return data.get(position);
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
                convertView = mInflater.inflate(R.layout.e3_managerdetailaty_cell, null);
                holder.dcTime = (TextView) convertView.findViewById(R.id.dc_time);
                holder.dcType = (TextView) convertView.findViewById(R.id.dc_type);
                holder.dcFinished = (TextView) convertView.findViewById(R.id.dc_finished);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date strdate = fmt.parse(data.get(position).getDate());
                DateFormat fmt1 =new SimpleDateFormat("MM月dd日");
                String startTime = fmt1.format(strdate);
                holder.dcTime.setText(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            switch (data.get(position).getType()){
                case 1:
                    holder.dcType.setText("预约来店");
                    break;
                case 2:
                    holder.dcType.setText("专业关怀");
                    break;
                case 3:
                    holder.dcType.setText("特殊关怀");
                    break;
                case 4:
                    holder.dcType.setText("到店护理");
                    break;
                case 5:
                    holder.dcType.setText("特殊标记");
                    break;
                default:
                    holder.dcType.setText("其他");
                    break;

            }

            if (data.get(position).getFinished()){
                holder.dcFinished.setText("已完成");
            }else {
                holder.dcFinished.setText("未完成");
            }

            return convertView;
        }
    }
}
