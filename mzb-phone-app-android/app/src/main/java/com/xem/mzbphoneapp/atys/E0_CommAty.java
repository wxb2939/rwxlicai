package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.xem.mzbphoneapp.R;

/**
 * Created by xuebing on 15/6/29.
 */
public class E0_CommAty extends FragmentActivity {


    private E1_SettingAty mContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_fragment);
        FragmentManager fm = getSupportFragmentManager();
//        mContentFragment = (E1_SettingAty) fm.findFragmentById(R.id.container);

        if (mContentFragment == null) {
            mContentFragment = new E1_SettingAty();
//            fm.beginTransaction().replace(R.id.container, mContentFragment).commit();
        }
    }
}
