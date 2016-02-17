package com.xem.mzbphoneapp.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.xem.mzbphoneapp.atys.E1_EmployeeNewsAty;
import com.xem.mzbphoneapp.atys.NotificationAty;
import com.xem.mzbphoneapp.model.BaseModel;
import com.xem.mzbphoneapp.model.NewsInfo;
import com.xem.mzbphoneapp.model.ServeInfo;
import com.xem.mzbphoneapp.utils.Config;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/6/5.
 */
public class MzbReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private ArrayList<ServeInfo> list = new ArrayList<ServeInfo>();
    private BaseModel bm;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Gson gson = new Gson();
        bm = gson.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), BaseModel.class);
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            NewsInfo news = new NewsInfo();
            news.type = bm.type;
            news.sendTime = bm.sendTime;
            news.detail = bundle.getString(JPushInterface.EXTRA_ALERT);
            news.extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            news.uid = Config.getCachedUid(context);
            news.save();

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            if (bm.type.startsWith("2")){
                Intent emp = new Intent(context, E1_EmployeeNewsAty.class);
                emp.putExtras(bundle);
                emp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(emp);
            } else {
                Intent i = new Intent(context, NotificationAty.class);
                i.putExtra("flag",bm.type);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);
            }

            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);

            /*String str =  bundle.getString(JPushInterface.EXTRA_EXTRA);
            Uri uri = Uri.parse(str);
            Intent intent1 = new Intent(intent.ACTION_VIEW,uri);
            context.startActivity(intent1);*/

            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
