package com.xem.mzbphoneapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.E3_ServiceingDetailAty;
import com.xem.mzbphoneapp.atys.E3_ServicetodoDetailAty;
import com.xem.mzbphoneapp.atys.MzbApplication;
import com.xem.mzbphoneapp.entity.EmployeeSatisfy;
import com.xem.mzbphoneapp.entity.Tbserving;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 15/7/29.
 */
public class E2_ServiceDoingFg extends Fragment {

    @InjectView(R.id.service_list)
    PullToRefreshListView listView;
    @InjectView(R.id.service_ground)
    RelativeLayout ground;

    private List<Tbserving> data;
    private EmployeeSatisfy employeeSatisfy;
    private MyAdapter adapter;
    private int curPage = 0;
    private MzbApplication application;
    private ImageLoader imageLoader;
    private View footView;
    private String branid;
    private String ppid;
    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.e2_service_doing_fg, null);
        ButterKnife.inject(this, view);
        if (getArguments() != null) {
            branid = getArguments().getString("branid");
            ppid = getArguments().getString("ppid");
        }
        imageLoader = ImageLoader.getInstance();
        application = (MzbApplication) getActivity().getApplication();
        data = new ArrayList<Tbserving>();
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        GetServeringList(0);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetServeringList(0);
            }
        });


        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetServeringList(curPage + 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), E3_ServiceingDetailAty.class);
                intent.putExtra("custid", data.get(position - 1).getCustid());
                intent.putExtra("branid", branid);
                intent.putExtra("ppid", ppid);
                startActivity(intent);
            }
        });

        footView = View.inflate(getActivity(), R.layout.footview_loading, null);

        return view;
    }


    public void GetServeringList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("ppid", ppid);
        params1.put("branid", branid);

        params1.put("begin", page + "");
        params1.put("count", 20 + "");


        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBSERVING, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            data.clear();
                        }

                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Tbserving tbserving = gson.fromJson(object.toString(), Tbserving.class);
                            data.add(tbserving);
                        }
                        curPage = data.size();
                        Utility.getInstance().hasDataLayout(getActivity(), data.size() != 0, ground, "暂无数据", R.mipmap.nodata_err);
                        add2removeFooterView();
                        listView.onRefreshComplete();
                        pd.dismiss();
                    } else {
                        listView.onRefreshComplete();
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
            }
        });
    }

    private void add2removeFooterView() {

        ListView lv = listView.getRefreshableView();
        adapter.notifyDataSetChanged();

        if (data.size() % 20 == 0 && data.size() != 0 && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }


    // ViewHolder静态类
    static class ViewHolder {
        public ImageView pic;
        public TextView name;
        public TextView time;  //美疗师
        public TextView comm;
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
        public Tbserving getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_service_fg_cell, null);
                holder.pic = (ImageView) convertView.findViewById(R.id.service_pic);
                holder.name = (TextView) convertView.findViewById(R.id.service_name);
                holder.time = (TextView) convertView.findViewById(R.id.service_time);
                holder.comm = (TextView) convertView.findViewById(R.id.all_comm);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.pic);
            holder.name.setText(data.get(position).getName());
            holder.time.setText(data.get(position).getTime());
            holder.comm.setText("到店时间：");
            return convertView;
        }
    }
}
