package com.rwxlicai.other;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rwxlicai.R;
import com.rwxlicai.activity.MainActivity;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.other.widget.GestureContentView;
import com.rwxlicai.other.widget.GestureDrawline;
import com.rwxlicai.utils.Config;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 16/1/4.
 */
public class GestureVerifyActivity extends RwxActivity {

    @InjectView(R.id.text_tip)
    TextView mTextTip;
    @InjectView(R.id.gesture_container)
    FrameLayout mGestureContainer;
    @InjectView(R.id.text_phone_number)
    TextView text_phone_number;


    private String mParamPhoneNumber;
    private long mExitTime = 0;
    private int mParamIntentCode;
    /** 手机号码*/
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /** 意图 */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
    private GestureContentView mGestureContentView;

    private Boolean flag = false;

    @Override
    protected void initView() {
        setContentView(R.layout.gesture_verify_activity);
        ButterKnife.inject(this);
        text_phone_number.setText(Config.getCachedPhone(GestureVerifyActivity.this));
        flag = getIntent().getBooleanExtra("flag",false);

        // 初始化一个显示各个点的viewGroup
//        mGestureContentView = new GestureContentView(this, true, "1235789",
        mGestureContentView = new GestureContentView(this, true, Config.getGpwd(GestureVerifyActivity.this),
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);
//                        showToast("密码正确");
                        if (flag){
                            startActivity(new Intent(GestureVerifyActivity.this, MainActivity.class));
                        }else {
                            GestureVerifyActivity.this.finish();
                        }
                    }

                    @Override
                    public void checkedFail() {
                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html
                                .fromHtml("<font color='#c70c1e'>密码错误</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    @Override
    protected void initData() {

    }


    private void ObtainExtraData() {
        mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
        mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
    }
}
