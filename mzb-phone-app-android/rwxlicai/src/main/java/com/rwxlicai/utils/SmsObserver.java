package com.rwxlicai.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.rwxlicai.activity.A0_RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuebing on 16/1/13.
 */
public class SmsObserver extends ContentObserver {

    private Context mcontext;
    private Handler mhandler;

    public SmsObserver(Context context,Handler handler) {
        super(handler);
        mcontext = context;
        mhandler = handler;
    }


    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        String code = "";




        if (uri.toString().equals("content://sms/raw")){
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mcontext.getContentResolver().query(inboxUri,null,null,null,"date desc");
        if (c != null){
            if (c.moveToFirst()){
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                if (!address.equals("106904604538389656")){
                    return;
                }
                Pattern pattern = Pattern.compile("(\\d{4})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()){
                    code = matcher.group(0);
                    Log.e("DEBUG", "code is" + code);
                    mhandler.obtainMessage(A0_RegisterActivity.MSG_RECEIVED_CODE,code).sendToTarget();
                }
            }
            c.close();
        }

    }
}