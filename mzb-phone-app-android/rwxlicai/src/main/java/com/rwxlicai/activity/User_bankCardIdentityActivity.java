package com.rwxlicai.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.model.ALL_BANK;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.IsMobileNo;
import com.rwxlicai.utils.SmsObserver;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class User_bankCardIdentityActivity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.spinner1)
    Spinner spinner;
    @InjectView(R.id.bankAccount)
    EditText bankAccount;
    @InjectView(R.id.userPhone)
    EditText userPhone;
    @InjectView(R.id.smsCode)
    EditText smsCode;
    @InjectView(R.id.btnGetCode)
    Button btnGetCode;
    @InjectView(R.id.next)
    Button next;

    private List<ALL_BANK> data;
    private String bid = null;


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
        setContentView(R.layout.user_bankcardidentity_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("绑定银行卡").setLeftImage(R.mipmap.top_view_back);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    protected void initData() {
        List<String> strList = new ArrayList<>();
        data = new Select().from(ALL_BANK.class).execute();
        for(Iterator<ALL_BANK> it=data.iterator();it.hasNext();){
            String value=it.next().bankName;
            strList.add(value);
        }
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

//                String[] language = getResources().getStringArray(R.array.languages);
//                Toast.makeText(User_bankCardIdentityActivity.this, "你点击的是:" + data.get(pos).bankName, Toast.LENGTH_LONG).show();
                bid = data.get(pos).bid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    @OnClick({R.id.titlebar_iv_left, R.id.next,R.id.btnGetCode})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            case R.id.btnGetCode:
                if (TextUtils.isEmpty(userPhone.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (IsMobileNo.isMobile(userPhone.getText().toString())){
                    hideKeyboard(view);
                    time.start();//开始计时
                    authcode();
                }else {
                    showToast("请输入正确的电话号码！谢谢");
                }
                break;
            case R.id.next:
                if (TextUtils.isEmpty(bankAccount.getText().toString())) {
                    showToast("请输入银行卡号");
                    return;
                }
                if (TextUtils.isEmpty(smsCode.getText().toString())) {
                    showToast("请输入验证码！");
                    return;
                }
                hideKeyboard(view);
                bankCardIdentity();
                break;
            default:
                break;
        }
    }

    public void bankCardIdentity() {
        RequestParams params = new RequestParams();
        try {
//            params.put("bank_account", bankAccount.getText().toString());
            params.put("bankAccount", bankAccount.getText().toString());
            params.put("bankId", bid);
            params.put("userPhone", userPhone.getText().toString());
            params.put("smsCode", smsCode.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RwxHttpClient.ClientTokenPost(User_bankCardIdentityActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.bankCardIdentity, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast(obj.getString("message"));
                        Config.cachedBankState(User_bankCardIdentityActivity.this,"2");
                        Config.cachedAuth(User_bankCardIdentityActivity.this,true);
                        finish();
                    } else {
                        showToast(obj.getString("message"));
//                        finish();
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


    private void authcode(){
        RequestParams params = new RequestParams();
//        params.put("userPhone", phonenum.getText().toString());
        params.put("userAccount", userPhone.getText().toString());

        RwxHttpClient.ClientPost(User_bankCardIdentityActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.authcode, params, new NetCallBack() {
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
