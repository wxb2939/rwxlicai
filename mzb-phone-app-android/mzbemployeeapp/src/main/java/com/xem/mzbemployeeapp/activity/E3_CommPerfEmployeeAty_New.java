package com.xem.mzbemployeeapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.TimeSelectAdapter;
import com.xem.mzbemployeeapp.entity.PerfPpData;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofDayFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofMonthFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofWeekFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofYearFg;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.view.MyFrameLayout;
import com.xem.mzbemployeeapp.views.MyListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by wellke on 2015/12/7.
 */
public class E3_CommPerfEmployeeAty_New extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.containerfg)
    MyFrameLayout containerfg;
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
    private String empid = "";
    private String dayStr;
    private int flag;
    private int mFlag;

    ArrayList<String> time_list;
    ArrayList<String> data;
    ArrayList<PerfPpData> per_data;
    TimeSelectAdapter bname_adapter;
    TimeSelectAdapter time_adapter;
    boolean isSelect = false;
    boolean istimeSelect = false, isFirstSele = false;

    private String daysStr;
    private String weelStr;
    private String monStr;
    private String yearStr;
    int index = -1;
    int selection;
    boolean isViles=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty);
        ButterKnife.inject(this);
        E2_CommPerfBranchAty_New.isVisible=false;
        time_list = new ArrayList<String>();
        data = new ArrayList();
        per_data = new ArrayList();
        time_list.add("按日查询");
        time_list.add("按周查询");
        time_list.add("按月查询");
        time_list.add("按年查询");
        if (E2_CommPerfBranchAty_New.bname != null) {
            data.add(E2_CommPerfBranchAty_New.bname);
        } else {
            if (Config.getCachedIsexp(this) != null) {
                data.add(A0_ExpAty.employ.getBname());
            } else {
                data.add(Config.getCachedBrand(this));
            }
        }
        bname_adapter = new TimeSelectAdapter(data, this);
        time_adapter = new TimeSelectAdapter(time_list, this);
        mListView.setAdapter(bname_adapter);
        time.setAdapter(time_adapter);
        new TitleBuilder(this).setTitleText("业绩详情").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.getVisibility() == View.VISIBLE) {
                    E2_CommPerfBranchAty_New.isVisible=false;
                    containerfg.setAlpha(1.0f);
                    select.setVisibility(View.GONE);
                    return;
                }
//                bname_adapter.setSelectedPosition(isSelect ? 0 : -1);
                bname_adapter.setSelectedPosition(0);
                bname_adapter.notifyDataSetInvalidated();
                if (isFirstSele) {
                    time_adapter.setSelectedPosition(selection);
                } else {
                    time_adapter.setSelectedPosition(0);
                }
                time_adapter.notifyDataSetInvalidated();
                containerfg.setAlpha(0.6f);
                E2_CommPerfBranchAty_New.isVisible=true;
                select.setVisibility(View.VISIBLE);
            }
        });

        empid = getIntent().getStringExtra("empid");
        flag = getIntent().getIntExtra("flag", 1);
        dayStr = getIntent().getStringExtra("time");

        daysStr = getIntent().getStringExtra("time");
        weelStr = getIntent().getStringExtra("time");
        monStr = getIntent().getStringExtra("time");
        yearStr = getIntent().getStringExtra("time");

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        init(flag);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSelect) {
                    isSelect = false;
                } else {
                    isSelect = true;
                }
                bname_adapter.setSelectedPosition(isSelect ? 0 : -1);
                bname_adapter.notifyDataSetInvalidated();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        E2_CommPerfBranchAty_New.isVisible=false;
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
                E2_CommPerfBranchAty_New.isVisible=false;
                containerfg.setAlpha(1.0f);
                select.setVisibility(View.GONE);
                break;
            }
//            确定
            case R.id.confirm: {
                if (bname_adapter.getSelectedPosition() == -1) {
                    showToast("请选择门店");
                    return;
                }
                if (time_adapter.getSelectedPosition() == -1) {
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
        ArrayList<String> data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            data.add("门店" + i);
        }
        switch (flag) {
            case 1:
                if (E3_PerfEmpofDayFg.dayStr != null) {
                    daysStr = E3_PerfEmpofDayFg.dayStr;
                }
                E3_PerfEmpofDayFg e3_PerfEmpofDayFg = new E3_PerfEmpofDayFg();
                Bundle args1 = new Bundle();
                args1.putString("empid", empid);
                args1.putString("time", daysStr);
                e3_PerfEmpofDayFg.setArguments(args1);
                ft.replace(R.id.containerfg, e3_PerfEmpofDayFg);
                ft.commit();
                break;
            case 2:
                if (E3_PerfEmpofWeekFg.dayStr != null) {
                    weelStr = E3_PerfEmpofWeekFg.dayStr;
                }
                E3_PerfEmpofWeekFg e3_PerfEmpofWeekFg = new E3_PerfEmpofWeekFg();
                Bundle args2 = new Bundle();
                args2.putString("empid", empid);
                args2.putString("time", weelStr);
                e3_PerfEmpofWeekFg.setArguments(args2);
                ft.replace(R.id.containerfg, e3_PerfEmpofWeekFg);
                ft.commit();
                break;
            case 3:
                if (E3_PerfEmpofMonthFg.dayStr != null) {
                    monStr = E3_PerfEmpofMonthFg.dayStr;
                }
                E3_PerfEmpofMonthFg e3_PerfEmpofMonthFg = new E3_PerfEmpofMonthFg();
                Bundle args3 = new Bundle();
                args3.putString("empid", empid);
                args3.putString("time", monStr);
                e3_PerfEmpofMonthFg.setArguments(args3);
                ft.replace(R.id.containerfg, e3_PerfEmpofMonthFg);
                ft.commit();
                break;
            case 4:
                if (E3_PerfEmpofYearFg.dayStr != null) {
                    yearStr = E3_PerfEmpofYearFg.dayStr;
                }
                E3_PerfEmpofYearFg e3_PerfEmpofYearFg = new E3_PerfEmpofYearFg();
                Bundle args4 = new Bundle();
                args4.putString("empid", empid);
                args4.putString("time", yearStr);
                e3_PerfEmpofYearFg.setArguments(args4);
                ft.replace(R.id.containerfg, e3_PerfEmpofYearFg);
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
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        E3_PerfEmpofDayFg.dayStr=null;
        E3_PerfEmpofWeekFg.dayStr=null;
        E3_PerfEmpofMonthFg.dayStr=null;
        E3_PerfEmpofYearFg.dayStr=null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        select.setVisibility(View.GONE);
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        E3_PerfEmpofDayFg.dayStr=null;
        E3_PerfEmpofWeekFg.dayStr=null;
        E3_PerfEmpofMonthFg.dayStr=null;
        E3_PerfEmpofYearFg.dayStr=null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        select.setVisibility(View.GONE);
        E2_CommPerfBranchAty_New.isVisible=false;
        containerfg.setAlpha(1.0f);
        E3_PerfEmpofDayFg.dayStr=null;
        E3_PerfEmpofWeekFg.dayStr=null;
        E3_PerfEmpofMonthFg.dayStr=null;
        E3_PerfEmpofYearFg.dayStr=null;
    }
}