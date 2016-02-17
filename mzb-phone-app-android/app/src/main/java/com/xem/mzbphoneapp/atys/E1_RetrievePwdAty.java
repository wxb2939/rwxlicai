package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.LoginData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/15.
 */
public class E1_RetrievePwdAty extends MzbActivity {

    private TimeCount time;

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.all_retrieve)
    View allRetrieve;
    @InjectView(R.id.retrieve_etPhoneNum)
    EditText retEtPhoneNum;
    @InjectView(R.id.retrieve_etPwd)
    EditText retEtPwd;
    @InjectView(R.id.retrieve_etCode)
    EditText retEtCode;
    @InjectView(R.id.retrieve_btnGetCode)
    Button retBtnGetCode;
    @InjectView(R.id.retrieve_btnLogin)
    Button retBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_pwd_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("找回密码").setLeftImage(R.mipmap.top_view_back);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }


    @OnClick({R.id.titlebar_iv_left, R.id.retrieve_btnLogin, R.id.retrieve_btnGetCode,R.id.all_retrieve})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.all_retrieve:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.retrieve_btnGetCode:
                if (retEtPhoneNum.getText().toString().trim().equals("")) {
                    showToast("输入电话号码");
                    return;
                }
                hideKeyboard(v);
                time.start();//开始计时
                asynchttpPost();
                break;

            case R.id.retrieve_btnLogin:
                if (retEtPhoneNum.getText().toString().trim().equals("")) {
                    showToast("请输入电话号码");
                    return;
                }
                if (retEtCode.getText().toString().trim().equals("")) {
                    showToast("请输入验证码");
                    return;
                }
                asynchttpPostNext();

        }

    }

    private void asynchttpPostNext() {
        RequestParams params = new RequestParams();
        params.put("type", "0");
        params.put("mobile", retEtPhoneNum.getText().toString());
        params.put("password", retEtPwd.getText().toString());
        params.put("authcode", retEtCode.getText().toString());

        RequestUtils.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_FPASSWORD, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        DoLogin(retEtPhoneNum.getText().toString(), retEtPwd.getText().toString());
//                        new CommLogin(E1_RetrievePwdAty.this).Login(retEtPhoneNum.getText().toString(), retEtPwd.getText().toString(), (MzbApplication) getApplication());
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
                pd.dismiss();
                showToast("请求失败");
            }
        });
    }

    public void DoLogin(String mobile, String password) {
        RequestParams params1 = new RequestParams();
        params1.put("mobile", mobile);
        params1.put("password", password);

        RequestUtils.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_LOGIN, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
//                        JSONObject data = obj.getJSONObject("data");
                        LoginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), LoginData.class);
                        Config.cachedUid(E1_RetrievePwdAty.this, loginData.getUid());
                        Config.cachedToken(E1_RetrievePwdAty.this, loginData.getToken());
                        Config.cachedName(E1_RetrievePwdAty.this, loginData.getName());
                        Config.cachedRoles(E1_RetrievePwdAty.this, loginData.getPortrait());
                        finish();
                    } else {
                        showToast(obj.getString("message"));
                        Toast.makeText(E1_RetrievePwdAty.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
                showToast("请求失败");
            }
        });
    }


    private void asynchttpPost() {
        RequestParams params = new RequestParams();
        params.put("type", "0");
        params.put("mobile", retEtPhoneNum.getText().toString());


        RequestUtils.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_AUTHCODE, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                showToast("发送成功");
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            retBtnGetCode.setClickable(false);
            retBtnGetCode.setText("" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            retBtnGetCode.setText("重新验证");
            retBtnGetCode.setClickable(true);

        }
    }

}
