package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/29.
 */
public class E1_AboutUsAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.panben)
    TextView banben;
    String localVersion = "1.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        ButterKnife.inject(this);
        Context context = E1_AboutUsAty.this;
        try {
            localVersion = getPackageManager().getPackageInfo("com.xem.mzbphoneapp", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new TitleBuilder(E1_AboutUsAty.this).setTitleText("关于我们").setLeftImage(R.mipmap.top_view_back);
        banben.setText("V"+ localVersion);
    }

    @OnClick({ R.id.titlebar_iv_left})
    public void Todo(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
