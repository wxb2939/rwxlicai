package com.xem.mzbphoneapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.external.maxwin.view.XListView.IXListViewListener;
import com.xem.mzbphoneapp.atys.MainTabAty;
import com.xem.mzbphoneapp.com.IMzbFragment;
import com.xem.mzbphoneapp.utils.ToastUtils;

/**
 * Created by xuebing on 15/6/3.
 */
public class MzbFragment extends Fragment implements IXListViewListener, IMzbFragment {


    protected MainTabAty activity;
    protected ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainTabAty) getActivity();
        imageLoader = ImageLoader.getInstance();
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity){
        Intent intent = new Intent(activity,tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg){
        ToastUtils.showToast(activity, msg, Toast.LENGTH_LONG);
    }


    @Override
    public void onPictureTouch(boolean isLeft) {

    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }
}
