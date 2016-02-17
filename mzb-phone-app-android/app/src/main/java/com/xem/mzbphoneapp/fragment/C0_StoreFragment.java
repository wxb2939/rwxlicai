package com.xem.mzbphoneapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.TitleBuilder;

/**
 * Created by xuebing on 15/6/3.
 */
public class C0_StoreFragment extends MzbFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View footView = inflater.inflate(R.layout.c0_store_fg, container, false);

        new TitleBuilder(footView).setTitleText("商城");

        return footView;
    }
}
