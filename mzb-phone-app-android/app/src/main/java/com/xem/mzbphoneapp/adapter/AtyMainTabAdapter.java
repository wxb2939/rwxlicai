package com.xem.mzbphoneapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by xuebing on 15/6/3.
 */
public class AtyMainTabAdapter extends FragmentPagerAdapter {


    private ArrayList<Fragment> fragmentsList;

    public AtyMainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public AtyMainTabAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public Fragment getItem(int index) {
        return fragmentsList.get(index);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
