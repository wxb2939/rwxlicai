package com.xem.mzbemployeeapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.fragment.E2_SaitsofYearFg;
import com.xem.mzbemployeeapp.fragment.E2_SatisofDayFg;
import com.xem.mzbemployeeapp.fragment.E2_SatisofMonthFg;
import com.xem.mzbemployeeapp.fragment.E2_SatisofWeekFg;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/27.
 */
public class E1_CommSelectAty extends MzbActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;


    @InjectView(R.id.titlebar_iv_left)
    ImageView back;


    private String branid = "";
    private String ppid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("满意度得分(员工)").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(E1_CommSelectAty.this, DialogActivity.class), 0);
            }
        });

        branid = getIntent().getStringExtra("branid");
        ppid = getIntent().getStringExtra("ppid");

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        /*E2_SatisofDayFg e2_SatisofDayFg = new E2_SatisofDayFg();
        Bundle args1 = new Bundle();
        args1.putString("ttp",ttp);
        e2_SatisofDayFg.setArguments(args1);*/

        E2_SatisofWeekFg e2_SatisofWeekFg = new E2_SatisofWeekFg();
        Bundle args2 = new Bundle();
        args2.putString("branid",branid);
        args2.putString("ppid",ppid);
        e2_SatisofWeekFg.setArguments(args2);
        ft.replace(R.id.containerfg, e2_SatisofWeekFg);
        ft.commit();

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
                switch (data.getIntExtra("age", -1)) {
                    case 1:

                        E2_SatisofDayFg e2_SatisofDayFg = new E2_SatisofDayFg();
                        Bundle args1 = new Bundle();
                        args1.putString("branid",branid);
                        args1.putString("ppid",ppid);
                        e2_SatisofDayFg.setArguments(args1);
                        ft.replace(R.id.containerfg, e2_SatisofDayFg);
                        ft.commit();
                        break;
                    case 2:

                        E2_SatisofWeekFg e2_SatisofWeekFg = new E2_SatisofWeekFg();
                        Bundle args2 = new Bundle();
                        args2.putString("branid",branid);
                        args2.putString("ppid",ppid);
                        e2_SatisofWeekFg.setArguments(args2);
                        ft.replace(R.id.containerfg, e2_SatisofWeekFg);
                        ft.commit();
                        break;
                    case 3:

                        E2_SatisofMonthFg e2_SatisofMonthFg = new E2_SatisofMonthFg();
                        Bundle args3 = new Bundle();
                        args3.putString("branid",branid);
                        args3.putString("ppid",ppid);
                        e2_SatisofMonthFg.setArguments(args3);
                        ft.replace(R.id.containerfg, e2_SatisofMonthFg);
                        ft.commit();
                        break;
                    case 4:

                        E2_SaitsofYearFg e2_SaitsofYearFg = new E2_SaitsofYearFg();
                        Bundle args4 = new Bundle();
                        args4.putString("branid",branid);
                        args4.putString("ppid",ppid);
                        e2_SaitsofYearFg.setArguments(args4);
                        ft.replace(R.id.containerfg, e2_SaitsofYearFg);
                        ft.commit();
                        break;
                    default:
                        break;
                }
        }
    }
}
