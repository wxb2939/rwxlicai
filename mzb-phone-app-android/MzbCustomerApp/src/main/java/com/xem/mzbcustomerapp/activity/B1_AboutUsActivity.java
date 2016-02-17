package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class B1_AboutUsActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.clause)
    TextView clause;
    @InjectView(R.id.call_text)
    TextView call_text;
    @InjectView(R.id.inter_text)
    TextView inter_text;
    @InjectView(R.id.img_text)
    TextView img_text;
    @InjectView(R.id.version)
    TextView version;

    private String localVersion = "1.0";

    @Override
    protected void initView() {
        setContentView(R.layout.b1_aboutus_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("关于我们").setLeftImage(R.mipmap.top_view_back);
        clause.setText("<<用户条款>>");
        String str = "Copyright 图片 2015";
        SpannableString ss = new SpannableString(str);
        int index = ss.toString().indexOf("图片");
        Resources rs = getResources();
        Drawable d = rs.getDrawable(R.mipmap.mp);
        d.setBounds(0, 0, d.getIntrinsicWidth() * 5 / 4, d.getIntrinsicHeight() * 5 / 4);
        ss.setSpan(new ImageSpan(d), index, index + "图片".length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        img_text.setText(ss);
    }

    @Override
    protected void initData() {
        try {
            localVersion = getPackageManager().getPackageInfo("com.xem.mzbcustomerapp", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new TitleBuilder(B1_AboutUsActivity.this).setTitleText("关于我们").setLeftImage(R.mipmap.top_view_back);
        version.setText("V"+ localVersion);
    }

    @OnClick({R.id.titlebar_iv_left, R.id.clause, R.id.call_text, R.id.inter_text})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.clause:
                intent2Aty(ArticleActivity.class);
                break;
            case R.id.call_text:
                Uri uri = Uri.parse("tel:" + call_text.getText().toString());
                Intent it = new Intent();   //实例化Intent
                it.setAction(Intent.ACTION_CALL);   //指定Action
                it.setData(uri);   //设置数据
                startActivity(it);//启动Acitivity
                break;
            case R.id.inter_text:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://" + inter_text.getText().toString());
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }
}
//}
