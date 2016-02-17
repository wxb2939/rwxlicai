package com.xem.mzbemployeeapp.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.EmployeeSatisfy;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.Utility;
import com.xem.mzbemployeeapp.views.MzbProgressDialog;

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
 * Created by xuebing on 15/7/27.
 */
public class E2_SatisofDayFg extends Fragment {

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
    private Date date;
    private List<EmployeeSatisfy> data;
    private MyAdapter adapter;
    private String branid = "";
    private int curPage;
    private View footView;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.e2_satis_of_year_fg, null);
        ButterKnife.inject(this, view);
        if (getArguments() != null) {
            branid = getArguments().getString("branid");
        }
        date = new Date();
        data = new ArrayList<EmployeeSatisfy>();

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
        footView = View.inflate(getActivity(), R.layout.footview_loading, null);
        return view;
    }


    @OnClick({R.id.select_right, R.id.select_left})
    public void click(View v) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()) {
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

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayStr = sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        selectTitle.setText(sdf1.format(date));
        GetServeRecordList(num);
    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "1");
        params1.put("branid", branid);
        params1.put("date", dayStr.toString());
        params1.put("begin", page + "");
        params1.put("count", 20 + "");


        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_EMPLOYEE, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            data.clear();
                        }

                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            EmployeeSatisfy employeeSatisfy = gson.fromJson(object.toString(), EmployeeSatisfy.class);
                            data.add(employeeSatisfy);
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

        if (data.size() % 10 == 0 && data.size() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }


    // ViewHolder静态类
    static class ViewHolder {
        public TextView name;
        public TextView position;  //美疗师
        public TextView score;
        public TextView tscore;

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
        public EmployeeSatisfy getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.e2_satisofday_fg_cell, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.position = (TextView) convertView.findViewById(R.id.tv_position);
                holder.score = (TextView) convertView.findViewById(R.id.tv_score);
                holder.tscore = (TextView) convertView.findViewById(R.id.tv_tscore);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(data.get(position).getName());
            holder.position.setText(data.get(position).getPosition());
            holder.score.setText(data.get(position).getScore());
            holder.tscore.setText(data.get(position).getTscore());
            return convertView;
        }
    }
}
