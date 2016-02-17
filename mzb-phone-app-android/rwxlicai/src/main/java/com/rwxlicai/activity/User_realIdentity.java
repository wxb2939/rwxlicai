package com.rwxlicai.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.view.TitleBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/16.
 */
public class User_realIdentity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.new_pwd)
    EditText new_pwd;
    @InjectView(R.id.next)
    Button next;

    @Override
    protected void initView() {
        setContentView(R.layout.user_realidentity_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("实名认证").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.titlebar_iv_left, R.id.next})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    showToast("请输入真实姓名！");
                    return;
                }
                if (TextUtils.isEmpty(new_pwd.getText().toString())) {
                    showToast("请输入身份证号！");
                    return;
                }
                hideKeyboard(view);
                realIdentity();
                break;
            default:
                break;
        }
    }

    public void realIdentity() {

        RequestParams params = new RequestParams();
        try {
            params.put("userRealname", pwd.getText().toString());
            params.put("cardNumber", new_pwd.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RwxHttpClient.ClientTokenPost(User_realIdentity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.realIdentity, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast(obj.getString("message"));
                        Config.cachedRealNameState(User_realIdentity.this, "2");
                        Config.cachedAuth(User_realIdentity.this, false);
                        startActivity(new Intent(User_realIdentity.this, User_bankCardIdentityActivity.class));
                        finish();
                    } else {
                        showToast(obj.getString("message"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
