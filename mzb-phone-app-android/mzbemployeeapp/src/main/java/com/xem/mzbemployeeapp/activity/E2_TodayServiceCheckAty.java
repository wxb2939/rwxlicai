package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.E1_InfoFgAdapter;
import com.xem.mzbemployeeapp.entity.ttpService;
import com.xem.mzbemployeeapp.fragment.E2_ServiceDoingFg;
import com.xem.mzbemployeeapp.fragment.E2_ServiceDoneFg;
import com.xem.mzbemployeeapp.fragment.E2_ServiceTodoFg;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/29.
 */
public class E2_TodayServiceCheckAty extends FragmentActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.viewpage)
    NoScrollViewPager viewPagers;


    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    private ttpService ttp;
    private String branid = "";
    private String ppid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_today_service_check_aty);
        new TitleBuilder(this).setTitleText("门店当日服务查询").setLeftImage(R.mipmap.top_view_back);
        ppid = getIntent().getStringExtra("ppid");
        branid = getIntent().getStringExtra("branid");
        ButterKnife.inject(this);
        setlinearLayouts();
        settextview();
        init();
    }


    public void init(){
        List<Fragment> totalFragement = new ArrayList<Fragment>();

        E2_ServiceDoingFg e2_serviceDoingFg = new E2_ServiceDoingFg();
        Bundle args1 = new Bundle();

        args1.putString("branid", branid);
        args1.putString("ppid",ppid);
        e2_serviceDoingFg.setArguments(args1);
        totalFragement.add(e2_serviceDoingFg);


        E2_ServiceTodoFg e2_serviceTodoFg = new E2_ServiceTodoFg();
        Bundle args2 = new Bundle();
        args2.putString("branid", branid);
        args2.putString("ppid",ppid);
        e2_serviceTodoFg.setArguments(args2);
        totalFragement.add(e2_serviceTodoFg);

        E2_ServiceDoneFg e2_ServiceDoneFg = new E2_ServiceDoneFg();
        Bundle args3 = new Bundle();
        args3.putString("branid", branid);
        args3.putString("ppid",ppid);
        e2_ServiceDoneFg.setArguments(args3);
        totalFragement.add(e2_ServiceDoneFg);



        viewPagers.setAdapter(new E1_InfoFgAdapter(getSupportFragmentManager(), totalFragement));
        //设置显示那页
        viewPagers.setCurrentItem(0);
        viewPagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                resetlaybg();
                linearLayouts[position].setBackgroundResource(R.mipmap.tab1);
                textViews[position].setTextColor(getResources().getColor(R.color.color_deep_text));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    //点击linerlayout实现切换fragment的效果

    public void LayoutOnclick(View v) {
        // 每次点击都重置linearLayouts的背景、textViews字体颜色
        switch (v.getId()) {
            case R.id.doing:
                resetlaybg();
                viewPagers.setCurrentItem(0);
                linearLayouts[0].setBackgroundResource(R.mipmap.tab2);
                textViews[0].setTextColor(getResources().getColor(
                        R.color.color_deep_text));
                break;

            case R.id.todo:
                resetlaybg();
                viewPagers.setCurrentItem(1);
                linearLayouts[1].setBackgroundResource(R.mipmap.tab2);
                textViews[1].setTextColor(getResources().getColor(
                        R.color.color_deep_text));
                break;
            case R.id.done:
                resetlaybg();
                viewPagers.setCurrentItem(2);
                linearLayouts[2].setBackgroundResource(R.mipmap.tab2);
                textViews[2].setTextColor(getResources().getColor(
                        R.color.color_deep_text));
                break;
        }
    }



    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }

    /** 初始化linerlayout */
    public void setlinearLayouts() {
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = (LinearLayout) findViewById(R.id.doing);
        linearLayouts[1] = (LinearLayout) findViewById(R.id.todo);
        linearLayouts[2] = (LinearLayout) findViewById(R.id.done);
        linearLayouts[0].setBackgroundResource(R.mipmap.tab2);
    }

    /** 初始化textview */
    public void settextview() {
        textViews = new TextView[3];
        textViews[0] = (TextView) findViewById(R.id.tvdoing);
        textViews[1] = (TextView) findViewById(R.id.tvtodo);
        textViews[2] = (TextView) findViewById(R.id.tvdone);
        textViews[0].setTextColor(getResources().getColor(
                R.color.color_deep_text));
    }

    /** 重置linearLayouts、textViews */
    public void resetlaybg() {
        for (int i = 0; i < 3; i++) {
            // linearLayouts[i].setBackgroundResource(R.drawable.ai);
            textViews[i].setTextColor(getResources().getColor(R.color.color_middle_text));
            linearLayouts[i].setBackgroundResource(R.mipmap.tab1);
        }

    }

}
