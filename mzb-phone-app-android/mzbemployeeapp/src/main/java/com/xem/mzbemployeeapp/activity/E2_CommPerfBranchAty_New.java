package com.xem.mzbemployeeapp.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.TimeSelectAdapter;
import com.xem.mzbemployeeapp.entity.PerfPpData_New;
import com.xem.mzbemployeeapp.fragment.E2_PerfBranchofDayFg;
import com.xem.mzbemployeeapp.fragment.E2_PerfBranchofMonthFg;
import com.xem.mzbemployeeapp.fragment.E2_PerfBranchofWeekFg;
import com.xem.mzbemployeeapp.fragment.E2_PerfBranchofYearFg;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.view.MyFrameLayout;
import com.xem.mzbemployeeapp.views.MyListView;
import com.xem.mzbemployeeapp.views.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by wellke on 2015/12/6.
 */
public class E2_CommPerfBranchAty_New extends MzbActivity{
    @InjectView(R.id.containerfg)
    MyFrameLayout containerfg;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.select)
    View select;
    @InjectView(R.id.bname)
    MyListView mListView;
    @InjectView(R.id.time)
    MyListView time;
    @InjectView(R.id.exit)
    TextView exit;
    @InjectView(R.id.confirm)
    TextView confirm;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String branid = "";
    private String ppid = "";
    private String dayStr;
    private String weelStr;
    private String monStr;
    private String yearStr;
    String date_str;
    private int flag;
    private int mFlag;
    Date date;
    ArrayList<String> time_list;
    ArrayList<String> data;
    ArrayList<PerfPpData_New> per_data;
    TimeSelectAdapter bname_adapter;
    TimeSelectAdapter time_adapter;
    public static String bname;

    int index=-1;
    int selection;
    boolean istimeSelect=false,isFirstSele=false;

    int data_index=-1;
    int data_selection;
    boolean isdataSelect=false,isdataFirstSele=false;
    //    String str;
    String right;
    public static boolean isVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty_new);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("员工业绩").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more);
        time_list=new ArrayList<String>();
        data=new ArrayList();
        per_data=new ArrayList();
        ppid = getIntent().getStringExtra("ppid");
        flag = getIntent().getIntExtra("flag", 1);
        branid = getIntent().getStringExtra("branid");
        right= getIntent().getStringExtra("right");
        if (branid != null) {
            getPerfPpofDayData(ppid, getIntent().getStringExtra("time"),Integer.parseInt(branid));
        }
    }

    //第一个界面获取门店信息的方法
    public void getPerfPpofDayData(final String ppid,String date_str,int brid) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "1");
        params1.put("ppid", ppid);
        params1.put("date", date_str);
        if (right.equals("one")){
            String str=null;
            params1.put("branid", str);
        }else if (right.equals("two")) {
            if (brid > 0){
                params1.put("branid",String.valueOf(branid));
            }else{
                String str=null;
                params1.put("branid",str);
            }
        }
        final MzbProgressDialog pd = new MzbProgressDialog(this, "请稍后....");
