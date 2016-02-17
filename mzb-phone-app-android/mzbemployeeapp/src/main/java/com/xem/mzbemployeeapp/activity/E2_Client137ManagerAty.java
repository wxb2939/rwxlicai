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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.CustomersData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.utils.Utility;

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

/**
 * Created by xuebing on 15/9/1.
 */
public class E2_Client137ManagerAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e2_storelist)
    PullToRefreshListView clientlist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.select_right)
    ImageView selectRight;

    private MyAdapter adapter;
    private String empid;
    private String branid;
    private int curPage = 0;
    private View footView;
    private View view;
    private JSONArray jsonArray;

    public static Date date;
    String dayStr;

    private List<CustomersData> data = new ArrayList<CustomersData>();
    boolean over=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_storefatisfy_aty);
        new TitleBuilder(this).setTitleText("会员137管理").setLeftImage(R.mipmap.top_view_back).setRightImage(R.mipmap.top_add);
        ButterKnife.inject(this);


        clientlist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                GetClient137ManagerList(0);
                over=false;
                refreshDate(0);
            }
        });


        clientlist.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
//                GetClient137ManagerList(curPage + 1);
                if (over){
                    return;
                }
                refreshDate(curPage + 1);
            }
        });

        clientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(E2_Client137ManagerAty.this, E3_ManagerDetailAty.class);
                Bundle bundle = new Bundle();
                bundle.putString("branid", branid);
                bundle.putString("custid", data.get(position - 1).getCustid().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        footView = View.inflate(E2_Client137ManagerAty.this, R.layout.footview_loading, null);

        date = new Date();
    }

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayStr = sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月");
        selectTitle.setText(sdf1.format(date));
        GetClient137ManagerList(num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        branid = getIntent().getStringExtra("branid");
//        GetClient137ManagerList(0);
        refreshDate(0);
        adapter = new MyAdapter(E2_Client137ManagerAty.this);
        clientlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_iv_right,R.id.select_right, R.id.select_left})
    public void onDo(View v) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.titlebar_iv_right:
                Intent brand = new Intent(E2_Client137ManagerAty.this, E3_AddclientAty.class);
                brand.putExtra("branid", branid);
                startActivity(brand);
                break;
            case R.id.select_left:
                c.add(c.MONTH, -1);
                date = c.getTime();
                if (date.getTime() <= System.currentTimeMillis()){
                    selectRight.setBackgroundResource(R.mipmap.left_new);
                    selectRight.setClickable(true);
                }
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
                refreshDate(0);
                break;
            default:
                break;
        }
    }


    public void GetClient137ManagerList(final int page) {
        RequestParams params1 = new RequestParams();
//        { branid:1, empid:1，date:“2015-11-10”，begin:0，count:10 }
        params1.put("branid", branid);
        params1.put("empid", Config.getCachedBrandEmpid(E2_Client137ManagerAty.this).toString());
        params1.put("date", dayStr);
        params1.put("begin", page + "");
        params1.put("count", 10 + "");
        RequestUtils.ClientTokenPost(E2_Client137ManagerAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_CUSTOMERS, params1, new NetCallBack(this) {

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
                            CustomersData customersData = gson.fromJson(object.toString(), CustomersData.class);
                            data.add(customersData);
                        }
                        view = Utility.getInstance().getDataLayout(E2_Client137ManagerAty.this, data.size() != 0, ground, "暂无数据", R.mipmap.nodata_err, view);
                        curPage = data.size();
                        add2removeFooterView();
                        clientlist.onRefreshComplete();
                        if (jsonArray.length() == 0){
                            over=true;
                        }
                    } else {
                        clientlist.onRefreshComplete();
                        showToast(obj.getString("message"));
                        over=false;
                    }
                } catch (JSONException e) {
                    over=false;
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
                over=false;
            }
        });
    }


    static class ViewHolder {
        public ImageView clientlogo;
        public TextView clientname;
        public TextView clientphone;
        public TextView clientnum;
        public TextView unfinished_num;
        public ImageView sign_img;
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
        public CustomersData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_client137manageraty_cell, null);
                holder.clientlogo = (ImageView) convertView.findViewById(R.id.client_img);
                holder.clientname = (TextView) convertView.findViewById(R.id.client_name);
                holder.clientphone = (TextView) convertView.findViewById(R.id.client_phone);
                holder.clientnum = (TextView) convertView.findViewById(R.id.client_num);
                holder.unfinished_num= (TextView) convertView.findViewById(R.id.unfinished_num);
                holder.sign_img= (ImageView) convertView.findViewById(R.id.sign_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.clientlogo);
            holder.clientname.setText(data.get(position).getName());
            holder.clientphone.setText(data.get(position).getTel());
            holder.clientnum.setText(data.get(position).getCount().toString());
            holder.unfinished_num.setText(data.get(position).getUnfinished().toString());
            if (data.get(position).hasplan!=null && data.get(position).hasplan.equals("true")){
                holder.sign_img.setVisibility(View.VISIBLE);
            }else{
                holder.sign_img.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    private void add2removeFooterView() {
        ListView lv = clientlist.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 10 == 0 && data.equals(0) && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }

}
