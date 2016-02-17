package com.xem.mzbphoneapp.atys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.xem.mzbphoneapp.R;

/**
 * Created by xuebing on 15/6/5.
 */

public class StartAty extends Activity {
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View startView = View.inflate(this, R.layout.splash, null);
        setContentView(startView);

        context = this;
        //渐变
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        startView.setAnimation(aa);
        aa.setAnimationListener(
                new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        redirectto();
                    }
                }
        );
    }

    private void redirectto() {
        Intent intent = new Intent(this, MainTabAty.class);
        startActivity(intent);
        finish();
    }
}