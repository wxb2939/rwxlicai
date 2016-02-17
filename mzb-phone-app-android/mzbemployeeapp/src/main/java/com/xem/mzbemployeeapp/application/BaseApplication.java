package com.xem.mzbemployeeapp.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by john on 2015/11/10.
 */
public class BaseApplication extends Application{
    private  static Context mContext;

    public BaseApplication() {
        mContext=this;
    }

    public static Context getmContext(){
        return  mContext;
    }
}
