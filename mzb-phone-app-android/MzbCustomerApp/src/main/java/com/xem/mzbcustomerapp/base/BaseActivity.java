package com.xem.mzbcustomerapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xem.mzbcustomerapp.utils.AppManager;
import com.xem.mzbcustomerapp.utils.ToastUtils;
import com.xem.mzbcustomerapp.view.IProcessDialog;
import com.xem.mzbcustomerapp.view.MzbProgressDialog;

/**
 * Created by xuebing on 15/10/21.
 */
public abstract class BaseActivity extends FragmentActivity implements IProcessDialog {

    protected Context context;
    protected BaseApplication application;
    protected ImageLoader imageLoader;
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application = (BaseApplication)getApplication();
        imageLoader = ImageLoader.getInstance();
        gson = new Gson();
        AppManager.getAppManager().addActivity(this);
        context = this;
        BaseApplication.getInstance().addActivity(this);
        initView();
        initData();
    }


    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    protected void intent2Aty(Class<? extends Activity> tarAty){
        Intent intent = new Intent(this,tarAty);
        startActivity(intent);
    }

    protected void showToast(String msg){
        ToastUtils.showToast(this, msg, Toast.LENGTH_LONG);
    }


    MzbProgressDialog pd;

    @Override
    public void showProcessDialog() {
        pd = new MzbProgressDialog(this, "请稍后....");
        pd.show();
    }

    @Override
    public void closeProcessDialog() {
        if (pd != null)
        {
            pd.dismiss();
            pd = null;
        }
    }

}
