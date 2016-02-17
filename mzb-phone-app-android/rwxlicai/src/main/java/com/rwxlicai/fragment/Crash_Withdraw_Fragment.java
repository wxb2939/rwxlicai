package com.rwxlicai.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.activity.User_realIdentity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.IsMobileNo;
import com.rwxlicai.utils.SmsObserver;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class Crash_Withdraw_Fragment extends Fragment {
    @InjectView(R.id.draw_num)
    EditText cashTotal;
    @InjectView(R.id.auth_code)
    EditText auth_code;
    @InjectView(R.id.next)
    Button next;
    @InjectView(R.id.btnGetCode)
    Button btnGetCode;
    @InjectView(R.id.userPhone)
    EditText userPhone;
    @InjectView(R.id.available)
    TextView available;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crash_withdraw_fragment, null);
        ButterKnife.inject(this, view);
        Bundle bundle1 = getArguments();
        available.setText(Config.getAvailableMoney(getActivity()));

        mObserver = new SmsObserver(getActivity(),mHandler);
        Uri uri = Uri.parse("content://sms");
        getActivity().getContentResolver().registerContentObserver(uri, true, mObserver);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        return view;
    }

    @OnClick({R.id.next,R.id.btnGetCode})
    public void clickAction(View v) {
        switch (v.getId()){
            case R.id.next:
                if (TextUtils.isEmpty(cashTotal.getText().toString())) {
                    Toast.makeText(getActivity(),"请输入提现金额！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(auth_code.getText().toString())) {
                    Toast.makeText(getActivity(),"请输入提现密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                applyWithdraw();
                break;
            case R.id.btnGetCode:
                if (TextUtils.isEmpty(userPhone.getText().toString())) {
                    Toast.makeText(getActivity(),"请输入电话号码！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (IsMobileNo.isMobile(userPhone.getText().toString())){
                    time.start();//开始计时
                    authcode();
                }else {
                    Toast.makeText(getActivity(),"请输入正确的电话号码！谢谢",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    private void authcode(){
        RequestParams params = new RequestParams();
//        params.put("userPhone", phonenum.getText().toString());
        params.put("userAccount", userPhone.getText().toString());

        RwxHttpClient.ClientPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.authcode, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("code").equals("0")) {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(),"确认网络连接",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void applyWithdraw(){
        RequestParams params = new RequestParams();
        params.put("cashTotal", cashTotal.getText().toString());
        params.put("smsCode", auth_code.getText().toString());
        params.put("userPhone", userPhone.getText().toString());
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.applyWithdraw, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_LONG).show();
                    } else {
//                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), User_realIdentity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), "请确认网络连接!", Toast.LENGTH_LONG).show();
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