//        pd.show();
        RequestUtils.ClientTokenPost(this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PERFPP, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONArray jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PerfPpData_New perfPpData = gson.fromJson(object.toString(), PerfPpData_New.class);
                            data.add(perfPpData.name);
                            per_data.add(perfPpData);
                        }
                        initView();
                        pd.dismiss();
                    } else {
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

    private void initView() {
        time_list.add("按日查询");
        time_list.add("按周查询");
        time_list.add("按月查询");
        time_list.add("按年查询");
        bname_adapter=new TimeSelectAdapter(data,this);
        time_adapter=new TimeSelectAdapter(time_list,this);
        mListView.setAdapter(bname_adapter);
        time.setAdapter(time_adapter);
        new TitleBuilder(this).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.getVisibility() == View.VISIBLE) {
                    isVisible=false;
                    containerfg.setAlpha(1.0f);
                    select.setVisibility(View.GONE);
                    return;
                }
                if (isFirstSele) {
                    time_adapter.setSelectedPosition(selection);
                } else {
                    time_adapter.setSelectedPosition(0);
                }
                time_adapter.notifyDataSetInvalidated();
                if (isdataFirstSele) {
                    bname_adapter.setSelectedPosition(data_selection);
                } else {
                    bname_adapter.setSelectedPosition(data.indexOf(bname));
                }
                bname_adapter.notifyDataSetInvalidated();
                isVisible=true;
                containerfg.setAlpha(0.6f);
                select.setVisibility(View.VISIBLE);
            }
        });
        //获取基本数据
        if (Integer.parseInt(branid) < 1){
            branid= Config.getCachedFirstBrandId(this);
        }
        date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayStr = sdf.format(date);
        weelStr = sdf.format(date);
        monStr = sdf.format(date);
        yearStr = sdf.format(date);
        date_str=sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        init(flag);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data_index == position){
                    if (isdataSelect){
                        isdataSelect=false;
                    }else{
                        isdataSelect=true;
                    }
                    bname_adapter.setSelectedPosition(isdataSelect ? position : -1);
                }else{
                    bname_adapter.setSelectedPosition(position);
                }
                data_selection=bname_adapter.getSelectedPosition();
                bname_adapter.notifyDataSetInvalidated();
                data_index=position;
                isdataFirstSele=true;
                PerfPpData_New ppd=per_data.get(position);
                branid=String.valueOf(ppd.branid);
                bname=per_data.get(position).name;
            }
        });

        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index == position){
                    if (istimeSelect){
                        istimeSelect=false;
                    }else{
                        istimeSelect=true;
                    }
                    time_adapter.setSelectedPosition(istimeSelect ? position:-1);
                }else{
                    time_adapter.setSelectedPosition(position);
                }
                selection=time_adapter.getSelectedPosition();
                time_adapter.notifyDataSetInvalidated();
                flag = position + 1;
                index=position;
                isFirstSele=true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerfg.setAlpha(1.0f);
        E2_CommPerfBranchAty_New.isVisible=false;
    }

    @OnClick({R.id.titlebar_iv_left,R.id.exit,R.id.confirm})
    public void click(View v){
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            //取消
            case R.id.exit:{
                time_adapter.setSelectedPosition(-1);
                time_adapter.notifyDataSetInvalidated();
                bname_adapter.setSelectedPosition(-1);
                bname_adapter.notifyDataSetInvalidated();
                isVisible=false;
                containerfg.setAlpha(1.0f);
                select.setVisibility(View.GONE);
                break;
            }
            //确定
            case R.id.confirm:{
                if (bname_adapter.getSelectedPosition() == (-1)){
                    showToast("请选择门店");
                    return;
                }if (time_adapter.getSelectedPosition() == (-1)){
                    showToast("请选择时间");
                    return;
                }
                isVisible=false;
                containerfg.setAlpha(1.0f);
                time_adapter.setSelectedPosition(-1);
                time_adapter.notifyDataSetInvalidated();
                bname_adapter.setSelectedPosition(-1);
                bname_adapter.notifyDataSetInvalidated();
                select.setVisibility(View.GONE);
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                init(flag);
                break;
            }
        }
    }

    private void init(int flag){
        switch (flag){
//            按日查询
            case 1:
                if (E2_PerfBranchofDayFg.dayStr != null){
                    dayStr=E2_PerfBranchofDayFg.dayStr;
                }
                E2_PerfBranchofDayFg e2_PerfBranchofDayFg = new E2_PerfBranchofDayFg();

                Bundle args1 = new Bundle();
                args1.putString("branid",branid);
                args1.putString("ppid",ppid);
                args1.putString("time",dayStr);
                e2_PerfBranchofDayFg.setArguments(args1);
                ft.replace(R.id.containerfg, e2_PerfBranchofDayFg);
                ft.commit();
                break;
//            按周查询
            case 2:
                if (E2_PerfBranchofWeekFg.dayStr != null){
                    weelStr=E2_PerfBranchofWeekFg.dayStr;
                }
                E2_PerfBranchofWeekFg e2_PerfBranchofWeekFg = new E2_PerfBranchofWeekFg();
                Bundle args2 = new Bundle();
                args2.putString("branid",branid);
                args2.putString("ppid",ppid);
                args2.putString("time",weelStr);
                e2_PerfBranchofWeekFg.setArguments(args2);
                ft.replace(R.id.containerfg, e2_PerfBranchofWeekFg);
                ft.commit();
                break;
//            按月查询
            case 3:
                if (E2_PerfBranchofMonthFg.dayStr != null){
                    monStr=E2_PerfBranchofMonthFg.dayStr;
                }
                E2_PerfBranchofMonthFg e2_PerfBranchofMonthFg = new E2_PerfBranchofMonthFg();
                Bundle args3 = new Bundle();
                args3.putString("branid",branid);
                args3.putString("ppid",ppid);
                args3.putString("time",monStr);
                e2_PerfBranchofMonthFg.setArguments(args3);
                ft.replace(R.id.containerfg, e2_PerfBranchofMonthFg);
                ft.commit();
                break;
//            按年查询
            case 4:
                if (E2_PerfBranchofYearFg.dayStr != null){
                    yearStr=E2_PerfBranchofYearFg.dayStr;
                }
                E2_PerfBranchofYearFg e2_PerfBranchofYearFg = new E2_PerfBranchofYearFg();
                Bundle args4 = new Bundle();
                args4.putString("branid",branid);
                args4.putString("ppid",ppid);
                args4.putString("time",yearStr);
                e2_PerfBranchofYearFg.setArguments(args4);
                ft.replace(R.id.containerfg, e2_PerfBranchofYearFg);
                ft.commit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        select.setVisibility(View.GONE);
        containerfg.setAlpha(1.0f);
        isVisible=false;
    }


    @Override
    protected void onStop() {
        super.onStop();
        select.setVisibility(View.GONE);
        containerfg.setAlpha(1.0f);
        isVisible=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        select.setVisibility(View.GONE);
        containerfg.setAlpha(1.0f);
        isVisible=false;
    }
}

