package com.xem.mzbemployeeapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.xem.mzbemployeeapp.application.BaseApplication;
import com.xem.mzbemployeeapp.utils.ToastUtils;

/**
 * Created by john on 2015/11/10.
 */
public class BaseActivity extends Activity{
    protected BaseApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        application = (BaseApplication)getApplication();
        init();
    }

    public void init() {

    }

    protected void showToast(String msg){
        ToastUtils.showToast(this, msg, Toast.LENGTH_LONG);
    }

    public <T> T findView(int res){
        return (T)findViewById(res);
    }

    //隐藏键盘输入法的方法
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
