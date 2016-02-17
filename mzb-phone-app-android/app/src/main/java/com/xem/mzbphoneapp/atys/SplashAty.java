package com.xem.mzbphoneapp.atys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.CommLogin;
import com.xem.mzbphoneapp.utils.Config;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/7/16.
 */
public class SplashAty extends MzbActivity {


    private AlphaAnimation start_anima;
    View view;
    private boolean isFirstIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.splash_aty, null);
        new CommLogin(SplashAty.this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
        setContentView(view);
        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

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
        if (isFirstIn){
            intent2Aty(SwitchActivity.class);
            finish();
        }else {
            intent2Aty(MainTabAty.class);
            finish();
        }
//        intent2Aty(MainTabAty.class);
//        finish();

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        JPushInterface.stopPush(this);
//    }

    /*private static final int WHAT_INITENT2LOGIN = 1;
    private static final long SPLASH_DUR_TIME = 2000;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_INITENT2LOGIN:
                    intent2Aty(MainTabAty.class);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };*/


     /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_aty);
        if (Config.getCachedUid(this) != null){
            new CommLogin(SplashAty.this).Login(Config.getCachedPhone(this),Config.getCachedPassword(this),application);
            handler.sendEmptyMessageDelayed(WHAT_INITENT2LOGIN,SPLASH_DUR_TIME);
        }else {
            handler.sendEmptyMessageDelayed(WHAT_INITENT2LOGIN,SPLASH_DUR_TIME);
        }


    }*/


}
