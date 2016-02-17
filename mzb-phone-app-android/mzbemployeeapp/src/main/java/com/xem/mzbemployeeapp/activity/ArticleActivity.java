package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ArticleActivity extends MzbActivity {
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("用户条款").setLeftImage(R.mipmap.top_view_back);
        //获取raw目录首先要获得资源(getResources()),然后在打开指定的资源(根据id)
        InputStream in=getResources().openRawResource(R.raw.employ);

        try {
            byte[] buffer=new byte[in.available()];//根据文件可读字节数创建用于临时存放数据的字节数组,请注意这方式只适用于小文件
            in.read(buffer);
            String msg=new String(buffer);
            //将内容放入到控件中
            text.setText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }
}
