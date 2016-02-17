package com.rwxlicai.other;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.utils.Config;
import com.rwxlicai.view.SlideSwitch;
import com.rwxlicai.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/4.
 */
public class GestureActivity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.btn_verify_lockpattern)
    View btn_verify_lockpattern;
    @InjectView(R.id.wiperSwitch1)
    SlideSwitch wipersWwitch1;


    @OnClick({R.id.btn_verify_lockpattern, R.id.wiperSwitch1,R.id.titlebar_iv_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
//            case R.id.wiperSwitch1:
//                startSetLockPattern();
//                break;
            case R.id.btn_verify_lockpattern:
                startSetLockPattern();
                break;
            default:
                break;
        }
    }


    @Override
    protected void initView() {
        setContentView(R.layout.gesture_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("账户管理").setLeftImage(R.mipmap.top_view_back);
        wipersWwitch1.setChecked(Config.getGopen(GestureActivity.this));
        wipersWwitch1.setOnChangedListener(new SlideSwitch.OnChangedListener() {
            @Override
            public void OnChanged(SlideSwitch wiperSwitch, boolean checkState) {
                if (checkState){
                    Config.cachedGopen(GestureActivity.this,true);
                    startSetLockPattern();
                }else {
                    Config.cachedGopen(GestureActivity.this, false);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    private void startSetLockPattern() {
        Intent intent = new Intent(GestureActivity.this, GestureEditActivity.class);
        startActivity(intent);
    }

    private void startVerifyLockPattern() {
        Intent intent = new Intent(GestureActivity.this, GestureVerifyActivity.class);
        startActivity(intent);
    }
}
