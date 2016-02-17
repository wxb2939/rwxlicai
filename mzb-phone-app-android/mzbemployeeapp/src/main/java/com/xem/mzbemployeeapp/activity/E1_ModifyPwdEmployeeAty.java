package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/25.
 */
public class E1_ModifyPwdEmployeeAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
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
        new TitleBuilder(this).setTitleText("修改密码").setLeftImage(R.mipmap.top_view_back);
    }

    @OnClick({R.id.makesure, R.id.titlebar_iv_left})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
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
                final MzbProgressDialog pd = new MzbProgressDialog(E1_ModifyPwdEmployeeAty.this,"请稍后....");
                pd.show();

                RequestParams params1 = new RequestParams();
                params1.put("accid", Config.getCachedBrandAccid(E1_ModifyPwdEmployeeAty.this).toString());
                params1.put("opassword", modifyOpwd.getText().toString());
                params1.put("npassword", modifyNpwd.getText().toString());
                RequestUtils.ClientTokenPost(E1_ModifyPwdEmployeeAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PASSWORD, params1, new NetCallBack() {
                    @Override
                    public void onMzbSuccess(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);

                            if (obj.getInt("code") == 0) {
                                showToast("修改成功");
                                pd.dismiss();
                                finish();
                            } else {
                                pd.dismiss();
                                showToast("帐号不存在/旧密码不正确");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMzbFailues(Throwable arg0) {
                        pd.dismiss();
                        showToast("请求失败");
                    }
                });
        }
    }
}
