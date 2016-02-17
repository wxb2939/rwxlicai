package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.RecordsData;
import com.xem.mzbcustomerapp.me.maxwin.view.XListView;
import com.xem.mzbcustomerapp.me.maxwin.view.XListView.IXListViewListener;
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
 * Created by xuebing on 15/10/30.
 */
public class B1_AllBeautyFileActivity extends BaseActivity implements IXListViewListener{

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
//    @InjectView(R.id.list)
//    PullToRefreshListView list;
    @InjectView(R.id.xlistview)
    XListView list;
    private List<RecordsData> data;
    private RecordsData recordsData;
    private MyAdapter adapter;
    private JSONArray jsonArray;
    private View footView;
    private int curPage;
    private int resultCode = -102;

    private void onLoad() {
        list.stopRefresh();
        list.stopLoadMore();
        list.setRefreshTime("刚刚");
    }

    @Override
    protected void initView() {
//        setContentView(R.layout.b1_allbeautyfile_activity);
        setContentView(R.layout.my_all_beauty);
        new TitleBuilder(this).setTitleText("所有档案").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        data = new ArrayList<>();
        adapter = new MyAdapter(B1_AllBeautyFileActivity.this);
//        footView = View.inflate(B1_AllBeautyFileActivity.this, R.layout.footview_loading, null);
        list.setAdapter(adapter);
        getRecords(curPage);
        list.setPullLoadEnable(true);
        list.setPullRefreshEnable(true);
        list.setXListViewListener(this);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        curPage=0;
        data.clear();
        getRecords(curPage);
        onLoad();

    }

    //加载更多
    @Override
    public void onLoadMore() {
        ++curPage;
        getRecords(curPage);
        onLoad();
    }

    @Override
    protected void initData() {
        list.setOnItemClickListener(new OnItemClickListenerImpl());
    }


    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }


    private void getRecords(int page) {
        Log.v("tag",page+"");
        RequestParams params = new RequestParams();
        params.put("uid", Config.getCachedUid(B1_AllBeautyFileActivity.this));
        params.put("begin", page+"");
        params.put("count", "20");

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.records, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                boolean flag=false;
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        //判断是否有数据
                        //有数据
                        if (jsonArray.length() == 0){
                            showToast("已没有更多数据");
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            RecordsData recordsData = gson.fromJson(object.toString(), RecordsData.class);
                            if (!data.contains(recordsData)){
                                data.add(recordsData);
                                flag=true;
                            }
                            if (! flag){
                                showToast("已没有更多数据");
                            }
//                            add2removeFooterView();
                        }
//                        list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast(obj.getString("message"));
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


    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView address;
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
        public RecordsData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.include_circle_imgtext, null);
                holder.img = (ImageView) convertView.findViewById(R.id.circle_img);
                holder.name = (TextView) convertView.findViewById(R.id.cirlce_title);
                holder.address = (TextView) convertView.findViewById(R.id.circle_detail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getLogo(), holder.img);
            holder.name.setText(data.get(position).getName());
            holder.address.setText("诊断日期：" + data.get(position).getTime());
            return convertView;
        }
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        public OnItemClickListenerImpl() {

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            recordsData = data.get(position-1);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("recordsData", recordsData);
            intent.putExtras(bundle);
            setResult(resultCode, intent);
            finish();
        }
    }


//    private void add2removeFooterView() {
//
//        ListView lv = list.getRefreshableView();
//        adapter.notifyDataSetChanged();
//
//        if (data.size() % 20 == 0 && data.size() != 0 && jsonArray.length() != 0) {
//            lv.addFooterView(footView);
//        } else {
//            lv.removeFooterView(footView);
//        }
//    }
}
