package com.rwxlicai.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/22.
 */
public class Borrow_SaveTender_Activity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.borrow_total)
    TextView borrow_total;
    @InjectView(R.id.borrow_balance)
    TextView borrow_balance;
    @InjectView(R.id.borrow_num)
    EditText borrow_num;
    @InjectView(R.id.borrow_pwd)
    EditText borrow_pwd;
    @InjectView(R.id.next)
    Button next;

    private String bid = null;
    private String accountCrash;

    @Override
    protected void initView() {
        setContentView(R.layout.borrow_savetender_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("投资").setLeftImage(R.mipmap.top_view_back);
//        data = (IndexResult) getIntent().getSerializableExtra("data");
        bid = getIntent().getStringExtra("bid");
        accountCrash = getIntent().getStringExtra("accountCrash");
//        saveTender();
    }

    @Override
    protected void initData() {
        borrow_total.setText(accountCrash);
        borrow_balance.setText(Config.getAvailableMoney(Borrow_SaveTender_Activity.this));

    }

    @OnClick({R.id.titlebar_iv_left,R.id.next})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(borrow_num.getText().toString())) {
                    showToast("请输入购买金额");
                    return;
                }
                if (TextUtils.isEmpty(borrow_pwd.getText().toString())) {
                    showToast("请输入支付密码");
                    return;
                }
                saveTender();
                break;
            default:
                break;
        }
    }

    public void saveTender() {
        RequestParams params = new RequestParams();
        params.put("bid", bid);
        params.put("tenderAmount", borrow_num.getText().toString());
        params.put("borrowTypeId", "1");
        params.put("payPassword", borrow_pwd.getText().toString());
        RwxHttpClient.ClientTokenPost(Borrow_SaveTender_Activity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.saveTender, params, new NetCallBack() {
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
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }
}