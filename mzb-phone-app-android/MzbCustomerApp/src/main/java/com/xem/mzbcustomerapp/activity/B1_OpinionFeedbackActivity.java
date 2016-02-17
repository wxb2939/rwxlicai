package com.xem.mzbcustomerapp.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class B1_OpinionFeedbackActivity extends BaseActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.idea)
    EditText idea;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_opinionfeedback_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("意见反馈").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.commit_feedback})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.commit_feedback:{
                if (TextUtils.isEmpty(idea.getText().toString())){
                    showToast("请输入内容后进行提交!");
                    break;
                }
                commitModify(idea.getText().toString());
            }
            default:
                break;
        }
    }

    private void commitModify(final String des) {
        RequestParams params1 = new RequestParams();
        params1.put("tel", "");
        params1.put("uid", Config.getCachedUid(B1_OpinionFeedbackActivity.this));
        params1.put("opinion", des);
        MzbHttpClient.ClientTokenPost(B1_OpinionFeedbackActivity.this, MzbUrlFactory.BASE_URL +"platform/feedback/send", params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                Log.v("tag", result);
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("提交成功");
                        finish();
                    }else{
                        showToast("提交失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("提交失败");
            }
        });
    }


}
