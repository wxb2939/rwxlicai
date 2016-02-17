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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.TimeSelectAdapter;
import com.xem.mzbemployeeapp.entity.PerfPpData_New;
import com.xem.mzbemployeeapp.fragment.E2_SaitsofYearFg;
import com.xem.mzbemployeeapp.fragment.E2_SatisofMonthFg;
import com.xem.mzbemployeeapp.fragment.E2_SatisofWeekFg;
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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by wellke on 2015/12/10.
 */
public class E2_StoreFatisfyAty_New extends MzbActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;

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

    private String branid = "";
    private String ppid = "";
    private int flag = 1;
    int index = -1;
    int selection;
    boolean istimeSelect = false, isFirstSele = false;

    int data_index = -1;
    int data_selection;
    boolean isdataSelect = false, isdataFirstSele = false;

    ArrayList<String> time_list;
    ArrayList<String> data;
    ArrayList<PerfPpData_New> per_data;
    TimeSelectAdapter bname_adapter;
    TimeSelectAdapter time_adapter;
    protected ImageLoader imageLoader;

    public static String bname;
    String right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty_new);
        ButterKnife.inject(this);
        E2_CommPerfBranchAty_New.isVisible=false;
        new TitleBuilder(this).setTitleText("满意度得分(员工)").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more);
        time_list=new ArrayList<String>();
        data=new ArrayList();
        per_data=new ArrayList();
        ppid = getIntent().getStringExtra("ppid");
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
//       pd.show();
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
        imageLoader = ImageLoader.getInstance();
        new TitleBuilder(this).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.getVisibility() == View.VISIBLE) {
                    select.setVisibility(View.GONE);
                    containerfg.setAlpha(1.0f);
                    E2_CommPerfBranchAty_New.isVisible=false;
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
//                  bname_adapter.setSelectedPosition(-1);
                    bname_adapter.setSelectedPosition(bname == null ? -1 : data.indexOf(bname));
                }
                bname_adapter.notifyDataSetInvalidated();
                E2_CommPerfBranchAty_New.isVisible=true;
                containerfg.setAlpha(0.6f);
                select.setVisibility(View.VISIBLE);
//                containerfg.setAlpha(0.5f);
            }
        });
        //意图中获取数据
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        data = bundle.getStringArrayList("data");
//        per_data = (ArrayList<PerfPpData_New>) getIntent().getSerializableExtra("per_data");
//        branid = getIntent().getStringExtra("branid");
        if (branid != null && Integer.parseInt(branid) < 1) {
            branid = Config.getCachedFirstBrandId(this);
        }
//        ppid = getIntent().getStringExtra("ppid");
        time_list.add("按周查询");
        time_list.add("按月查询");
        time_list.add("按年查询");
        bname_adapter = new TimeSelectAdapter(data, this);
        time_adapter = new TimeSelectAdapter(time_list, this);
        mListView.setAdapter(bname_adapter);
        time.setAdapter(time_adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data_index == position) {
                    if (isdataSelect) {
                        isdataSelect = false;
                    } else {
                        isdataSelect = true;
                    }
                    bname_adapter.setSelectedPosition(isdataSelect ? position : -1);
                } else {
                    bname_adapter.setSelectedPosition(position);
                }
                data_selection = bname_adapter.getSelectedPosition();
                bname_adapter.notifyDataSetInvalidated();
                data_index = position;
                isdataFirstSele = true;
                PerfPpData_New ppd = per_data.get(position);
//                branid = String.valueOfppd.branid);
                branid = ppd.branid;
                bname = per_data.get(position).name;
            }
        });

        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index == position) {
                    if (istimeSelect) {
                        istimeSelect = false;
                    } else {
                        istimeSelect = true;
                    }
                    time_adapter.setSelectedPosition(istimeSelect ? position : -1);
                } else {
                    time_adapter.setSelectedPosition(position);
                }
                selection = time_adapter.getSelectedPosition();
                time_adapter.notifyDataSetInvalidated();
                flag = position + 1;
                index = position;
                isFirstSele = true;
            }
        });

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        E2_SatisofWeekFg e2_SatisofWeekFg = new E2_SatisofWeekFg();
        Bundle args2 = new Bundle();
        args2.putString("branid", branid);
        args2.putString("ppid", ppid);
        e2_SatisofWeekFg.setArguments(args2);
        ft.replace(R.id.containerfg, e2_SatisofWeekFg);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerfg.setAlpha(1.0f);
        E2_CommPerfBranchAty_New.isVisible=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        select.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        select.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        select.setVisibility(View.GONE);
    }

    @OnClick({R.id.titlebar_iv_left, R.id.exit, R.id.confirm})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            //取消
            case R.id.exit: {
                time_adapter.setSelectedPosition(-1);
                time_adapter.notifyDataSetInvalidated();
                bname_adapter.setSelectedPosition(-1);
                bname_adapter.notifyDataSetInvalidated();
                select.setVisibility(View.GONE);
                containerfg.setAlpha(1.0f);
                E2_CommPerfBranchAty_New.isVisible=false;
                break;
            }
            //确定
            case R.id.confirm: {
                if (bname_adapter.getSelectedPosition() == (-1)) {
                    showToast("请选择门店");
                    return;
                }
                if (time_adapter.getSelectedPosition() == (-1)) {
                    showToast("请选择时间");
                    return;
                }
                containerfg.setAlpha(1.0f);
                E2_CommPerfBranchAty_New.isVisible=false;
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


    private void init(int flag) {
        switch (flag) {
            case 1:
                E2_SatisofWeekFg e2_SatisofWeekFg = new E2_SatisofWeekFg();
                Bundle args2 = new Bundle();
                args2.putString("branid", branid);
                args2.putString("ppid", ppid);
                e2_SatisofWeekFg.setArguments(args2);
                ft.replace(R.id.containerfg, e2_SatisofWeekFg);
                ft.commit();
                break;
            case 2:
                E2_SatisofMonthFg e2_SatisofMonthFg = new E2_SatisofMonthFg();
                Bundle args3 = new Bundle();
                args3.putString("branid", branid);
                args3.putString("ppid", ppid);
                e2_SatisofMonthFg.setArguments(args3);
                ft.replace(R.id.containerfg, e2_SatisofMonthFg);
                ft.commit();
                break;
            case 3:
                E2_SaitsofYearFg e2_SaitsofYearFg = new E2_SaitsofYearFg();
                Bundle args4 = new Bundle();
                args4.putString("branid", branid);
                args4.putString("ppid", ppid);
                e2_SaitsofYearFg.setArguments(args4);
                ft.replace(R.id.containerfg, e2_SaitsofYearFg);
                ft.commit();
                break;
            default:
                break;
        }
    }
}
