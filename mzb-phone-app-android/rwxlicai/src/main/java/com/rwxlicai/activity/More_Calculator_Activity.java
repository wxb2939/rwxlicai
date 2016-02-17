package com.rwxlicai.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class More_Calculator_Activity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.money)
    EditText money;
    @InjectView(R.id.month)
    EditText month;
    @InjectView(R.id.rate)
    EditText rate;
    @InjectView(R.id.next)
    Button next;
    @InjectView(R.id.total)
    TextView total;
    @InjectView(R.id.interest)
    TextView interest;

    private Integer mothy,moneyy,ratey;


    @Override
    protected void initView() {
        setContentView(R.layout.more_calculator_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("计算器").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left, R.id.next})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(money.getText().toString())) {
                    showToast("请输入投资金额");
                    return;
                }else {
                    moneyy = Integer.parseInt(money.getText().toString());
                }
                if (TextUtils.isEmpty(month.getText().toString())) {
                    showToast("请输入投资期限");
                    return;
                }else {
                    mothy = Integer.parseInt(month.getText().toString());
                }
                if (TextUtils.isEmpty(rate.getText().toString())) {
                    showToast("请输入年化收益率");
                    return;
                }else {
                    ratey = Integer.parseInt(rate.getText().toString());
                }
                hideKeyboard(view);
                calculate();
                break;
            default:
                break;
        }
    }

    private void calculate(){
        Integer all = moneyy*ratey/100/12*mothy+moneyy;
        Integer mInterest = all-moneyy;
        total.setText(all.toString());
        interest.setText(mInterest.toString());
    }
}
