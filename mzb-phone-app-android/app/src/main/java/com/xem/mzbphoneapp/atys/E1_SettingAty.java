package com.xem.mzbphoneapp.atys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbDialog;

import java.util.LinkedHashSet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/6/29.
 */
public class E1_SettingAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.set_exit)
    View btn_exit;
    @InjectView(R.id.set_idea_feedback)
    View ideaFeedback;
    @InjectView(R.id.set_modif_pwd)
    View modifPwd;
    @InjectView(R.id.set_about_us)
    View aboutUs;


    private MzbDialog mDialog;
    public MzbApplication app;


    @Override
    public void onResume() {
        super.onResume();
        Utility.getInstance().clickEffect(modifPwd);
        Utility.getInstance().clickEffect(ideaFeedback);
        Utility.getInstance().clickEffect(aboutUs);
        Utility.getInstance().clickEffect(btn_exit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("设置").setLeftImage(R.mipmap.top_view_back);
    }

    @OnClick({R.id.titlebar_iv_left,R.id.set_exit, R.id.set_idea_feedback, R.id.set_modif_pwd, R.id.set_about_us})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
//              getFragmentManager().beginTransaction().replace(R.id.container,new E0_ProfileFragment());
                break;
            case R.id.set_exit:
                String exit = getResources().getString(R.string.exit);
                String ensure_exit = getResources().getString(R.string.ensure_exit);

                mDialog = new MzbDialog(E1_SettingAty.this, exit, ensure_exit);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Config.cachedUid(E1_SettingAty.this, null);
                        Config.cachedToken(E1_SettingAty.this, null);
                        Config.cachedPhone(E1_SettingAty.this, null);
                        Config.cachedIsexp(E1_SettingAty.this, null);
                        Config.cachedBrandAccid(E1_SettingAty.this, -1);
                        Config.cachedBrandEmpid(E1_SettingAty.this, -1);
                        Config.cachedBrandCodes(E1_SettingAty.this, null);
                        Config.cachedBrandRights(E1_SettingAty.this, null);
                        application.setBranchData(null);
                        application.setIsLogin(false);
                        if (app != null){
                            app.setBranchData(null);
                        }
                        Intent intent = new Intent(E1_SettingAty.this, MainTabAty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        JPushInterface.setAliasAndTags(getApplicationContext(), "", new LinkedHashSet<String>(), null);
                        JPushInterface.stopPush(getApplicationContext());
                    }
                });

                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                break;
            case R.id.set_idea_feedback:
                intent2Aty(E1_IdeaFeedbackAty.class);
                break;
            case R.id.set_modif_pwd:
                intent2Aty(A0_ModifyPwdAty.class);
                break;

            case R.id.set_about_us:
                intent2Aty(E1_AboutUsAty.class);
                break;
            default:
                break;
        }
    }

}
