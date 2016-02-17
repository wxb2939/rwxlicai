package com.xem.mzbemployeeapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofDayFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofMonthFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofWeekFg;
import com.xem.mzbemployeeapp.fragment.E3_PerfEmpofYearFg;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/22.
 */
public class E3_CommPerfEmployeeAty extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;


    private FragmentManager fm;
    private FragmentTransaction ft;
    private String empid = "";
    private String dayStr;
    private int flag;
    private int mFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("业绩详情").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(E3_CommPerfEmployeeAty.this, DialogActivity.class);
                mIntent.putExtra("flag", 1);
                startActivityForResult(mIntent, 0);
            }
        });

        empid = getIntent().getStringExtra("empid");
        flag = getIntent().getIntExtra("flag", 1);
        dayStr = getIntent().getStringExtra("time");

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        init(flag);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.titlebar_iv_left})
    public void click(View v){
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        switch (resultCode) {
            case RESULT_CANCELED:
                break;
            case RESULT_OK:
                mFlag = data.getIntExtra("age",-1);
                init(mFlag);
        }
    }


    private void init(int flag){
        ArrayList<String> data=new ArrayList();
        for (int i=0; i<10; i++){
            data.add("门店"+i);
        }
        switch (flag){
            case 1:
                E3_PerfEmpofDayFg e3_PerfEmpofDayFg = new E3_PerfEmpofDayFg();
                Bundle args1 = new Bundle();
                args1.putString("empid",empid);
                 e3_PerfEmpofDayFg.setArguments(args1);
                ft.replace(R.id.containerfg, e3_PerfEmpofDayFg);
                ft.commit();
                break;
            case 2:

                E3_PerfEmpofWeekFg e3_PerfEmpofWeekFg = new E3_PerfEmpofWeekFg();
                Bundle args2 = new Bundle();
                args2.putString("empid",empid);
                args2.putString("time",dayStr);
                e3_PerfEmpofWeekFg.setArguments(args2);
                ft.replace(R.id.containerfg, e3_PerfEmpofWeekFg);
                ft.commit();
                break;
            case 3:

                E3_PerfEmpofMonthFg e3_PerfEmpofMonthFg = new E3_PerfEmpofMonthFg();
                Bundle args3 = new Bundle();
                args3.putString("empid",empid);
                args3.putString("time",dayStr);
                e3_PerfEmpofMonthFg.setArguments(args3);
                ft.replace(R.id.containerfg, e3_PerfEmpofMonthFg);
                ft.commit();
                break;
            case 4:

                E3_PerfEmpofYearFg e3_PerfEmpofYearFg = new E3_PerfEmpofYearFg();
                Bundle args4 = new Bundle();
                args4.putString("empid",empid);
                args4.putString("time",dayStr);
                e3_PerfEmpofYearFg.setArguments(args4);
                ft.replace(R.id.containerfg, e3_PerfEmpofYearFg);
                ft.commit();
                break;
            default:
                break;
        }
    }
}