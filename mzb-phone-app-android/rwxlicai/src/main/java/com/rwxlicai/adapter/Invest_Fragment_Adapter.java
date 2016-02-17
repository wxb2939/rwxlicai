package com.rwxlicai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xuebing on 16/1/14.
 */
public class Invest_Fragment_Adapter extends FragmentPagerAdapter {

    List<Fragment> list;

    public Invest_Fragment_Adapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }

}