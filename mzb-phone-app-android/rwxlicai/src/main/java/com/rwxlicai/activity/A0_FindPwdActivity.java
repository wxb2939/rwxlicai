package com.rwxlicai.activity;

import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
public class A0_FindPwdActivity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.auth_code)
    EditText auth_code;
    @InjectView(R.id.btnGetCode)
    Button btnGetCode;
    @InjectView(R.id.new_pwd)
    EditText new_pwd;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.loginin)
    Button loginin;

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
                btnGetCode.setText(code);
            }
        }
    };


    @Override
    protected void initView() {
        setContentView(R.layout.a0_findpwd_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("找回登陆密码").setLeftImage(R.mipmap.top_view_back);
        mObserver = new SmsObserver(A0_FindPwdActivity.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        //把observer注册到"content://sms"
        getContentResolver().registerContentObserver(uri, true, mObserver);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.loginin,R.id.btnGetCode})
    public void clickAction(View view){
        switch (view.getId()){
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
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    showToast("请输入新密码！");
                    return;
                }
                if (TextUtils.isEmpty(new_pwd.getText().toString())) {
                    showToast("再一次输入新密码！");
                    return;
                }


                hideKeyboard(view);
                try {
                    forGetPass();

                } catch (Exception e) {
                    e.printStackTrace();
                }
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


    private void forGetPass() throws Exception{
        RequestParams params = new RequestParams();
        params.put("userAccount", phonenum.getText().toString());
        params.put("userPassword", DesEncrypt.DesUtil(pwd.getText().toString()));
        params.put("userPrePassword",DesEncrypt.DesUtil(new_pwd.getText().toString()));
        params.put("smsCode", auth_code.getText().toString());

        RwxHttpClient.ClientPost(A0_FindPwdActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.forgetpass, params, new NetCallBack() {
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




    private void authcode(){
        RequestParams params = new RequestParams();
//        params.put("userPhone", phonenum.getText().toString());
        params.put("userAccount", phonenum.getText().toString());

        RwxHttpClient.ClientPost(A0_FindPwdActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.authcode, params, new NetCallBack() {
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
                showToast("请确认网络连接");
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
            btnGetCode.setClickable(false);
            btnGetCode.setText("" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("重新验证");
            btnGetCode.setClickable(true);
        }
    }
}
