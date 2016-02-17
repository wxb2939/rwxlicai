package com.xem.mzbcustomerapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.base.BaseApplication;
import com.xem.mzbcustomerapp.entity.CdefaultData;
import com.xem.mzbcustomerapp.entity.CloginData;
import com.xem.mzbcustomerapp.entity.branch;
import com.xem.mzbcustomerapp.entity.cate;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xuebing on 15/10/27.
 */
public class CommLogin {
    Context context;
    public CommLogin(Context context) {
        this.context = context;
    }
    Gson gson = new Gson();

    public void Login(final String mobile, final String password, final BaseApplication app) {

        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.clogin, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        Config.cachedPhone(context, mobile);
                        Config.cachedPassword(context, password);
                        CloginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), CloginData.class);
                        Config.cachedUid(context, loginData.getUid().toString());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedName(context, loginData.getName());
                        Config.cachedPortrait(context, loginData.getPortrait());
                        Config.cachedScore(context, loginData.getScore());
                        Config.cachedDesc(context, loginData.getDesc());
                        Config.cachedSex(context, loginData.getSex());
                        app.setIsLogin(true);

                        if (!Config.getCachedIscustomer(context)) {
                            cdefault(app);
                        }
                        new GetPushagrs(context).refreshPushagrs();

                    } else {
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


    public void cdefault(final BaseApplication app){

        RequestParams params = new RequestParams();
        params.put("uid", Config.getCachedUid(context));
        MzbHttpClient.ClientTokenPost(context,MzbUrlFactory.BASE_URL + MzbUrlFactory.cdefault, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        CdefaultData cdefaultData = gson.fromJson(obj.getJSONObject("data").toString(),CdefaultData.class);
                        branch mbranch = cdefaultData.getBranch();
                        if (mbranch != null){
                                Config.cachedBrandid(context, mbranch.getBranid());
                                Config.cachedPpid(context, mbranch.getPpid());
                                Config.cachedCustid(context, mbranch.getCustid());
                        }
                        cate mcate = cdefaultData.getCate();
                        if (mcate != null){
                            Config.cachedCateid(context, mcate.getCateid());
                            Config.cachedCname(context, mcate.getName());
                        }
                        app.setCdefaultData(cdefaultData);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
            }
        });
    }
}
