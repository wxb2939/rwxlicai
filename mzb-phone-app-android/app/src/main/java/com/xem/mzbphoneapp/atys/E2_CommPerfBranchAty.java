package com.xem.mzbphoneapp.atys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.fragment.E1_PerfPpofDayFg;
import com.xem.mzbphoneapp.fragment.E2_PerfBranchofDayFg;
import com.xem.mzbphoneapp.fragment.E2_PerfBranchofMonthFg;
import com.xem.mzbphoneapp.fragment.E2_PerfBranchofWeekFg;
import com.xem.mzbphoneapp.fragment.E2_PerfBranchofYearFg;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/22.
 */
public class E2_CommPerfBranchAty extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private String branid = "";
    private String ppid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("员工业绩").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(E2_CommPerfBranchAty.this, DialogActivity.class);
                mIntent.putExtra("flag",1);
                startActivityForResult(mIntent, 0);
            }
        });

        branid = getIntent().getStringExtra("branid");
        ppid = getIntent().getStringExtra("ppid");

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        E2_PerfBranchofDayFg e2_PerfBranchofDayFg = new E2_PerfBranchofDayFg();
        Bundle args1 = new Bundle();
        args1.putString("branid",branid);
        args1.putString("ppid",ppid);
        e2_PerfBranchofDayFg.setArguments(args1);
        ft.replace(R.id.containerfg, e2_PerfBranchofDayFg);
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

                        E2_PerfBranchofDayFg e2_PerfBranchofDayFg = new E2_PerfBranchofDayFg();
                        Bundle args1 = new Bundle();
                        args1.putString("branid",branid);
                        args1.putString("ppid",ppid);
                        e2_PerfBranchofDayFg.setArguments(args1);
                        ft.replace(R.id.containerfg, e2_PerfBranchofDayFg);
                        ft.commit();
                        break;
                    case 2:

                        E2_PerfBranchofWeekFg e2_PerfBranchofWeekFg = new E2_PerfBranchofWeekFg();
                        Bundle args2 = new Bundle();
                        args2.putString("branid",branid);
                        args2.putString("ppid",ppid);
                        e2_PerfBranchofWeekFg.setArguments(args2);
                        ft.replace(R.id.containerfg, e2_PerfBranchofWeekFg);
                        ft.commit();
                        break;
                    case 3:

                        E2_PerfBranchofMonthFg e2_PerfBranchofMonthFg = new E2_PerfBranchofMonthFg();
                        Bundle args3 = new Bundle();
                        args3.putString("branid",branid);
                        args3.putString("ppid",ppid);
                        e2_PerfBranchofMonthFg.setArguments(args3);
                        ft.replace(R.id.containerfg, e2_PerfBranchofMonthFg);
                        ft.commit();
                        break;
                    case 4:

                        E2_PerfBranchofYearFg e2_PerfBranchofYearFg = new E2_PerfBranchofYearFg();
                        Bundle args4 = new Bundle();
                        args4.putString("branid",branid);
                        args4.putString("ppid",ppid);
                        e2_PerfBranchofYearFg.setArguments(args4);
                        ft.replace(R.id.containerfg, e2_PerfBranchofYearFg);
                        ft.commit();
                        break;
                    default:
                        break;
                }
        }
    }

}
