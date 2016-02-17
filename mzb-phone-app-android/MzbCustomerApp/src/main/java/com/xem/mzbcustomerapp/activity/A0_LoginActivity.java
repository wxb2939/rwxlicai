package com.xem.mzbcustomerapp.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.base.BaseApplication;
import com.xem.mzbcustomerapp.entity.CdefaultData;
import com.xem.mzbcustomerapp.entity.CloginData;
import com.xem.mzbcustomerapp.entity.branch;
import com.xem.mzbcustomerapp.entity.cate;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.GetPushagrs;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class A0_LoginActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.titlebar_tv_right)
    TextView more;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.btn_forget_pwd)
    Button btn_forget_pwd;
    @InjectView(R.id.loginin)
    Button loginin;


    @Override
    protected void initView() {
        setContentView(R.layout.a0_login_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("登录美之伴").setLeftImage(R.mipmap.top_view_back).setRightText("注册");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.btn_forget_pwd, R.id.loginin})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            case R.id.titlebar_tv_right:
                intent2Aty(A0_RegisterActivity.class);
                break;

            case R.id.btn_forget_pwd:
                intent2Aty(A0_FindPwdActivity.class);
                break;

            case R.id.loginin:
                if (TextUtils.isEmpty(phonenum.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showToast("请输入密码！");
                    return;
                }
                hideKeyboard(view);
                Login(phonenum.getText().toString(), password.getText().toString(), application);
                break;
            default:
                break;
        }
    }


    public void Login(final String mobile, final String password, final BaseApplication app) {

        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.clogin, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        Config.cachedPhone(context, mobile);
                        Config.cachedPassword(context, password);
                        CloginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), CloginData.class);
                        Config.cachedUid(context, loginData.getUid().toString());
                        Config.cachedToken(context, loginData.getToken());
                        Config.cachedName(context, loginData.getName());
                        Config.cachedPortrait(context, loginData.getPortrait());
                        Config.cachedScore(context, loginData.getScore());
                        Config.cachedDesc(context, loginData.getDesc());
                        Config.cachedSex(context, loginData.getSex());
                        app.setIsLogin(true);
                        MobclickAgent.onProfileSignIn(Config.getCachedUid(A0_LoginActivity.this));
                        cdefault(app);
                        new GetPushagrs(context).refreshPushagrs();
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


    private void cdefault(final BaseApplication app) {

        RequestParams params = new RequestParams();
        params.put("uid", Config.getCachedUid(context));
        MzbHttpClient.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.cdefault, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        CdefaultData cdefaultData = gson.fromJson(obj.getJSONObject("data").toString(), CdefaultData.class);
                        branch mbranch = cdefaultData.getBranch();
                        if (mbranch != null) {
//                            if (Config.getCachedBrandid(context) == null){
                            Config.cachedBrandid(context, mbranch.getBranid());
//                            }
//                            if ( Config.getCachedPpid(context) == null){
                            Config.cachedPpid(context, mbranch.getPpid());
//                            }
//                            if ( Config.getCachedCustid(context) == null){
                            Config.cachedCustid(context, mbranch.getCustid());
//                            }
                        }
                        cate mcate = cdefaultData.getCate();
                        if (mcate != null) {
                            Config.cachedCateid(context, mcate.getCateid());
                            Config.cachedCname(context, mcate.getName());
                        }
                        app.setCdefaultData(cdefaultData);
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
            }
        });
    }

}
