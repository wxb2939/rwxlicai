package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.utils.CommLogin;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/7/8.
 */
public class NotificationAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.webView)
    WebView webView;

    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_aty);
        ButterKnife.inject(this);

        new TitleBuilder(this).setTitleText("美之伴").setLeftImage(R.mipmap.top_view_back);
        new CommLogin(NotificationAty.this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        Intent intent = getIntent();

        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            flag = intent.getStringExtra("flag");
            String content = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject object = new JSONObject(content);

                if (object != null) {
                    if (object.isNull("url")) {
                        Intent mIntent = new Intent(NotificationAty.this,E0_InfoAty.class);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mIntent.putExtra("flag",flag);
                        startActivity(mIntent);
                        finish();
                    } else {
                        String url = object.getString("url");
                        webView.loadUrl(url);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.titlebar_iv_left})
    public void Ondo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                Intent mIntent = new Intent(NotificationAty.this,E0_InfoAty.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("flag",1);
                startActivity(mIntent);
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
            Intent nIntent = new Intent(NotificationAty.this,E0_InfoAty.class);
            nIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            nIntent.putExtra("flag",1);
            startActivity(nIntent);
            finish();
            return true;
        }
        return true;
    }
}