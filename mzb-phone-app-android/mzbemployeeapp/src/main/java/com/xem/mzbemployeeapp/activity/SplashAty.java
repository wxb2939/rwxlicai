package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.utils.Config;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/7/16.
 */
public class SplashAty extends MzbActivity {


    private AlphaAnimation start_anima;
    View view;
    private boolean isFirstIn = false;
    private MzbApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.splash_aty, null);
//        new CommLogin(SplashAty.this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
        setContentView(view);
//        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
//        isFirstIn = preferences.getBoolean("isFirstIn", true);
        app = (MzbApplication) getApplication();
        initView();
        initData();
    }

    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(2000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                redirectTo();
            }
        });
    }
    private void initView() {

    }

    private void redirectTo() {
        if (Config.getCachedIsexp(this) != null ){
            //清空体验数据
            Config.cachedIsexp(this, null);
            Config.cachedUid(this, null);
            Config.cachedToken(this, null);
            Config.cachedPortrait(this, null);
            Config.cachedName(this, null);
            Config.cachedBrandAccid(this, null);
            Config.cachedBrandEmpid(this, null);
            Config.cachedBrandCodes(this, null);
            Config.cachedBrandRights(this, null);
        }
//        判断用户是否登录
        //登录了,进入首页
        if (Config.getCachedUid(this) != null) {
//            new CommLogin(this).FirstLogin(Config.getCachedPhone(this), Config.getCachedPassword(this), app);
            intent2Aty(HomeActivity.class);
            finish();
        }
        //没有登录，进入登录页面
        else{
//            showToast("请先登陆，绑定门店");
            intent2Aty(LoginOrRegActivity.class);
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

}
