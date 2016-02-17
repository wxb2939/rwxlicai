package com.xem.mzbemployeeapp.activity;

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
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.SelectData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/1.
 */
public class E3_AddclientAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e2_storelist)
    PullToRefreshListView clientlist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private String branid;
    private MyAdapter adapter;
    private View footView;
    private int curPage = 0;
    private int resultCode = -100;
    private List<SelectData> data = new ArrayList<SelectData>();
    private SelectData selectData;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_addclient);
        new TitleBuilder(this).setTitleText("添加顾客137").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);

        branid = getIntent().getStringExtra("branid");
        GetAddclientList(0);
        adapter = new MyAdapter(E3_AddclientAty.this);
        clientlist.setAdapter(adapter);


        clientlist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetAddclientList(0);
            }
        });


        clientlist.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetAddclientList(curPage + 1);
            }
        });


        footView = View.inflate(E3_AddclientAty.this, R.layout.footview_loading, null);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void GetAddclientList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("begin", page + "");
        params1.put("count", 20 + "");
        RequestUtils.ClientTokenPost(E3_AddclientAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_SELECT, params1, new NetCallBack(this) {

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
                            SelectData selectData = gson.fromJson(object.toString(), SelectData.class);
                            data.add(selectData);
                        }
                        Utility.getInstance().hasDataLayout(E3_AddclientAty.this, data.size() != 0, ground, "暂无数据", R.mipmap.nodata_err);
                        curPage = data.size();
                        add2removeFooterView();
                        clientlist.onRefreshComplete();
                    } else {
                        clientlist.onRefreshComplete();
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


    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }

    static class ViewHolder {
        public ImageView addclientimg;
        public TextView addclientname;
        public TextView addclientphone;
        public TextView addclientbtn;
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
        public SelectData getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.e3_addclientaty_cell, null);
                holder.addclientimg = (ImageView) convertView.findViewById(R.id.addlient_img);
                holder.addclientname = (TextView) convertView.findViewById(R.id.addclient_name);
                holder.addclientphone = (TextView) convertView.findViewById(R.id.addclient_phone);
                holder.addclientbtn = (TextView) convertView.findViewById(R.id.addclient_btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.addclientimg);
            holder.addclientname.setText(data.get(position).getName());
            holder.addclientphone.setText(data.get(position).getTel());
            holder.addclientbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetFocus(data.get(position).getCustid().toString());
                    showToast("已成功加入137管理");
                    finish();
                }
            });
            return convertView;
        }
    }

    public void SetFocus(String str) {

        RequestParams params = new RequestParams();
        params.put("custid", str);
        params.put("branid", branid);
        params.put("empid", Config.getCachedBrandEmpid(this).toString());
        RequestUtils.ClientTokenPost(E3_AddclientAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_FOCUS, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                    } else {
                        showToast("设置失败");
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
        ListView lv = clientlist.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 10 == 0 && data.equals(0) && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }
}
