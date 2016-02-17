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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.activity.E2_StoreFatisfyAty_New;
import com.xem.mzbemployeeapp.entity.EmployeeSatisfy;
import com.xem.mzbemployeeapp.entity.SatisAndBranch;
import com.xem.mzbemployeeapp.entity.StoreFatisfy;
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
public class E2_SaitsofYearFg extends Fragment {

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

    boolean over=false;
    private String dayStr;
    private Date date;
    private List<EmployeeSatisfy> data;
    private MyAdapter adapter;
    private String branid;
    private String ppid;
    private int curPage;
    private View footView;
    private View view;
    private JSONArray jsonArray;
    View convertView;

    StoreFatisfy perfPpData;
    protected ImageLoader imageLoader;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.e2_satis_of_year_fg, null);
        ButterKnife.inject(this, view);
        date = new Date();
        data = new ArrayList<EmployeeSatisfy>();
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            branid = getArguments().getString("branid");
            ppid = getArguments().getString("ppid");
        }
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout.e2_storefatisfy_aty_cell, null);
        ListView list_view=listView.getRefreshableView();
        list_view.addHeaderView(convertView);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
        refreshDate(0);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                over=false;
                refreshDate(0);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (over){
                    return;
                }
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
                c.add(c.YEAR, -1);
                date = c.getTime();
                if (date.getTime() <= System.currentTimeMillis()){
                    selectRight.setBackgroundResource(R.mipmap.left_new);
                    selectRight.setClickable(true);
                }
                over=false;
                refreshDate(0);
                break;
            case R.id.select_right:
                c.add(c.YEAR, 1);
                date = c.getTime();
                if (date.getTime() > System.currentTimeMillis()){
                    selectRight.setBackgroundResource(R.mipmap.right_none);
                    selectRight.setClickable(false);
                    c.add(c.YEAR, -1);
                    date = c.getTime();
                    break;
                }
                over=false;
                refreshDate(0);
                break;
            default:
                break;
        }
    }

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        dayStr = sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年");
        selectTitle.setText(sdf1.format(date));
        GetServeRecordList(num);
    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "4");
        params1.put("branid", branid);
        params1.put("ppid", ppid);
        params1.put("date", dayStr.toString());
        params1.put("begin", page + "");
        params1.put("count", 20 + "");

        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_EMPLOYEE_FULL, params1, new NetCallBack() {

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
                        SatisAndBranch perfBranchData = gson.fromJson(obj.getString("data"), SatisAndBranch.class);
                        perfPpData = perfBranchData.branch;
                        jsonArray = new JSONArray(gson.toJson(perfBranchData.employee));
                        for (EmployeeSatisfy per : perfBranchData.employee) {
                            data.add(per);
                        }
                        if (perfPpData != null) {
                            imageLoader.displayImage(perfPpData.getLogo(), ((ImageView) convertView.findViewById(R.id.e2_logo)));
                            ((TextView) convertView.findViewById(R.id.e2_store_name)).setText(perfPpData.getName());
                            ((TextView) convertView.findViewById(R.id.e2_store_consultant)).setText(perfPpData.getConsultant());
                            ((TextView) convertView.findViewById(R.id.e2_store_beautician)).setText(perfPpData.getBeautician());
                            ((TextView) convertView.findViewById(R.id.e2_store_service)).setText(perfPpData.getService());
                            ((TextView) convertView.findViewById(R.id.e2_store_result)).setText(perfPpData.getResult());
                            E2_StoreFatisfyAty_New.bname=perfPpData.getName();
                        }
                        curPage = data.size();
                        view = Utility.getInstance().getDataLayout(getActivity(), curPage != 0, ground, "暂无数据", R.mipmap.nodata_err, view);

                        add2removeFooterView();
                        listView.onRefreshComplete();

                        pd.dismiss();
                        if (jsonArray.length() == 0){
                            over=true;
                        }
                    } else {
                        listView.onRefreshComplete();
                        over=false;
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    over=false;
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                over=false;
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
        public TextView pb_position;
        public TextView pb_name;
        public TextView pb_crash;
        public TextView pb_work;
        public ImageView pic;
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
            // Get the row id associated with the specified position in the
            // list.
            // 获取在列表中与指定索引对应的行id
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.employ_myd_item, null);
                holder.pic = (ImageView) convertView.findViewById(R.id.client_img);
                holder.pb_name = (TextView) convertView.findViewById(R.id.client_name);
                holder.pb_position = (TextView) convertView.findViewById(R.id.work_name);
                holder.pb_crash = (TextView) convertView.findViewById(R.id.client_num);
                holder.pb_work = (TextView) convertView.findViewById(R.id.client_phone);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.pb_name.setText(data.get(position).getName());
            if (data.get(position) == null || data.get(position).equals("")) {
                holder.pb_position.setText("");
            } else {
                holder.pb_position.setText("(" + data.get(position).getPosition() + ")");
            }
            imageLoader.displayImage(data.get(position).pic, holder.pic);
            holder.pb_work.setText(data.get(position).getScore().toString());
            holder.pb_crash.setText(data.get(position).getTscore().toString());
            return convertView;
        }
    }
}
