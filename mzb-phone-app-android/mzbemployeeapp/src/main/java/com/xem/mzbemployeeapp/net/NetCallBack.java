package com.xem.mzbemployeeapp.net;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xem.mzbemployeeapp.utils.IProcessDialog;

/**
 * Created by john on 2015/11/10.
 */
public abstract class NetCallBack extends AsyncHttpResponseHandler {

    IProcessDialog showProcess;

    public NetCallBack()
    {

    }

    public NetCallBack(IProcessDialog processDialog)
    {
        showProcess = processDialog;
    }

    @Override
    public void onStart() {
        Log.i("info", "请求开始，弹出进度框");
        showProcessDialog();
        super.onStart();
    }

    @Override
    public void onSuccess(String s) {
        Log.i("info", "请求成功，隐藏进度框" + s);
        closeProcessDialog();
        onMzbSuccess(s);
        super.onSuccess(s);
    }

    @Override
    public void onFailure(Throwable throwable, String s) {
        Log.i("info", "错误信息：" + s);
        closeProcessDialog();
        onMzbFailues(throwable);
        super.onFailure(throwable);
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.i("info", "网络请求失败，隐藏进度框");
        closeProcessDialog();
        onMzbFailues(throwable);
        super.onFailure(throwable);
    }

    @Override
    public void onFinish() {
        Log.i("info", "结束");
        super.onFinish();
    }

    public abstract void onMzbSuccess(String result);

    public abstract void onMzbFailues(Throwable arg0);



    private void showProcessDialog()
    {
        if (showProcess != null)
        {
            showProcess.showProcessDialog();
        }
    }

    private void closeProcessDialog()
    {
        if (showProcess != null)
        {
            showProcess.closeProcessDialog();
        }
    }
}
