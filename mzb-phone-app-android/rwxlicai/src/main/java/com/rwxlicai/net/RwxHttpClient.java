package com.rwxlicai.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.PrepareDate;

/**
 * Created by xuebing on 16/1/13.
 */
public class RwxHttpClient{

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void clientGet(String url, NetCallBack cb) {
        client.get(url, cb);
    }

    public static void ClientPost(Context context,String url, RequestParams params, NetCallBack cb) {
//        client.addHeader(Config.KEY_TOKEN, null);
//        client.addHeader("JSESSIONID", null);
//        client.setTimeout(5000);
        client.addHeader("Request-From","AndroidApp");
        client.addHeader("deviceId", PrepareDate.getImdi(context));
//        client.addHeader("deviceId", "000000000000000");
        client.addHeader("randomCode", PrepareDate.generateRandomStr(10));
//        client.addHeader("randomCode", "1234567890");
        client.post(url, params, cb);
    }

    public static void ClientTokenPost(Context context, String url, RequestParams params, NetCallBack cb) {
//        client.setTimeout(5000);
        client.addHeader("TOKEN", Config.getCachedToken(context));
        client.addHeader("JSESSIONID", Config.getJSESSIONID(context));
        client.addHeader("Request-From","AndroidApp");
        client.addHeader("deviceId", PrepareDate.getImdi(context));
        client.addHeader("randomCode", PrepareDate.generateRandomStr(10));
        client.post(url, params, cb);
    }


}
