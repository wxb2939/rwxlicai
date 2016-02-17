package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.com.SmsObserver;
import com.xem.mzbemployeeapp.net.MzbHttpClient;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.IsMobileNo;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class A0_FindPwdActivity extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.auth_code)
    EditText auth_code;
    @InjectView(R.id.btnGetCode)
    Button btn_get_code;
//    @InjectView(R.id.agree_protcol)
//    Button agree_protcol;
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
                btn_get_code.setText(code);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {

        setContentView(R.layout.a0_register_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("找回密码").setLeftImage(R.mipmap.top_view_back);
        loginin.setText("下一步");
        mObserver = new SmsObserver(A0_FindPwdActivity.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        //把observer注册到"content://sms"
        getContentResolver().registerContentObserver(uri,true,mObserver);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent2Aty(A0_LoginAty.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.titlebar_iv_left,R.id.loginin,R.id.btnGetCode})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
//                finish();
                intent2Aty(A0_LoginAty.class);
                finish();
                finish();
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

            case R.id.loginin:
                if (TextUtils.isEmpty(phonenum.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (TextUtils.isEmpty(auth_code.getText().toString())) {
                    showToast("请输入验证码！");
                    return;
                }
                hideKeyboard(view);
                authcodeTest();
                break;
            default:
                break;
        }
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


    private void authcode(){
        RequestParams params = new RequestParams();
        params.put("type", "0");
        params.put("mobile", phonenum.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_AUTHCODE, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

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

    private void authcodeTest(){

        RequestParams params = new RequestParams();
        params.put("type", 1+"");
        params.put("mobile", phonenum.getText().toString());
        params.put("authcode", auth_code.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_AUTHCODE, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0){
                        Intent intent = new Intent();
                        intent.setClass(A0_FindPwdActivity.this, A0_ResetPwdActivity.class);
                        intent.putExtra("mobile", phonenum.getText().toString());
                        intent.putExtra("authcode", auth_code.getText().toString());
                        startActivity(intent);
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
                showToast("");
            }
        });
    }
}
