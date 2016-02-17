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
import com.xem.mzbemployeeapp.entity.StoreFatisfy;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;



/**
 * Created by xuebing on 15/8/3.
 */
public class E2_StoreFatisfyAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e2_storelist)
    PullToRefreshListView storelist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private List<StoreFatisfy> data = new ArrayList<StoreFatisfy>();
    private MyAdapter adapter;
    private View footView;
    private int curPage = 0;
    private String ppid = "";
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_storefatisfy_aty);
        new TitleBuilder(this).setTitleText("满意度得分（门店）").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        ppid = getIntent().getStringExtra("ppid");
        GetStoreFatisfyList(0);
        adapter = new MyAdapter(E2_StoreFatisfyAty.this);
        storelist.setAdapter(adapter);


        storelist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetStoreFatisfyList(0);
            }
        });


        storelist.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetStoreFatisfyList(curPage + 1);
            }
        });

        storelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(E2_StoreFatisfyAty.this, E1_CommSelectAty.class);
                Bundle bundle = new Bundle();
                bundle.putString("branid", data.get(position - 1).getBranid());
                bundle.putString("ppid",ppid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        footView = View.inflate(E2_StoreFatisfyAty.this, R.layout.footview_loading, null);

    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void GetStoreFatisfyList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("ppid", ppid);
//        params1.put("begin", page + "");
//        params1.put("count", 10 + "");
        RequestUtils.ClientTokenPost(E2_StoreFatisfyAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_SBRANCH, params1, new NetCallBack(this) {

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
                            StoreFatisfy storeFatisfy = gson.fromJson(object.toString(), StoreFatisfy.class);
                            data.add(storeFatisfy);
                        }

                        if (data.size() == 0) {
                            View grounds = View.inflate(E2_StoreFatisfyAty.this, R.layout.ground_comm_back, null);
                            ground.addView(grounds);
                        }

                        curPage = data.size();
                        add2removeFooterView();
                        storelist.onRefreshComplete();
                    } else {
                        storelist.onRefreshComplete();
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
        ListView lv = storelist.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 10 == 0 && data.equals(0) && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }

    static class ViewHolder {
        public ImageView logo;
        public TextView name;
        public TextView consultant;
        public TextView beautician;
        public TextView service;
        public TextView result;
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
        public StoreFatisfy getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_storefatisfy_aty_cell, null);
                holder.logo = (ImageView) convertView.findViewById(R.id.e2_logo);
                holder.name = (TextView) convertView.findViewById(R.id.e2_store_name);
                holder.consultant = (TextView) convertView.findViewById(R.id.e2_store_consultant);
                holder.beautician = (TextView) convertView.findViewById(R.id.e2_store_beautician);
                holder.service = (TextView) convertView.findViewById(R.id.e2_store_service);
                holder.result = (TextView) convertView.findViewById(R.id.e2_store_result);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getLogo(), holder.logo);
            holder.name.setText(data.get(position).getName());
            holder.consultant.setText(data.get(position).getConsultant());
            holder.beautician.setText(data.get(position).getBeautician());
            holder.service.setText(data.get(position).getService());
            holder.result.setText(data.get(position).getResult());
            return convertView;
        }
    }
}
