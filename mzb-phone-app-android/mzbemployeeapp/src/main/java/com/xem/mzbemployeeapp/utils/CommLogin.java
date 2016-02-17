package com.xem.mzbemployeeapp.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.activity.A0_ExpAty;
import com.xem.mzbemployeeapp.activity.HomeActivity;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.entity.EmployeData;
import com.xem.mzbemployeeapp.entity.Employes;
import com.xem.mzbemployeeapp.entity.Experience;
import com.xem.mzbemployeeapp.net.MzbHttpClient;
import com.xem.mzbemployeeapp.net.NetCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by wellke on 2015/11/10.
 */
public class CommLogin {
    Context context;

    public CommLogin() {

    }

    public CommLogin(Context context) {
        this.context = context;
    }

    Gson gson = new Gson();



    public void Login(final String mobile, final String password, final MzbApplication app) {
        RequestParams params = new RequestParams();
        params.put("type", "B");
        params.put("mobile", mobile);
        params.put("password", password);
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Employes loginData = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
                        Config.cachedUid(context, loginData.getUid());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedIsemploye(context, loginData.getIsemploye());
                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedPortrait(context, employeData.getPortrait());
                            Config.cachedName(context, employeData.getName());
                            Config.cachedSex(context, employeData.sex);
                            Config.cachedBrandAccid(context, employeData.getAccid());
                            Config.cachedBrandEmpid(context, employeData.getEmpid());
                            Config.cachedBrandCodes(context, employeData.getRoles());
                            Config.cachedBrandRights(context, employeData.getRights());
                            Config.cachedBrand(context, employeData.bname);
                            Config.cachedFirstBrandId(context, employeData.firstbranid);
                        }
                        app.setIsLogin(true);
                        new GetPushagrs(context).refreshPushagrs();
                        JPushInterface.resumePush(context.getApplicationContext());
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
//                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
                ToastUtils.showToast("登录失败", 0);
            }
        });

    }

    //登录体验
    public void DoLogin(String role,final MzbApplication app) {
        RequestParams params1 = new RequestParams();
//        params1.put("type", "B");
        params1.put("role", role);
        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_DEMOLOGIN, params1, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Experience loginData = gson.fromJson(obj.getJSONObject("data").toString(), Experience.class);
                        A0_ExpAty.employ = loginData.getEmploye();
//                        Employes loginData = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
//                        Config.cachedUid(context, loginData.getUid());
//                        Config.cachedToken(context, loginData.getToken());
//                        Config.cachedIsemploye(context, loginData.getIsemploye());
//                        EmployeData employeData = loginData.getEmploye();
//                        if (employeData != null) {
//                            Config.cachedPortrait(context, employeData.getPortrait());
//                            Config.cachedName(context, employeData.getName());
//                            Config.cachedBrandAccid(context, employeData.getAccid());
//                            Config.cachedBrandEmpid(context, employeData.getEmpid());
//                            Config.cachedBrandCodes(context, employeData.getRoles());
//                            Config.cachedBrandRights(context, employeData.getRights());
//                        }
                        app.setIsLogin(true);
                        new GetPushagrs(context).refreshPushagrs();
                        JPushInterface.resumePush(context.getApplicationContext());
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
//                pd.dismiss();

            }
        });
    }

    public void FirstLogin(final String mobile, final String password, final MzbApplication app) {
        RequestParams params = new RequestParams();
        params.put("type", "B");
        params.put("mobile", mobile);
        params.put("password", password);
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Employes loginData = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
                        Config.cachedUid(context, loginData.getUid());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedIsemploye(context, loginData.getIsemploye());
                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedPortrait(context, employeData.getPortrait());
                            Config.cachedName(context, employeData.getName());
                            Config.cachedBrandAccid(context, employeData.getAccid());
                            Config.cachedBrandEmpid(context, employeData.getEmpid());
                            Config.cachedBrandCodes(context, employeData.getRoles());
                            Config.cachedBrandRights(context, employeData.getRights());
                        }
                        app.setIsLogin(true);
                        new GetPushagrs(context).refreshPushagrs();
                        JPushInterface.resumePush(context.getApplicationContext());
                        context.startActivity(new Intent(context, HomeActivity.class));
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
//                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
                ToastUtils.showToast("登录失败", 0);
            }
        });

    }


    public void RetPwdLogin(final String mobile, final String password, final MzbApplication app) {
        RequestParams params = new RequestParams();
        params.put("type", "B");
        params.put("mobile", mobile);
        params.put("password", password);
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Employes loginData = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
                        Config.cachedUid(context, loginData.getUid());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedIsemploye(context, loginData.getIsemploye());
                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedPortrait(context, employeData.getPortrait());
                            Config.cachedName(context, employeData.getName());
                            Config.cachedBrandAccid(context, employeData.getAccid());
                            Config.cachedBrandEmpid(context, employeData.getEmpid());
                            Config.cachedBrandCodes(context, employeData.getRoles());
                            Config.cachedBrandRights(context, employeData.getRights());
                        }
                        app.setIsLogin(true);
                        new GetPushagrs(context).refreshPushagrs();
                        JPushInterface.resumePush(context.getApplicationContext());
//                        intent2Aty(HomeActivity.class);
                        context.startActivity(new Intent(context, HomeActivity.class));
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
//                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
                ToastUtils.showToast("登录失败", 0);
            }
        });

    }


}
