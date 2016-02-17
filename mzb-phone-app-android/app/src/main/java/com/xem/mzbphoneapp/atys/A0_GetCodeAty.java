package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.com.SmsObserver;
import com.xem.mzbphoneapp.entity.LoginData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.IsMobileNo;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 15/5/28.
 */
public class A0_GetCodeAty extends MzbActivity {

    @InjectView(R.id.etPhoneNum)
    EditText etPhone;
    @InjectView(R.id.etCode)
    EditText etCode;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btnGetCode)
    Button checking;

    public static final int MSG_RECEIVED_CODE = 1;
    private TimeCount time;
    private SmsObserver mObserver;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_RECEIVED_CODE){
                String code = (String) msg.obj;
                //update the ui
                etCode.setText(code);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_code_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("注册").setLeftImage(R.mipmap.top_view_back).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mObserver = new SmsObserver(A0_GetCodeAty.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        //把observer注册到"content://sms"
        getContentResolver().registerContentObserver(uri,true,mObserver);



        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        checking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (IsMobileNo.isMobile(etPhone.getText().toString())){
                    hideKeyboard(v);
                    time.start();//开始计时
                    asynchttpPost();
                }else {
                    showToast("请输入正确的电话号码！谢谢");
                }
            }
        });
        findViewById(R.id.all_getcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText())) {
                    showToast(getResources().getString(R.string.phone_num_can_not_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText())) {
                    showToast(getResources().getString(R.string.code_can_not_be_empty));
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    showToast(getResources().getString(R.string.message_content_can_not_be_empty));
                    return;
                }
                asynchttpPostLogin();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void asynchttpPostLogin() {
        RequestParams params = new RequestParams();
        params.put("mobile", etPhone.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("authcode", etCode.getText().toString());
        RequestUtils.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_REGISTER, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0){
                        DoLogin(etPhone.getText().toString(), etPassword.getText().toString());
                        finish();
                    }
                    else {
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



    private void asynchttpPost() {

        RequestParams params = new RequestParams();
        params.put("type", "0");
        params.put("mobile", etPhone.getText().toString());



        RequestUtils.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_AUTHCODE, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
//                Toast.makeText(A0_GetCodeAty.this, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
//                Toast.makeText(A0_GetCodeAty.this, "请求失败", Toast.LENGTH_LONG).show();
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

                        LoginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), LoginData.class);

                        Config.cachedUid(A0_GetCodeAty.this, loginData.getUid());
                        Config.cachedToken(A0_GetCodeAty.this, loginData.getToken());
                        Config.cachedName(A0_GetCodeAty.this, loginData.getName());
                        Config.cachedRoles(A0_GetCodeAty.this, loginData.getPortrait());
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
                showToast(getResources().getString(R.string.network_error));
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
            checking.setClickable(false);
            checking.setText("" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            checking.setText("重新验证");
            checking.setClickable(true);
        }
    }
}