package com.xem.mzbcustomerapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.EmpqueryData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/3.
 */
public class B2_SelectMlsActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e2_storelist)
    PullToRefreshListView orderlist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private int num = -1;
    private List<EmpqueryData> data = new ArrayList<EmpqueryData>();
    private int curPage = 0;
    private View footView;
    private MyAdapter adapter;
    private int resultCode = -101;


    @Override
    protected void initView() {
        setContentView(R.layout.b2_selectmls_activity);
        new TitleBuilder(this).setTitleText("选择美疗师").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        num = getIntent().getIntExtra("num",-1);

        getEmployeeList(0);
        adapter = new MyAdapter(B2_SelectMlsActivity.this);
        orderlist.setAdapter(adapter);

        orderlist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getEmployeeList(0);
            }
        });

        orderlist.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                getEmployeeList(curPage + 1);
            }
        });

        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("info", data.get(position - 1).getName());
                intent.putExtra("waitid", data.get(position - 1).getEmpid());
                intent.putExtra("num", position - 1);
                setResult(resultCode, intent);
                finish();

            }
        });
        footView = View.inflate(B2_SelectMlsActivity.this, R.layout.footview_loading, null);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            default:
                break;
        }
    }

    public void getEmployeeList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", Config.getCachedBrandid(B2_SelectMlsActivity.this));
        params1.put("codes", "115");
        params1.put("begin", page + "");
        params1.put("count", 10 + "");
        MzbHttpClient.ClientTokenPost(B2_SelectMlsActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.em_query, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {

                        if (page == 0) {
                            data.clear();
                        }
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            EmpqueryData empqueryData = gson.fromJson(object.toString(), EmpqueryData.class);
                            data.add(empqueryData);
                        }
                        curPage = data.size();
                        add2removeFooterView();
                        orderlist.onRefreshComplete();
                    } else {
                        orderlist.onRefreshComplete();
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



    static class ViewHolder {
        public ImageView mlspic;
        public TextView mlsname;
        public ImageView mslgood;
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
        public EmpqueryData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.b2_selectmls_aty_cell, null);
                holder.mlspic = (ImageView) convertView.findViewById(R.id.mls_pic);
                holder.mlsname = (TextView) convertView.findViewById(R.id.mls_name);
                holder.mslgood = (ImageView) convertView.findViewById(R.id.mls_good);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.mlspic);
            holder.mlsname.setText(data.get(position).getName());
            if (num == position){
                holder.mslgood.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

    private void add2removeFooterView() {
        ListView lv = orderlist.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 10 == 0 && data.equals(0)) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }
}