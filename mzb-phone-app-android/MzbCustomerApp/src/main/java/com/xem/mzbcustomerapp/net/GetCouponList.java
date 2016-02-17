package com.xem.mzbcustomerapp.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.entity.CouponData;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by xuebing on 15/10/30.
 */
public class GetCouponList {

    private Context context;
    private JSONArray jsonArray;
    private List<CouponData> data;
    private int curPage;
    PullToRefreshListView listView;

    public GetCouponList(Context context, List<CouponData> data, PullToRefreshListView listView) {
        this.context = context;
        this.data = data;
        this.listView = listView;
    }

    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", Config.getCachedUid(context));
        params1.put("finished", "false");
        params1.put("begin", page + "");
        params1.put("count", 20 + "");

        final MzbProgressDialog pd = new MzbProgressDialog(context, "请稍后....");
        pd.show();

        MzbHttpClient.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.service_records, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        if (page == 0) {
                            data.clear();
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CouponData couponData = gson.fromJson(object.toString(), CouponData.class);
                            data.add(couponData);
                        }
                        curPage = data.size();
                        listView.onRefreshComplete();
                        pd.dismiss();
                    } else {
                        listView.onRefreshComplete();
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
            }
        });
    }
}
