package com.xem.mzbphoneapp.atys;

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
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.ttpService;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;

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
public class E2_BrandServicetppAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.serverlist)
    PullToRefreshListView listView;


    private List<ttpService> data;
    private MyAdapter adapter;
    private int curPage = 0;
    private View footView;
    private String ppid = "";
    private JSONArray jsonArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_brandservicetpp_aty);
        new TitleBuilder(this).setTitleText("门店当日服务查询(门店)").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        ppid = getIntent().getStringExtra("ppid");

        data = new ArrayList<ttpService>();
        adapter = new MyAdapter(E2_BrandServicetppAty.this);
        listView.setAdapter(adapter);

        GetServerList(0);
        data = new ArrayList<ttpService>();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetServerList(0);
            }
        });
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetServerList(0);
            }
        });
        footView = View.inflate(E2_BrandServicetppAty.this,R.layout.footview_loading,null);
    }


    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }


    public void GetServerList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("ppid", ppid);
        params1.put("begin",page+"");
        params1.put("count", "10");

        RequestUtils.ClientTokenPost(E2_BrandServicetppAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TPP, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        if (page == 0){
                            data.clear();
                        }
                        jsonArray = new JSONArray(obj.getString("data"));
                        ttpService ttp = new ttpService();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            ttp = gson.fromJson(object.toString(), ttpService.class);
                            data.add(ttp);
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(E2_BrandServicetppAty.this,E2_TodayServiceCheckAty.class);
                                Bundle bundle = new Bundle();
                                intent.putExtra("branid", data.get(position-1).getBranid());
                                intent.putExtra("ppid",ppid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        curPage = data.size();
                        add2removeFooterView();
                        listView.onRefreshComplete();
                    } else {
                        listView.onRefreshComplete();
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


    private void add2removeFooterView(){

        ListView lv = listView.getRefreshableView();
        adapter.notifyDataSetChanged();

        if (data.size()%10 == 0 && data.equals(0) && jsonArray.length() != 0){

            lv.addFooterView(footView);
        }else {
            lv.removeFooterView(footView);
        }
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView storeName;
        public TextView serviceDoing;
        public TextView servideTodo;
        public TextView servideDone;
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
        public ttpService getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_brandservicetpp_aty_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.e2_brandsericer_img);
                holder.storeName = (TextView) convertView.findViewById(R.id.e2_server_name);
                holder.serviceDoing = (TextView) convertView.findViewById(R.id.e2_server_doing);
                holder.servideTodo = (TextView) convertView.findViewById(R.id.e2_server_done);
                holder.servideDone = (TextView) convertView.findViewById(R.id.e2_server_todo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getLogo(), holder.img);
            holder.storeName.setText(data.get(position).getName());
            holder.serviceDoing.setText(data.get(position).getService());
            holder.servideTodo.setText(data.get(position).getLeave());
            holder.servideDone.setText(data.get(position).getAppo());
            return convertView;
        }
    }
}
