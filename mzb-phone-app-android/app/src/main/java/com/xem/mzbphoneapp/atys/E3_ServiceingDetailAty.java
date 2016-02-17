package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.ServiceingDetail;
import com.xem.mzbphoneapp.entity.TbsercustData;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/12.
 */
public class E3_ServiceingDetailAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e3_name)
    TextView name;
    @InjectView(R.id.e3_photo)
    ImageView photo;
    @InjectView(R.id.e3_phone)
    TextView phone;
    @InjectView(R.id.e3_time)
    TextView time;
    @InjectView(R.id.list)
    PullToRefreshListView listview;

    private String cus = "";
    private String ttp = "";
    private String ppid = "";
    private List<ServiceingDetail> list;
    private MyAdapter adapter;
    private View footView;
    private int curPage = 0;
    private JSONArray data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_service_detail_aty);
        new TitleBuilder(E3_ServiceingDetailAty.this).setLeftImage(R.mipmap.top_view_back).setTitleText("服务详情(服务中)");
        cus = getIntent().getStringExtra("custid");
        ttp = getIntent().getStringExtra("branid");
        ppid = getIntent().getStringExtra("ppid");

        ButterKnife.inject(this);
        list = new ArrayList<ServiceingDetail>();
        GetServiceinfo();
        adapter = new MyAdapter(E3_ServiceingDetailAty.this);
        listview.setAdapter(adapter);
        GetService(0);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetService(0);
            }
        });

        listview.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetService(curPage + 1);
            }
        });

        footView = View.inflate(E3_ServiceingDetailAty.this, R.layout.footview_loading, null);
    }

    @OnClick({R.id.titlebar_iv_left})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }


    public void GetServiceinfo() {
        RequestParams params = new RequestParams();
        params.put("ppid",ppid);
        params.put("branid", ttp);
        params.put("custid", cus);
        RequestUtils.ClientTokenPost(E3_ServiceingDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBSERCUST, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        TbsercustData tbsercustData = gson.fromJson(obj.getJSONObject("data").toString(),TbsercustData.class);
                        imageLoader.displayImage(tbsercustData.getPic(), photo);
                        name.setText(tbsercustData.getName());
                        phone.setText(tbsercustData.getPhone());
                        time.setText(tbsercustData.getTime());
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


    // ViewHolder静态类
    static class ViewHolder {
        public TextView cellname;
        public TextView cellgw;
        public TextView cellmls;
        public TextView cellsj;
    }


    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public ServiceingDetail getItem(int position) {
            return list.get(position);
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
                convertView = mInflater.inflate(R.layout.service_cell, null);
                holder.cellname = (TextView) convertView.findViewById(R.id.cell_name);
                holder.cellgw = (TextView) convertView.findViewById(R.id.cell_gw);
                holder.cellmls = (TextView) convertView.findViewById(R.id.cell_mls);
                holder.cellsj = (TextView) convertView.findViewById(R.id.cell_sj);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cellname.setText(list.get(position).getName());
            holder.cellgw.setText(list.get(position).getFirst());
            holder.cellmls.setText(list.get(position).getSecond());
            String start = list.get(position).getStart();
            String end = list.get(position).getEnd();

            if (start != null && end != null){
                holder.cellsj.setText(start+"-"+end);
            } else {
                holder.cellsj.setText("");
            }

//            holder.cellsj.setText(list.get(position).getStart()+"-"+list.get(position).getEnd());
            return convertView;
        }
    }

    public void GetService(final int page) {
        RequestParams params = new RequestParams();
        params.put("ppid",ppid);
        params.put("branid", ttp);
        params.put("custid", cus);
        params.put("begin", page + "");
        params.put("count", 20 + "");
        RequestUtils.ClientTokenPost(E3_ServiceingDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBSERITEMS, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            list.clear();
                        }
                        data = obj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            ServiceingDetail serviceingDetail = gson.fromJson(jsonObject.toString(), ServiceingDetail.class);
                            list.add(serviceingDetail);
                        }
                        curPage = list.size();
                        add2removeFooterView();
                        listview.onRefreshComplete();
                    } else {
                        listview.onRefreshComplete();
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                listview.onRefreshComplete();
                showToast("请求失败");
            }
        });
    }

    private void add2removeFooterView() {
        ListView lv = listview.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (list.size() % 20 == 0 && list.equals(0) && data.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }
}
