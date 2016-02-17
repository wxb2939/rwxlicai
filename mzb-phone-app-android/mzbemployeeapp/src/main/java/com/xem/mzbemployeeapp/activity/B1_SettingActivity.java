package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.net.MzbHttpClient;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.MzbDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class B1_SettingActivity extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.modif_pwd)
    View modifi_pwd;
    @InjectView(R.id.cancel_login)
    View cancel_login;

    private MzbDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.b1_setting_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("设置").setLeftImage(R.mipmap.top_view_back);
    }

    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.modif_pwd,R.id.cancel_login})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.modif_pwd:
                intent2Aty(A0_ModifyPwdActivity.class);
                break;
            case R.id.cancel_login:
                String exit = getResources().getString(R.string.exit);
                String ensure_exit = getResources().getString(R.string.ensure_exit);

                mDialog = new MzbDialog(B1_SettingActivity.this, exit, ensure_exit);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Config.cachedUid(B1_SettingActivity.this, null);
                        Config.cachedToken(B1_SettingActivity.this, null);
                        Config.cachedPhone(B1_SettingActivity.this, null);
                        application.setIsLogin(true);
                        Intent intent = new Intent(B1_SettingActivity.this, LoginOrRegActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
//                        logout();
//                        JPushInterface.setAliasAndTags(getApplicationContext(), "", new LinkedHashSet<String>(), null);
//                        JPushInterface.stopPush(getApplicationContext());
                    }
                });

                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            default:
                break;
        }
    }


    private void logout(){
        RequestParams params = new RequestParams();

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.EXIT_LOGIN, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast(obj.getString("退出成功！"));
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
