package com.xem.mzbphoneapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.atys.MzbApplication;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.EmployeData;
import com.xem.mzbphoneapp.entity.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xuebing on 15/7/13.
 */


public class CommLogin {
    Context context;

    public CommLogin(Context context) {
        this.context = context;
    }


    public void Login(String mobile, String password, final MzbApplication app) {

        RequestParams params = new RequestParams();
        params.put("type", "B");
        params.put("mobile", mobile);
        params.put("password", password);

        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Gson gson = new Gson();
                        LoginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), LoginData.class);

                        Config.cachedUid(context, loginData.getUid());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedName(context, loginData.getName());
                        Config.cachedPortrait(context, loginData.getPortrait());
                        Config.cachedScore(context, loginData.getScore());


                        EmployeData employeData = loginData.getEmploye();

                        if (employeData != null) {
                            Config.cachedBrandAccid(context, employeData.getAccid());
                            Config.cachedBrandEmpid(context, employeData.getEmpid());
                            Config.cachedBrandCodes(context, employeData.getRoles());
                            Config.cachedBrandRights(context, employeData.getRights());
                        }

                        app.setIsLogin(true);
                        BranchData branchData = loginData.getBranch();
                        if (branchData != null) {
                            app.setBranchData(branchData);
                        }
//                        JPushInterface.resumePush(context.getApplicationContext());
//                        new GetPushagrs(context).refreshPushagrs();
                    } else {
//                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
