package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/20.
 */
public class E1_IdeaFeedbackAty extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.all_idea)
    View allIdea;
    @InjectView(R.id.idea)
    EditText idea;
    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.commit_feedback)
    Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_feedbackz_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("意见反馈").setLeftImage(R.mipmap.top_view_back);
    }


    @OnClick({R.id.commit_feedback, R.id.titlebar_iv_left,R.id.all_idea})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            case R.id.all_idea:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.commit_feedback:
                if (TextUtils.isEmpty(idea.getText())) {
                    showToast("请输入您的意见，谢谢！");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText())) {
                    showToast("请输入您的联系方式，给您更快的联系！");
                    return;
                }

                RequestParams params1 = new RequestParams();
                params1.put("uid", Config.getCachedUid(E1_IdeaFeedbackAty.this));
                params1.put("tel", phone.getText().toString());
                params1.put("opinion", idea.getText().toString());

                RequestUtils.ClientTokenPost(E1_IdeaFeedbackAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_FEEDBACK, params1, new NetCallBack(this) {
                    @Override
                    public void onMzbSuccess(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);

                            if (obj.getInt("code") == 0) {
                                showToast("提交成功！谢谢");
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
                        showToast("请求失败");
                    }
                });
        }
    }
}
