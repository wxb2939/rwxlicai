package com.rwxlicai.activity;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.DesEncrypt;
import com.rwxlicai.utils.IsMobileNo;
import com.rwxlicai.utils.SmsObserver;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/13.
 */
public class A0_RegisterActivity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.auth_code)
    EditText auth_code;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.btnGetCode)
    Button btn_get_code;
    @InjectView(R.id.other)
    EditText other;

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
                btn_get_code.setText(code);
            }
        }
    };



    @Override
    protected void initView() {
        setContentView(R.layout.a0_register_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("注册新用户").setLeftImage(R.mipmap.top_view_back);

        mObserver = new SmsObserver(A0_RegisterActivity.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        //把observer注册到"content://sms"
        getContentResolver().registerContentObserver(uri, true, mObserver);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.titlebar_iv_left,R.id.loginin,R.id.btnGetCode,R.id.all_loginin})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.all_loginin:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.loginin:
                if (TextUtils.isEmpty(phonenum.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (TextUtils.isEmpty(auth_code.getText().toString())) {
                    showToast("请输入验证码！");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())){
                    showToast("请输入密码");
                }
                hideKeyboard(view);
                useResister();
                break;
            case R.id.btnGetCode:
                if (TextUtils.isEmpty(phonenum.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (IsMobileNo.isMobile(phonenum.getText().toString())){
                    hideKeyboard(view);
                    time.start();//开始计时
                    authcode();
                }else {
                    showToast("请输入正确的电话号码！谢谢");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void authcode(){
        RequestParams params = new RequestParams();
        params.put("userPhone", phonenum.getText().toString());

        RwxHttpClient.ClientPost(A0_RegisterActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.authcode, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("code").equals("0")) {
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
            }
        });
    }


    private void useResister(){
        RequestParams params = new RequestParams();
        params.put("userAccount", phonenum.getText().toString());
        try {
            params.put("userPassword", DesEncrypt.DesUtil(password.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        params.put("userPassword",password.getText().toString());
        params.put("userPhone",phonenum.getText().toString());
        params.put("smsCode",auth_code.getText().toString());
        params.put("inviteUserAccount",other.getText().toString());

        RwxHttpClient.ClientPost(A0_RegisterActivity.this,RwxUrlFactory.BASE_URL + RwxUrlFactory.register, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast(obj.getString("message"));
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



    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_get_code.setClickable(false);
            btn_get_code.setText("" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btn_get_code.setText("重新验证");
            btn_get_code.setClickable(true);
        }
    }
}
