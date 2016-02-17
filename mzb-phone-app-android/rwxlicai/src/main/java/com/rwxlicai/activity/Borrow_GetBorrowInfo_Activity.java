package com.rwxlicai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.entity.IndexResult;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.OkHttpClientManager;
import com.rwxlicai.view.RoundProgressBar;
import com.rwxlicai.view.TitleBuilder;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/21.
 */
public class Borrow_GetBorrowInfo_Activity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.interestRate)
    TextView interestRate;
    @InjectView(R.id.borrowTimeLimit)
    TextView borrowTimeLimit;
    @InjectView(R.id.borrowTitle)
    TextView borrowTitle;
    @InjectView(R.id.roundProgressBar)
    RoundProgressBar progressRate;
    @InjectView(R.id.borrowSum)
    TextView borrowSum;
    @InjectView(R.id.tenderSum)
    TextView tenderSum;
    @InjectView(R.id.tenderTime)
    TextView tenderTime;
    @InjectView(R.id.repaymentStyle)
    TextView repaymentStyle;
    @InjectView(R.id.next)
    Button next;


    private String eid;
    private static final Map<String, String> replaceStr = new HashMap<String, String>();
    private IndexResult result;
    private Float accountCrash;
    private Float crashSum;
    private Float crashYtou;

    @Override
    protected void initView() {
        setContentView(R.layout.borrow_getborrowinfo_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("标的详情").setLeftImage(R.mipmap.top_view_back);
        eid = getIntent().getStringExtra("data");


//        getBorrowInfo();
        get();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.next})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.next:
                if (Config.getLogin(Borrow_GetBorrowInfo_Activity.this)){
                    if (Config.getAuth(Borrow_GetBorrowInfo_Activity.this)){
                        Intent intent = new Intent(Borrow_GetBorrowInfo_Activity.this,Borrow_SaveTender_Activity.class);
                        intent.putExtra("bid",result.getBid());
                        intent.putExtra("accountCrash",accountCrash.toString());
                        startActivity(intent);
                        finish();
                    }else {
                        if (Config.getRealNameState(Borrow_GetBorrowInfo_Activity.this) == "2"){
                            startActivity(new Intent(Borrow_GetBorrowInfo_Activity.this,User_bankCardIdentityActivity.class));
                        }else {
                            startActivity(new Intent(Borrow_GetBorrowInfo_Activity.this,User_realIdentity.class));
                        }
                    }
                }else {
                    startActivity(new Intent(Borrow_GetBorrowInfo_Activity.this,A0_LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void get(){
//        OkHttpClientManager.getAsyn("https://www.baidu.com", new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(String u) {
////                mTv.setText(u);//注意这里是UI线程
//                showToast("=======");
//            }
//        });

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("key1", "value1");
//        map.put("key2", "value2");
//        map.put("key3", "value3");

//        OkHttpClientManager.Param param = new OkHttpClientManager.Param();
        OkHttpClientManager.nPostAsyn(Borrow_GetBorrowInfo_Activity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.getBorrowInfo + eid + ".do", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u) {
                try {
                    JSONObject obj = new JSONObject(u);
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("result");
                        result = gson.fromJson(data.toString(), IndexResult.class);
                        init();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



//        OkHttpClientManager.getAsyn(RwxUrlFactory.BASE_URL + RwxUrlFactory.getBorrowInfo + eid + ".do", new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    if (obj.getInt("code") == 0) {
//                        JSONObject data = obj.getJSONObject("result");
//                        result  = gson.fromJson(data.toString(), IndexResult.class);
//
//                        init();
//                    } else {
//                        showToast(obj.getString("message"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    private void init(){
        interestRate.setText(result.getInterestRate()+"%");
        borrowTimeLimit.setText(result.getBorrowTimeLimit()+"月");
        borrowTitle.setText(result.getBorrowTitle());
        borrowSum.setText(result.getBorrowSum());
        tenderSum.setText(result.getTenderSum());
        tenderTime.setText(result.getTenderTime());
        repaymentStyle.setText(result.getRepaymentStyle());

        double mfloat = Float.parseFloat(result.getProgressRate());
        progressRate.setProgress((int) mfloat);
        crashSum = Float.parseFloat(result.getBorrowSum());
        crashYtou = Float.parseFloat(result.getTenderSum());
        accountCrash = crashSum - crashYtou;
    }

}
