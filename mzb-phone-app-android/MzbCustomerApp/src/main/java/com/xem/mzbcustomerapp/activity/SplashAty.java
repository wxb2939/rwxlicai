package com.xem.mzbcustomerapp.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.utils.CommLogin;
import com.xem.mzbcustomerapp.utils.Config;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xuebing on 15/10/30.
 */
public class SplashAty extends BaseActivity {
    private AlphaAnimation start_anima;
    View view;
    private boolean isFirstIn = false;

    @Override
    protected void initView() {

        view = View.inflate(this, R.layout.splash_aty, null);
        new CommLogin(SplashAty.this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
        setContentView(view);
        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
//        ShareSDK.initSDK(this);

    }

    @Override
    protected void initData() {

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

    private void redirectTo() {
        if (isFirstIn){
            intent2Aty(HomeActivity.class);
            finish();
        }else {
            intent2Aty(HomeActivity.class);
            finish();
        }
//        intent2Aty(MainTabAty.class);
//        finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    //    protected void onPause() {
//        super.onPause();
//
//    }
}
