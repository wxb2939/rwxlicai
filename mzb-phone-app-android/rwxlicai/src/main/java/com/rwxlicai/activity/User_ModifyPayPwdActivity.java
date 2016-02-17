package com.rwxlicai.activity;

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
import com.rwxlicai.utils.DesEncrypt;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/16.
 */
public class User_ModifyPayPwdActivity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.new_pwd)
    EditText new_pwd;
    @InjectView(R.id.sure_pwd)
    EditText sure_pwd;
    @InjectView(R.id.next)
    Button next;

    @Override
    protected void initView() {

        setContentView(R.layout.user_modifypwd_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("修改交易密码").setLeftImage(R.mipmap.top_view_back);
        next.setText("提交修改交易密码");
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
                    showToast("请输旧密码！");
                    return;
                }
                if (TextUtils.isEmpty(new_pwd.getText().toString())) {
                    showToast("请输入新密码！");
                    return;
                }
                if (TextUtils.isEmpty(sure_pwd.getText().toString())) {
                    showToast("请输入确认密码！");
                    return;
                }
                hideKeyboard(view);
                updatePayPass();
                break;
            default:
                break;
        }
    }

    public void updatePayPass() {
        RequestParams params = new RequestParams();
        try {
            params.put("payOldPassword", DesEncrypt.DesUtil(pwd.getText().toString()));
            params.put("payPassword", DesEncrypt.DesUtil(new_pwd.getText().toString()));
            params.put("payPrePassword", DesEncrypt.DesUtil(sure_pwd.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RwxHttpClient.ClientTokenPost(User_ModifyPayPwdActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.updatepaypass, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast(obj.getString("message"));
                    } else {
                        showToast(obj.getString("message"));
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
