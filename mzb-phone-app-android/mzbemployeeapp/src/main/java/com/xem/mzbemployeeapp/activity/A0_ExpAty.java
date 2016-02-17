package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.EmployeData;
import com.xem.mzbemployeeapp.entity.Employes;
import com.xem.mzbemployeeapp.entity.ExperEmploye;
import com.xem.mzbemployeeapp.entity.Experience;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.GetPushagrs;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/9/9.
 */
public class A0_ExpAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.lb_exp)
    View lb_exp;
    @InjectView(R.id.lb_good)
    ImageView lb_good;
    @InjectView(R.id.dz_exp)
    View dz_exp;
    @InjectView(R.id.dz_good)
    ImageView dz_good;
    @InjectView(R.id.gw_exp)
    View gw_exp;
    @InjectView(R.id.gw_good)
    ImageView gw_good;
    @InjectView(R.id.mls_exp)
    View mls_exp;
    @InjectView(R.id.mls_good)
    ImageView mls_good;

    public static String str;
    public static String position;
    public static String ppid;
    public static ExperEmploye employ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a0_exp_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("选择角色");
        if (LoginOrRegActivity.str != null){
            new TitleBuilder(this).setLeftImage(R.mipmap.top_view_back);
        }

//        MzbApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (application.getCurRoles()){
            case 1:
                lb_good.setVisibility(View.VISIBLE);
                break;
            case 2:
                dz_good.setVisibility(View.VISIBLE);
                break;
            case 3:
                gw_good.setVisibility(View.VISIBLE);
                break;
            case 4:
                mls_good.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.titlebar_iv_left,R.id.lb_exp,R.id.lb_good,
                R.id.dz_exp,R.id.dz_good,R.id.gw_exp,R.id.gw_good,R.id.mls_exp,R.id.mls_good,R.id.exit_btn})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                intent2Aty(LoginOrRegActivity.class);
                finish();
                break;
            case R.id.lb_exp:
                application.setCurRoles(1);
                DoLogin("101");
                str="101";
                Config.cachedIsexp(this, "isexp");
                break;
            case R.id.dz_exp:
                application.setCurRoles(2);
                Config.cachedIsexp(this, "isexp");
                DoLogin("112");
                str="112";
                break;
            case R.id.gw_exp:
                application.setCurRoles(3);
                DoLogin("114");
                str="114";
                Config.cachedIsexp(this, "isexp");
                break;
            case R.id.mls_exp:
                application.setCurRoles(4);
                Config.cachedIsexp(this, "isexp");
                DoLogin("115");
                str="115";
                break;
            //退出登录体验
            case R.id.exit_btn:
//                清空体验标记
                Config.cachedIsexp(this, null);
////              清空体验缓存数据
                Config.cachedUid(this, null);
                Config.cachedToken(this, null);
                Config.cachedPortrait(this, null);
                Config.cachedName(this, null);
                Config.cachedBrandAccid(this, null);
                Config.cachedBrandEmpid(this, null);
                Config.cachedBrandCodes(this, null);
                Config.cachedBrandRights(this, null);
                //去选择页面
                intent2Aty(LoginOrRegActivity.class);
                finish();
                break;
            default:
                break;
        }
    }


    public void DoLogin( String role) {
//        Log.v("tag",role);
        RequestParams params1 = new RequestParams();
//        params1.put("type", "B");
        params1.put("role", role);
        RequestUtils.ClientTokenPost(A0_ExpAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_DEMOLOGIN, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
//                        Log.v("tag",);
//                        Log.v("tag",obj.getJSONObject("data").toString());
                        Experience loginData = gson.fromJson(obj.getJSONObject("data").toString(), Experience.class);
                        Employes login = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
                        EmployeData employeData = login.getEmploye();
                        employ=loginData.getEmploye();
                        Config.cachedUid(A0_ExpAty.this, login.getUid());
                        Config.cachedToken(A0_ExpAty.this, login.getToken());
                        Config.cachedIsemploye(A0_ExpAty.this, login.getIsemploye());
                        if (employeData != null) {
                            Config.cachedPortrait(A0_ExpAty.this, employeData.getPortrait());
                            Config.cachedName(A0_ExpAty.this, employeData.getName());
                            Config.cachedBrandAccid(A0_ExpAty.this, employeData.getAccid());
                            Config.cachedBrandEmpid(A0_ExpAty.this, employeData.getEmpid());
                            Config.cachedBrandCodes(A0_ExpAty.this, employeData.getRoles());
                            Config.cachedBrandRights(A0_ExpAty.this, employeData.getRights());
                        }
                        application.setIsLogin(true);
                        new GetPushagrs(A0_ExpAty.this).refreshPushagrs();
                        JPushInterface.resumePush(getApplicationContext());
                        intent2Aty(ExperienceActivity.class);
                        finish();
                    } else {
//                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
//                pd.dismiss();
                showToast("请求失败,请确认网络连接!");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (LoginOrRegActivity.str != null){
                intent2Aty(LoginOrRegActivity.class);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
