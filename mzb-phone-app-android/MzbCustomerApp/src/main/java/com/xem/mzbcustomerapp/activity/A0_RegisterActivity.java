package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.IsMobileNo;
import com.xem.mzbcustomerapp.utils.SmsObserver;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class A0_RegisterActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.auth_code)
    EditText auth_code;
    @InjectView(R.id.btnGetCode)
    Button btn_get_code;
    @InjectView(R.id.agree_protcol)
    TextView agree_protcol;
    @InjectView(R.id.loginin)
    Button loginin;
    @InjectView(R.id.all_loginin)
    View all_loginin;

//    private String mobile;
//    private String authcode;


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
        String str="注册即表示您同意<<用户条款>>";
        SpannableString ss=new SpannableString(str);
        //我们要为点击返回添加动作，按下返回到前一个窗口
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                intent2Aty(ArticleActivity.class);
            }
        }, str.indexOf("<<用户条款>>"), str.indexOf("<<用户条款>>")+"<<用户条款>>".length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        agree_protcol.setText(ss);
        agree_protcol.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
//        mobile = phonenum.getText().toString();
//        authcode = auth_code.getText().toString();
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
                hideKeyboard(view);
                authcodeTest();
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
        params.put("type", "0");
        params.put("mobile", phonenum.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.authcode, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0){

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

            }
        });

    }

    private void authcodeTest(){

        RequestParams params = new RequestParams();
        params.put("type", 1+"");
        params.put("mobile", phonenum.getText().toString());
        params.put("authcode", auth_code.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.authcode, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0){
                        Intent intent = new Intent();
                        intent.setClass(A0_RegisterActivity.this, A0_SettingPwdActivity.class);
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
