package com.xem.mzbcustomerapp.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
 * Created by xuebing on 15/10/29.
 */
public class A0_ModifyPwdActivity extends BaseActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.new_pwd)
    EditText new_pwd;
    @InjectView(R.id.next)
    Button next;
    @Override
    protected void initView() {
        setContentView(R.layout.a0_modify_pwd_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("修改密码").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.titlebar_iv_left,R.id.next})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    showToast("请输旧密码！");
                    return;
                }
                if (TextUtils.isEmpty(new_pwd.getText().toString())) {
                    showToast("请输入新密码！");
                    return;
                }
                hideKeyboard(view);
                modifypwd();
                break;
            default:
                break;
        }
    }

    private void modifypwd(){
        RequestParams params = new RequestParams();
        params.put("mobile", Config.getCachedPhone(A0_ModifyPwdActivity.this));
        params.put("opassword", pwd.getText().toString());
        params.put("npassword", new_pwd.getText().toString());

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.password, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("修改成功");
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

            }
        });

    }

}
