package com.xem.mzbemployeeapp.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.entity.EmployeData;
import com.xem.mzbemployeeapp.entity.Employes;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.GetPushagrs;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class A0_LoginAty extends MzbActivity {
    //控件
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
    @InjectView(R.id.circle_img)
    ImageView circle_img;
//    @InjectView(R.id.btn_exp)
//    Button btn_exp;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_a0__login_aty2);
    ButterKnife.inject(this);
    new TitleBuilder(this).setTitleText("用户登陆").setRightText("注册").setLeftImage(R.mipmap.top_view_back);
}
//    @OnClick({R.id.loginin, R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.btn_forget_pwd,
//            R.id.all_loginin, R.id.btn_exp})
    @OnClick({R.id.loginin, R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.btn_forget_pwd,
            R.id.all_loginin})
    public void onDo(View v) {

        switch (v.getId()) {
            //注册
            case R.id.titlebar_tv_right:
                intent2Activity(A0_RegisterActivity.class);
                break;
            //后退
            case R.id.titlebar_iv_left:
                intent2Activity(LoginOrRegActivity.class);
                finish();
                break;
            //登录
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
            case R.id.all_loginin:{
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            }
            case R.id.btn_forget_pwd:
                intent2Aty(A0_FindPwdActivity.class);
                finish();
                break;
        }
    }

    public void DoLogin(final String mobile, String password) {
        RequestParams params1 = new RequestParams();
//        params1.put("type", "B");
        params1.put("mobile", mobile);
        params1.put("password", password);
        RequestUtils.ClientTokenPost(A0_LoginAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Employes loginData = gson.fromJson(obj.getJSONObject("data").toString(), Employes.class);
                        Config.cachedUid(A0_LoginAty.this, loginData.getUid());
                        Config.cachedToken(A0_LoginAty.this, loginData.getToken());
                        Config.cachedIsemploye(A0_LoginAty.this, loginData.getIsemploye());
                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedPortrait(A0_LoginAty.this, employeData.getPortrait());
                            Config.cachedName(A0_LoginAty.this, employeData.getName());
                            Config.cachedSex(A0_LoginAty.this, employeData.sex);
                            Config.cachedBrandAccid(A0_LoginAty.this, employeData.getAccid());
                            Config.cachedBrandEmpid(A0_LoginAty.this, employeData.getEmpid());
                            Config.cachedBrandCodes(A0_LoginAty.this, employeData.getRoles());
                            Config.cachedBrandRights(A0_LoginAty.this, employeData.getRights());
                            Config.cachedBrand(A0_LoginAty.this, employeData.bname);
                            Config.cachedFirstBrandId(A0_LoginAty.this,employeData.firstbranid);
                        }
//                        BranchData branchData = loginData.getBranch();
                        application.setIsLogin(true);
//                        if (branchData != null) {
//                            application.setBranchData(branchData);
//                        }
                        new GetPushagrs(A0_LoginAty.this).refreshPushagrs();
                        JPushInterface.resumePush(getApplicationContext());
                        intent2Activity(HomeActivity.class);
                        finish();
                        //判断员工是否进行了绑定
                        //绑定,进入员工页面
//                        finish();
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

    protected void intent2Activity(Class<? extends Activity> tarActivity){
        Intent intent = new Intent(this,tarActivity);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        imageLoader.displayImage(Config.getCachedPortrait(this), circle_img);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            intent2Activity(LoginOrRegActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
