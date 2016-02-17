package com.xem.mzbemployeeapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xuebing on 15/10/22.
 */
public class B0_Info_Fragment_Adapter extends FragmentPagerAdapter {

    List<Fragment> list;

    public B0_Info_Fragment_Adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}