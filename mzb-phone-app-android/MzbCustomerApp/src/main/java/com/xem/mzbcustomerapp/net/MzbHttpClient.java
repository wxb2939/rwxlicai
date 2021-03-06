package com.xem.mzbcustomerapp.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.utils.Config;

/**
 * Created by xuebing on 15/10/22.
 */
public class MzbHttpClient {

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void clientGet(String url, NetCallBack cb) {
        client.get(url, cb);
    }

    public static void ClientPost(String url, RequestParams params, NetCallBack cb) {
        client.post(url, params, cb);
    }

    public static void ClientTokenPost(Context context, String url, RequestParams params, NetCallBack cb) {
        client.addHeader(Config.KEY_TOKEN, Config.getCachedToken(context));
        client.post(url, params, cb);
    }
}
