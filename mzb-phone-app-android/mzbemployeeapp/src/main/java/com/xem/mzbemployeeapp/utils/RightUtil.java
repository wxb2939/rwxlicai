package com.xem.mzbemployeeapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.entity.PerfPpData_New;
import com.xem.mzbemployeeapp.entity.StoreFatisfy;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.views.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wellke on 2015/12/9.
 */
public class RightUtil {

    public static RightUtil instance=new RightUtil();
    Gson gson = new Gson();
    private RightUtil(){

    }

    public static RightUtil getInstance(){
        return instance;
    }

    //第一个界面获取门店信息的方法
    public void getPerfPpofDayData(final String ppid,String date_str,final String branid, final Context context,final Class<? extends Activity> tarAty) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "1");
        params1.put("ppid", ppid);
        params1.put("date", date_str);
        String str=null;
        params1.put("branid",str);
        final MzbProgressDialog pd = new MzbProgressDialog(context, "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PERFPP, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONArray jsonArray = new JSONArray(obj.getString("data"));
                        ArrayList<PerfPpData_New> per_data=new ArrayList();
                        ArrayList<String> data=new ArrayList();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PerfPpData_New perfPpData = gson.fromJson(object.toString(), PerfPpData_New.class);
                            data.add(perfPpData.name);
                            per_data.add(perfPpData);
                        }
                        Intent intent=new Intent(context,tarAty);
                        Bundle bundle=new Bundle();
                        bundle.putStringArrayList("data", data);
                        intent.putExtra("bundle", bundle);
                        intent.putExtra("per_data", (Serializable) per_data);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", branid);
                        context.startActivity(intent);
                        pd.dismiss();
                    } else {
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

    //第二个界面获取门店信息的方法
    public void getPerfPpofDayData(final String ppid,String date_str,final int branid, final Context context,final Class<? extends Activity> tarAty) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "1");
        params1.put("ppid", ppid);
        params1.put("date", date_str);
        if (branid > 0){
            params1.put("branid",String.valueOf(branid));
        }else{
            String str=null;
            params1.put("branid",str);
        }
        final MzbProgressDialog pd = new MzbProgressDialog(context, "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PERFPP, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONArray jsonArray = new JSONArray(obj.getString("data"));
                        ArrayList<PerfPpData_New> per_data = new ArrayList();
                        ArrayList<String> data = new ArrayList();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PerfPpData_New perfPpData = gson.fromJson(object.toString(), PerfPpData_New.class);
                            data.add(perfPpData.name);
                            per_data.add(perfPpData);
                        }
                        Intent intent = new Intent(context, tarAty);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("data", data);
                        intent.putExtra("bundle", bundle);
                        intent.putExtra("per_data", (Serializable) per_data);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", String.valueOf(branid));
                        context.startActivity(intent);
                        pd.dismiss();
                    } else {
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

    //获取门店满意度信息的方法
    public void GetStoreFatisfyList(final String ppid,final String branid, final Context context,final Class<? extends Activity> tarAty) {
        RequestParams params1 = new RequestParams();
        params1.put("ppid", ppid);
        final MzbProgressDialog pd = new MzbProgressDialog(context, "请稍后....");
        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_SBRANCH, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONArray jsonArray = new JSONArray(obj.getString("data"));
                        ArrayList<StoreFatisfy> per_data = new ArrayList();
                        ArrayList<String> data = new ArrayList();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            StoreFatisfy storeFatisfy = gson.fromJson(object.toString(), StoreFatisfy.class);
                            per_data.add(storeFatisfy);
                            data.add(storeFatisfy.getName());
                        }

                        Intent intent = new Intent(context, tarAty);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("data", data);
                        intent.putExtra("bundle", bundle);
                        intent.putExtra("per_data", (Serializable) per_data);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid",branid);
                        context.startActivity(intent);

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
