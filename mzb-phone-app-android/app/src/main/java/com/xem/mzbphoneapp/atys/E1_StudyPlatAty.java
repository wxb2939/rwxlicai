package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/11.
 */
public class E1_StudyPlatAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_study_plat_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("学习平台").setLeftImage(R.mipmap.top_view_back);
    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
