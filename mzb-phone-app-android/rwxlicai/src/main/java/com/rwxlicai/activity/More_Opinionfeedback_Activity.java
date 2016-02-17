package com.rwxlicai.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class More_Opinionfeedback_Activity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.idea)
    EditText idea;


    @Override
    protected void initView() {
        setContentView(R.layout.more_opinionfeedback_activity);
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
                feedBack();
            }
            default:
                break;
        }
    }

    public void feedBack() {

        RequestParams params = new RequestParams();
        params.put("content", idea.getText().toString());


        RwxHttpClient.ClientPost(More_Opinionfeedback_Activity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.feedBack, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 200) {
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
