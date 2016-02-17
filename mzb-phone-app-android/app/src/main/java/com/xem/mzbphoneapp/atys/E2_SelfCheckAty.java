package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.fragment.E2_ServiceDoingFg;
import com.xem.mzbphoneapp.fragment.E2_ServiceDoneFg;
import com.xem.mzbphoneapp.fragment.E2_ServiceTodoFg;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import java.util.ArrayList;

/**
 * Created by xuebing on 15/7/28.
 */
public class E2_SelfCheckAty extends FragmentActivity{

    private ViewPager m_vp;
    // 通过pagerTabStrip可以设置标题的属性
    private PagerTabStrip pagerTabStrip;
    private E2_ServiceDoingFg serviceDoingFg;
    private E2_ServiceTodoFg serviceTodoFg;
    private E2_ServiceDoneFg serviceDoneFg;


    // 页面列表
    private ArrayList<Fragment> fragmentList;
    // 标题列表
    private ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_self_check_aty);
        new TitleBuilder(E2_SelfCheckAty.this).setTitleText("门店当日服务查询").setLeftImage(R.mipmap.top_view_back).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }



    public void initView() {
        m_vp = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
        // 设置下划线颜色
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.common_color));
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.common_white));
        serviceDoingFg = new E2_ServiceDoingFg();
        serviceTodoFg = new E2_ServiceTodoFg();
        serviceDoneFg = new E2_ServiceDoneFg();
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(serviceDoingFg);
        fragmentList.add(serviceTodoFg);
        fragmentList.add(serviceDoneFg);
        titleList.add("服务中");
        titleList.add("预约中");
        titleList.add("已离店");
        m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return titleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
