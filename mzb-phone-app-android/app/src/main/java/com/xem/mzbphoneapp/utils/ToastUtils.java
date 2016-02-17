package com.xem.mzbphoneapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xuebing on 15/7/16.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context,CharSequence text,int duration) {
        if(mToast == null){
            mToast = Toast.makeText(context,text,duration);
        }else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
