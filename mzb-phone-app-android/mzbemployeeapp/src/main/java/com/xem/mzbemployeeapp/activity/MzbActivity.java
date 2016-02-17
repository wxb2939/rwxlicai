package com.xem.mzbemployeeapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.IProcessDialog;
import com.xem.mzbemployeeapp.utils.NetWorkHelper;
import com.xem.mzbemployeeapp.utils.ToastUtils;
import com.xem.mzbemployeeapp.views.MzbProgressDialog;


/**
 * Created by wellke on 15/6/13.
 */
public class MzbActivity extends Activity implements IProcessDialog {

    protected String TAG;

    protected AlertDialog mAlertDialog;
    protected MzbApplication application;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    protected ImageLoader imageLoader;
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application = (MzbApplication) getApplication();
        sp = getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE);
        editor = getSharedPreferences(Config.APP_ID, Context.MODE_PRIVATE).edit();
        imageLoader = ImageLoader.getInstance();
        gson = new Gson();
        MzbApplication.getInstance().addActivity(this);
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



    protected ProgressDialog showProgressDialog(String pTitle, String pMessage,
                                                DialogInterface.OnCancelListener pCancelClickListener) {
        mAlertDialog = ProgressDialog.show(this, pTitle, pMessage, true, true);
        mAlertDialog.setOnCancelListener(pCancelClickListener);
        return (ProgressDialog) mAlertDialog;
    }

    protected void closeMDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    //网络操作时执行并提示
    protected Boolean checkNetAddToast() {
        if (NetWorkHelper.isNetworkAvailable(this)){
            return true;
        }else {
            handleNetworkError();
            return false;
        }
    }

    private int network_err_count = 0;

    protected void handleNetworkError() {
        network_err_count++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MzbActivity.this, getString(R.string.network_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
