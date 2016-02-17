package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.CommLogin;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/20.
 */
public class A0_ModifyPwdAty extends MzbActivity {


    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.all_modifypwd)
    View allModifypwd;
    @InjectView(R.id.makesure)
    Button makesure;
    @InjectView(R.id.modify_opwd)
    TextView modifyOpwd;
    @InjectView(R.id.modify_npwd)
    TextView modifyNpwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_pwd_aty);
        ButterKnife.inject(this);
//        new TitleBuilder(this).setTitleText("修改密码").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.makesure, R.id.titlebar_iv_left,R.id.all_modifypwd})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.all_modifypwd:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.makesure:

                if (TextUtils.isEmpty(modifyOpwd.getText())) {
                    showToast("请输入您的旧密码，谢谢！");
                    return;
                }
                if (TextUtils.isEmpty(modifyNpwd.getText())) {
                    showToast("请输入您的新密码，谢谢！");
                    return;
                }

                RequestParams params1 = new RequestParams();
                params1.put("mobile", Config.getCachedPhone(A0_ModifyPwdAty.this));
                params1.put("opassword", modifyOpwd.getText().toString());
                params1.put("npassword", modifyNpwd.getText().toString());
                RequestUtils.ClientTokenPost(A0_ModifyPwdAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_PASSWORD, params1, new NetCallBack(this) {
                    @Override
                    public void onMzbSuccess(String result) {

                        try {
                            JSONObject obj = new JSONObject(result);

                            if (obj.getInt("code") == 0) {
                                showToast("密码修改成功！");
                                new CommLogin(A0_ModifyPwdAty.this).Login(Config.getCachedPhone(A0_ModifyPwdAty.this), Config.getCachedPassword(A0_ModifyPwdAty.this), application);
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
