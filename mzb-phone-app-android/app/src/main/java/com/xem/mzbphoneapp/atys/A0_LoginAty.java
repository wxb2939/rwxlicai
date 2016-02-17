package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.EmployeData;
import com.xem.mzbphoneapp.entity.LoginData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.GetPushagrs;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/6/6.
 */

public class A0_LoginAty extends MzbActivity {

    @InjectView(R.id.all_loginin)
    View allLoginin;
    @InjectView(R.id.titlebar_tv_right)
    TextView more;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e0_phonenum)
    EditText phonenum;
    @InjectView(R.id.e0_password)
    EditText password;
    @InjectView(R.id.loginin)
    Button loginin;
    @InjectView(R.id.btn_forget_pwd)
    Button btnForgetPwd;
    @InjectView(R.id.btn_exp)
    Button btn_exp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_loginin);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("用户登陆").setRightText("注册").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.loginin, R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.btn_forget_pwd,
            R.id.all_loginin, R.id.btn_exp})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_tv_right:
                intent2Aty(A0_GetCodeAty.class);
                finish();
                break;
            //登录体验
            case R.id.btn_exp:
                Config.cachedIsexp(A0_LoginAty.this, "isexp");
                intent2Aty(A0_ExpAty.class);
                finish();
                break;
            case R.id.all_loginin:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.loginin:
                if (phonenum.getText().toString().trim().equals("")) {
                    showToast("输入电话号码");
                    return;
                } else if (password.getText().toString().trim().equals("")) {
                    showToast("输入密码");
                    return;
                }
                Config.cachedPhone(A0_LoginAty.this, phonenum.getText().toString());
                Config.cachedPassword(A0_LoginAty.this, password.getText().toString());
                hideKeyboard(v);
                if (checkNetAddToast()) {
                    DoLogin(phonenum.getText().toString(), password.getText().toString());
                }

                break;
            case R.id.btn_forget_pwd:
                intent2Aty(E1_RetrievePwdAty.class);
                finish();
                break;
        }
    }


    public void DoLogin(final String mobile, String password) {
        RequestParams params1 = new RequestParams();
        params1.put("type", "B");
        params1.put("mobile", mobile);
        params1.put("password", password);
        RequestUtils.ClientTokenPost(A0_LoginAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        LoginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), LoginData.class);
                        Config.cachedUid(A0_LoginAty.this, loginData.getUid());
                        Config.cachedToken(A0_LoginAty.this, loginData.getToken());
                        Config.cachedName(A0_LoginAty.this, loginData.getName());
                        Config.cachedPortrait(A0_LoginAty.this, loginData.getPortrait());
                        Config.cachedIsemploye(A0_LoginAty.this, loginData.getIsemploye());
                        Config.cachedIsmember(A0_LoginAty.this, loginData.getIsmember());
                        Config.cachedScore(A0_LoginAty.this, loginData.getScore());

                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedBrandAccid(A0_LoginAty.this, employeData.getAccid());
                            Config.cachedBrandEmpid(A0_LoginAty.this, employeData.getEmpid());
                            Config.cachedBrandCodes(A0_LoginAty.this, employeData.getRoles());
                            Config.cachedBrandRights(A0_LoginAty.this, employeData.getRights());
                        }
                        BranchData branchData = loginData.getBranch();
                        application.setIsLogin(true);
                        if (branchData != null) {
                            application.setBranchData(branchData);
                        }
                        new GetPushagrs(A0_LoginAty.this).refreshPushagrs();
                        JPushInterface.resumePush(getApplicationContext());
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
                showToast("请求失败,请确认网络连接!");
            }
        });
    }
}
