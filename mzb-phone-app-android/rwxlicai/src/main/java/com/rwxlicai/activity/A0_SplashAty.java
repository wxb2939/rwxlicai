package com.rwxlicai.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.other.GestureVerifyActivity;
import com.rwxlicai.utils.Config;

/**
 * Created by xuebing on 16/1/20.
 */
public class A0_SplashAty extends RwxActivity {

    private AlphaAnimation start_anima;
    View view;
    private boolean isFirstIn = false;

    @Override
    protected void initView() {
        view = View.inflate(this, R.layout.splash_activity, null);
        setContentView(view);
        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

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

    @Override
    protected void initData() {

    }


    private void redirectTo() {
        if (isFirstIn){
            intent2Aty(A0_SwitchActivity.class);
            finish();
        }else {
            if (Config.getGopen(A0_SplashAty.this)){
                Intent intent = new Intent(A0_SplashAty.this,GestureVerifyActivity.class);
                intent.putExtra("flag",true);
                startActivity(intent);
//                intent2Aty(GestureVerifyActivity.class);
                finish();
            }else {
                intent2Aty(MainActivity.class);
                finish();
            }
        }
    }

}
