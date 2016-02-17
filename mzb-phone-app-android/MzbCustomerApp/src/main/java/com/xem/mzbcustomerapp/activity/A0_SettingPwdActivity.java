package com.xem.mzbcustomerapp.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.CommLogin;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class A0_SettingPwdActivity extends BaseActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.setting_pwd)
    EditText setting_pwd;
    @InjectView(R.id.login_in)
    Button login_in;

    private String mobile;
    private String authcode;
//    private String password;

    @Override
    protected void initView() {

        setContentView(R.layout.a0_setting_pwd_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("设置密码").setLeftImage(R.mipmap.top_view_back);


    }

    @Override
    protected void initData() {
        mobile = getIntent().getStringExtra("mobile");
        authcode = getIntent().getStringExtra("authcode");
//        password = setting_pwd.getText().toString();
    }

    @OnClick({R.id.titlebar_iv_left,R.id.login_in})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.login_in:
                if (TextUtils.isEmpty(mobile)) {
                    showToast("请输入密码！");
                    return;
                }else {
                    register();
                }
                break;
            default:
                break;
        }
    }


    private void register(){
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("authcode", authcode);
        params.put("password",setting_pwd.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.register, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
//                        showToast(obj.getString("注册成功"));
                        showToast("注册成功");
                        new CommLogin(context).Login(mobile, setting_pwd.getText().toString(), application);
                        intent2Aty(HomeActivity.class);
                        finish();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast(getResources().getString(R.string.net_err));
            }
        });
    }
}
