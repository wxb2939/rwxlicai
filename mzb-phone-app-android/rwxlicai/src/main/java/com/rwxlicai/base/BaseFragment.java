package com.rwxlicai.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by xuebing on 15/12/29.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context context;
    public View rootView;
    protected BaseApplication baseApplication;
    protected ImageLoader imageLoader;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        baseApplication = (BaseApplication) getActivity().getApplication();
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = initView(inflater);
        return rootView;
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onClick(View arg0) {


    }

    protected abstract View initView(LayoutInflater inflater);

    protected abstract void initData();

    protected abstract void processClick(View v);
}