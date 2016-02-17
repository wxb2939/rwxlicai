package com.rwxlicai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.other.GestureActivity;
import com.rwxlicai.utils.Config;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/14.
 */
public class Profile_ManagerActivity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.gesture)
    View gesture;
    @InjectView(R.id.layout)
    Button layout;
    @InjectView(R.id.modify_pwd)
    View modify_pwd;
    @InjectView(R.id.updatePayPass)
    View updatePayPass;


    @Override
    protected void initView() {
        setContentView(R.layout.profile_manager_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("账户管理").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.gesture,R.id.layout,R.id.modify_pwd,R.id.updatePayPass})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.gesture:
                intent2Aty(GestureActivity.class);
                break;
            case R.id.layout:
                layout();
                break;
            case R.id.modify_pwd:
                intent2Aty(User_ModifyPwdActivity.class);
                break;
            case R.id.updatePayPass:
                intent2Aty(User_ModifyPayPwdActivity.class);
                break;
            default:
                break;
        }
    }


    private void layout(){
        RwxHttpClient.ClientPost(Profile_ManagerActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.layout, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("code").equals("0")) {
                        showToast(obj.getString("message"));
                        Config.cachedToken(Profile_ManagerActivity.this, null);
                        Config.cachedPhone(Profile_ManagerActivity.this, null);
                        Config.cachedLogin(Profile_ManagerActivity.this,false);
                        Intent intent = new Intent(Profile_ManagerActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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
