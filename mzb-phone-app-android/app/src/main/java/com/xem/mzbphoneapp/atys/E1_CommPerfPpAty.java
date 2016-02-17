package com.xem.mzbphoneapp.atys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.fragment.E1_PerfPpofDayFg;
import com.xem.mzbphoneapp.fragment.E1_PerfPpofMonthFg;
import com.xem.mzbphoneapp.fragment.E1_PerfPpofWeekFg;
import com.xem.mzbphoneapp.fragment.E1_PerfPpofYearFg;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/22.
 */
public class E1_CommPerfPpAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private String ppid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_comm_select_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("业绩详情(门店)").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(E1_CommPerfPpAty.this, DialogActivity.class);
                mIntent.putExtra("flag",1);
                startActivityForResult(mIntent, 0);
            }
        });

        ppid = getIntent().getStringExtra("ppid");

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        E1_PerfPpofDayFg e1_PerfPpofDayFg = new E1_PerfPpofDayFg();
        Bundle args1 = new Bundle();
        args1.putString("ppid",ppid);
        e1_PerfPpofDayFg.setArguments(args1);
        ft.replace(R.id.containerfg, e1_PerfPpofDayFg);
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

                        E1_PerfPpofDayFg e1_PerfPpofDayFg = new E1_PerfPpofDayFg();
                        Bundle args1 = new Bundle();
                        args1.putString("ppid",ppid);
                        e1_PerfPpofDayFg.setArguments(args1);
                        ft.replace(R.id.containerfg, e1_PerfPpofDayFg);
                        ft.commit();
                        break;
                    case 2:

                        E1_PerfPpofWeekFg e1_PerfPpofWeekFg = new E1_PerfPpofWeekFg();
                        Bundle args2 = new Bundle();
                        args2.putString("ppid",ppid);
                        e1_PerfPpofWeekFg.setArguments(args2);
                        ft.replace(R.id.containerfg, e1_PerfPpofWeekFg);
                        ft.commit();
                        break;
                    case 3:

                        E1_PerfPpofMonthFg e1_PerfPpofMonthFg = new E1_PerfPpofMonthFg();
                        Bundle args3 = new Bundle();
                        args3.putString("ppid",ppid);
                        e1_PerfPpofMonthFg.setArguments(args3);
                        ft.replace(R.id.containerfg, e1_PerfPpofMonthFg);
                        ft.commit();
                        break;
                    case 4:

                        E1_PerfPpofYearFg e1_PerfPpofYearFg = new E1_PerfPpofYearFg();
                        Bundle args4 = new Bundle();
                        args4.putString("ppid",ppid);
                        e1_PerfPpofYearFg.setArguments(args4);
                        ft.replace(R.id.containerfg, e1_PerfPpofYearFg);
                        ft.commit();
                        break;
                    default:
                        break;
                }
        }
    }
}
