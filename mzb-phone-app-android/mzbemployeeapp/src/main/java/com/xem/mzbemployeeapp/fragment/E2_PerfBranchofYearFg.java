package com.xem.mzbemployeeapp.fragment;

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
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.activity.E2_CommPerfBranchAty_New;
import com.xem.mzbemployeeapp.activity.E3_CommPerfEmployeeAty_New;
import com.xem.mzbemployeeapp.entity.BranchAndEmploy;
import com.xem.mzbemployeeapp.entity.PerfBranchData;
import com.xem.mzbemployeeapp.entity.PerfPpData_New;
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
    View convertView;

    public static String dayStr;
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
    boolean over = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.e2_perf_branchc_comm, null);
        ButterKnife.inject(this, view);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            branid = getArguments().getString("branid");
            ppid = getArguments().getString("ppid");
            dayStr = getArguments().getString("time");
        }

        if (dayStr == null) {
            date = new Date();
        } else {
            date = Utility.getInstance().toDate(dayStr);
        }
//        date = new Date();
        data = new ArrayList<PerfBranchData>();

        convertView = LayoutInflater.from(getActivity()).inflate(R.layout.head_view, null);
        ListView list_view = listView.getRefreshableView();
        list_view.addHeaderView(convertView);

        refreshDate(0);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
//        getPerfPpofDayData();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                over = false;
                refreshDate(0);
            }
        });

        listView.setOnLastItemVisibleListener(
                new PullToRefreshBase.OnLastItemVisibleListener() {
                    @Override
                    public void onLastItemVisible() {
                        if (over) {
                            return;
                        }
                        refreshDate(curPage + 1);
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    return;
                }
                Intent intent = new Intent(getActivity(), E3_CommPerfEmployeeAty_New.class);
                intent.putExtra("empid", data.get(position - 2).getEmpid().toString());
                intent.putExtra("flag", 4);
                intent.putExtra("time", dayStr);
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

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_BRANCHFULL, params1, new NetCallBack() {

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

                        BranchAndEmploy perfBranchData = gson.fromJson(obj.getString("data"), BranchAndEmploy.class);
                        perfPpData = perfBranchData.branch;

                        if (perfPpData != null) {
                            imageLoader.displayImage(perfPpData.logo, ((ImageView) convertView.findViewById(R.id.b0_order_img)));
                            ((TextView) convertView.findViewById(R.id.b0_order_name)).setText(perfPpData.name);
                            ((TextView) convertView.findViewById(R.id.b0_order_phone)).setText(perfPpData.work);
                            ((TextView) convertView.findViewById(R.id.b0_order_adrress)).setText(perfPpData.cash);
                            E2_CommPerfBranchAty_New.bname = perfPpData.name;
                        }

                        jsonArray = new JSONArray(gson.toJson(perfBranchData.employee));
                        for (PerfBranchData per : perfBranchData.employee) {
                            data.add(per);
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

        if (data.size() % 10 == 0 && data.size() != 0 && jsonArray.length() != 0 && over) {
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.employ_yj_item, null);
            holder.pb_name = (TextView) convertView.findViewById(R.id.client_name);
            holder.pb_position = (TextView) convertView.findViewById(R.id.work_name);
            holder.pb_crash = (TextView) convertView.findViewById(R.id.client_num);
            holder.pb_work = (TextView) convertView.findViewById(R.id.client_phone);
            holder.pic = (ImageView) convertView.findViewById(R.id.client_img);
            if (data.get(position) == null || data.get(position).equals("")) {
                holder.pb_position.setText("");
            } else {
                holder.pb_position.setText("(" + data.get(position).getPosition() + ")");
            }
            imageLoader.displayImage(data.get(position).pic, holder.pic);

            holder.pb_name.setText(data.get(position).getName());
            holder.pb_work.setText(data.get(position).getWork().toString());
            holder.pb_crash.setText(data.get(position).getCash().toString());
            return convertView;
        }
    }

    PerfPpData_New perfPpData;

}