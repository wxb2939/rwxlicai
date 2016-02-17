package com.rwxlicai.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rwxlicai.utils.AppManager;
import com.rwxlicai.utils.ToastUtils;
import com.rwxlicai.view.IProcessDialog;
import com.rwxlicai.view.MzbProgressDialog;

/**
 * Created by xuebing on 15/12/28.
 */
public abstract class BaseActivity extends AppCompatActivity implements IProcessDialog {

    protected Context context;
    protected BaseApplication application;
    protected ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application = (BaseApplication) getApplication();
        imageLoader = ImageLoader.getInstance();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void onPause() {
        super.onPause();
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
