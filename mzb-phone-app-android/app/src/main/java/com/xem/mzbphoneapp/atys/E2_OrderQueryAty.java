package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.OrderQuery;
import com.xem.mzbphoneapp.entity.Store;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_order_back;

/**
 * Created by xuebing on 15/7/23.
 */
public class E2_OrderQueryAty extends MzbActivity {

    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_right)
    ImageView selectRight;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.e2_order_query_list)
    PullToRefreshListView orderQueryList;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private Date date;
    private List<OrderQuery> data;
    private Store storeInfo;
    private MyAdapter adapter;
    private View footView;
    private View view;
    private int curPage = 0;
    private String dayStr = "";
    private String branid = "";
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_order_query_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("预约查询").setLeftImage(R.mipmap.top_view_back);

        branid = getIntent().getStringExtra("branid");
        date = new Date();
        data = new ArrayList<OrderQuery>();
        refreshDate(0);

        adapter = new MyAdapter(E2_OrderQueryAty.this);
        orderQueryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        orderQueryList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshDate(0);
            }
        });
        orderQueryList.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                refreshDate(curPage + 1);
            }
        });
        footView = View.inflate(E2_OrderQueryAty.this, R.layout.footview_loading, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View grounds = View.inflate(E2_OrderQueryAty.this, ground_order_back, null);
    }

    @OnClick({R.id.titlebar_iv_left,R.id.select_right,R.id.select_left})
      public void click(View v){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.select_left:
                c.add(c.DATE, -1);
                date = c.getTime();
                refreshDate(0);
                break;
            case R.id.select_right:
                c.add(c.DATE, 1);
                date = c.getTime();
                refreshDate(0);
                break;
            default:
                break;
        }
    }

    private void refreshDate(int num)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        dayStr=sdf.format(date);
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy年MM月dd日");
        selectTitle.setText(sdf1.format(date));
        GetServeRecordList(num);
    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("date", dayStr);
        params1.put("begin", page+"");
        params1.put("count", 20+"");

        RequestUtils.ClientTokenPost(E2_OrderQueryAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_BRANCH, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            data.clear();
                        }

                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            OrderQuery orderQuery = gson.fromJson(object.toString(), OrderQuery.class);
                            data.add(orderQuery);
                        }
                        view  = Utility.getInstance().getDataLayout(E2_OrderQueryAty.this,data.size() != 0,ground,"暂无数据",R.mipmap.nodata_err,view);
                        curPage = data.size();
                        add2removeFooterView();
                        orderQueryList.onRefreshComplete();
                    } else {
                        orderQueryList.onRefreshComplete();
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
        ListView lv = orderQueryList.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 10 == 0 && data.equals(0) && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }


    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView time;  //美疗师
        public TextView waiter;
        public TextView comments;

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
        public OrderQuery getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_orderquery_aty_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.e2_order_pic);
                holder.name = (TextView) convertView.findViewById(R.id.e2_order_name);
                holder.time = (TextView) convertView.findViewById(R.id.e2_order_time);
                holder.waiter = (TextView) convertView.findViewById(R.id.e2_order_waiter);
                holder.comments = (TextView) convertView.findViewById(R.id.e2_btn_comment);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPortrait(), holder.img);
            holder.name.setText(data.get(position).getName());
            holder.time.setText(data.get(position).getTime());
            holder.waiter.setText(data.get(position).getWaiter());

            if (data.get(position).getState().equals("4") ) {
                holder.comments.setText("已到店");
                holder.comments.setTextColor(getResources().getColor(R.color.recordone));
            } else {
                holder.comments.setText("未到店");
                holder.comments.setTextColor(getResources().getColor(R.color.recordtwo));
            }
            return convertView;
        }
    }
}
