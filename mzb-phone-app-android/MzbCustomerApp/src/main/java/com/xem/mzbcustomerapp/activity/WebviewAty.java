package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/30.
 */
public class WebviewAty extends BaseActivity{

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.webView)
    WebView myWebview;

    @Override
    protected void initView() {
        setContentView(R.layout.notification_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("美之伴").setLeftImage(R.mipmap.top_view_back);
        Intent intent = getIntent();

        if (null != intent) {
            Bundle bundle = getIntent().getExtras();

            String content = bundle.getString("extra");
            try {
                JSONObject object = new JSONObject(content);

                if (object.length() != 0) {
                    String url = object.getString("url");
                    myWebview.loadUrl(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //启用支持javascript
        WebSettings settings = myWebview.getSettings();
        settings.setJavaScriptEnabled(true);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left})
    public void Ondo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
//                intent2Aty(E0_InfoAty.class);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            intent2Aty(E0_InfoAty.class);
            finish();
            return true;
        }
        return true;
    }
}
