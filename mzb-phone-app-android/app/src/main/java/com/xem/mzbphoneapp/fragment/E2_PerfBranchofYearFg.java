package com.xem.mzbphoneapp.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.xem.mzbphoneapp.atys.E3_CommPerfEmployeeAty;
import com.xem.mzbphoneapp.entity.PerfBranchData;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

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
 * Created by xuebing on 15/9/22.
 */
public class E2_PerfBranchofYearFg extends Fragment {

    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_right)
    ImageView selectRight;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.e2_list)
    PullToRefreshListView listView;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private String dayStr;
    protected ImageLoader imageLoader;
    private Date date;
    private List<PerfBranchData> data;
    private MyAdapter adapter;
    private String ppid = "";
    private String branid = "";
    private int curPage;
    private View footView;
    private View view;
    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.e2_perf_branchc_comm, null);
        ButterKnife.inject(this, view);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            branid = getArguments().getString("branid");
            ppid = getArguments().getString("ppid");
        }
        date = new Date();
        data = new ArrayList<PerfBranchData>();

        refreshDate(0);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshDate(0);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                refreshDate(curPage + 1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), E3_CommPerfEmployeeAty.class);
                intent.putExtra("empid", data.get(position - 1).getEmpid().toString());
                startActivity(intent);
            }
        });

        footView = View.inflate(getActivity(), R.layout.footview_loading, null);

        return view;
    }


    @OnClick({R.id.select_right, R.id.select_left})
    public void click(View v) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()) {
            case R.id.select_left:
                c.add(c.YEAR, -1);
                date = c.getTime();
                refreshDate(0);
                break;
            case R.id.select_right:
                c.add(c.YEAR, 1);
                date = c.getTime();
                refreshDate(0);
                break;
            default:
                break;
        }
    }

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayStr = sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年");
        selectTitle.setText(sdf1.format(date));
        getPerfPpofDayData(num);
    }

    public void getPerfPpofDayData(final Integer page) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "4");
        params1.put("branid", branid);
        params1.put("ppid", ppid);
        params1.put("date", dayStr.toString());
        params1.put("begin", page.toString());
        params1.put("count", 20 + "");


        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PERFBRANCH, params1, new NetCallBack() {

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
                            PerfBranchData perfBranchData = gson.fromJson(object.toString(), PerfBranchData.class);
                            data.add(perfBranchData);
                        }
                        curPage = data.size();
                        view = Utility.getInstance().getDataLayout(getActivity(), curPage != 0, ground, "暂无数据", R.mipmap.nodata_err, view);
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

        if (data.size() % 10 == 0 && data.size() != 0 && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }

    static class ViewHolder {
        public TextView pb_position;
        public TextView pb_name;
        public TextView pb_crash;
        public TextView pb_work;

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
        public PerfBranchData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_perf_branch_fg_cell, null);
                holder.pb_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.pb_position = (TextView) convertView.findViewById(R.id.tv_position);
                holder.pb_crash = (TextView) convertView.findViewById(R.id.tv_crash);
                holder.pb_work = (TextView) convertView.findViewById(R.id.tv_work);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.pb_name.setText(data.get(position).getName());
            holder.pb_position.setText(data.get(position).getPosition());
            holder.pb_work.setText(data.get(position).getWork().toString());
            holder.pb_crash.setText(data.get(position).getCash().toString());
            return convertView;
        }
    }

}