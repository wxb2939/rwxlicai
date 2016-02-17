package com.rwxlicai.activity;

import android.view.View;
import android.widget.ImageView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class Profile_InfoManager_Activity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @Override
    protected void initView() {
        setContentView(R.layout.profile_redpacketsmanager_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("系统消息").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
