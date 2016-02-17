package com.xem.mzbphoneapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.A0_LoginAty;
import com.xem.mzbphoneapp.atys.E0_CouponAty;
import com.xem.mzbphoneapp.atys.E0_EmployeeBindAty;
import com.xem.mzbphoneapp.atys.E0_InfoAty;
import com.xem.mzbphoneapp.atys.E1_EmployeeInfoAty;
import com.xem.mzbphoneapp.atys.E1_ProfileInfoAty;
import com.xem.mzbphoneapp.atys.E1_SettingAty;
import com.xem.mzbphoneapp.atys.MzbApplication;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

/**
 * Created by xuebing on 15/6/3.
 */
public class E0_ProfileFragment extends MzbFragment {

    private ImageView cover_user_photo;
    private String token;
    private String uid, uri;
    private Boolean isemploye;
    private static int FIMG_REQUEST_CODE = 1;
    private TextView tv_uid, userIntegral, jf;
    private MzbApplication app;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View footView = inflater.inflate(R.layout.e0_more_fg, container, false);

        new TitleBuilder(footView).setTitleText("个人");

        isemploye = Config.getCachedIsemploye(getActivity());
        cover_user_photo = (ImageView) footView.findViewById(R.id.cover_user_photo);
        tv_uid = (TextView) footView.findViewById(R.id.tv_uid);
//        userIntegral = (TextView) footView.findViewById(R.id.user_integral);
        jf = (TextView) footView.findViewById(R.id.morefg_jf);

        // 用户登陆
        View personLogin = footView.findViewById(R.id.personLogin);
        personLogin.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(personLogin);
        personLogin.setLayoutParams(Utility.getInstance().getLayoutThreeParams(personLogin, this));

        //我是员工绑定
        View selfEmployee = footView.findViewById(R.id.self_employee);
        selfEmployee.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(selfEmployee);

        //点击设置

        View selfSetting = footView.findViewById(R.id.self_setting);
        selfSetting.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(selfSetting);

        //消息

        View selfMsg = footView.findViewById(R.id.self_msg);
        selfMsg.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(selfMsg);

        //优惠卷
        View selfPrevilige = footView.findViewById(R.id.self_previlige);
        selfPrevilige.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(selfPrevilige);
        return footView;
    }

    @Override
    public void onResume() {
        super.onResume();
        app = (MzbApplication) getActivity().getApplication();
        token = Config.getCachedToken(getActivity());
        uid = Config.getCachedUid(getActivity());
        uri = Config.getCachedPortrait(getActivity());
        if (app.getIsLogin() && app.getBranchData() != null) {
            imageLoader.displayImage(uri, cover_user_photo);
            tv_uid.setText(Config.getCachedName(getActivity()));
            jf.setVisibility(View.INVISIBLE);
        } else {
            cover_user_photo.setImageDrawable(getResources().getDrawable(R.mipmap.ic_default));
            tv_uid.setText("亲...");
            jf.setText("您未登陆");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String uri = Config.getCachedPortrait(getActivity());
        imageLoader.displayImage(uri, cover_user_photo);
        return;
    }


    class setClicklistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.personLogin:
                    if (app.getIsLogin()) {
                        Intent profile = new Intent(getActivity(), E1_ProfileInfoAty.class);
                        profile.putExtra(Config.KEY_TOKEN, token);
                        profile.putExtra(Config.KEY_UID, uid);
                        startActivityForResult(profile, FIMG_REQUEST_CODE);
                        break;
                    } else {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    }
                    //点击我是员工
                case R.id.self_employee:
                    //登录
                    if (app.getIsLogin()) {
                        //绑定
                        if (Config.getCachedIsemploye(getActivity())) {
                            intent2Activity(E1_EmployeeInfoAty.class);
                            getActivity().finish();
                            break;
                        }
                        //未绑定
                        else {
                            intent2Activity(E0_EmployeeBindAty.class);
                            break;
                        }
                    }
                    //未登录
                    else {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    }
                case R.id.self_setting:
                    if (Utility.getInstance().isFastClick()) {
                        return;
                    } else {
                        if (app.getIsLogin()) {
                            intent2Activity(E1_SettingAty.class);
                            break;
                        } else {
                            intent2Activity(A0_LoginAty.class);
                            break;
                        }
                    }

                case R.id.self_msg:
                    if (app.getIsLogin()) {
                        intent2Activity(E0_InfoAty.class);
                        getActivity().finish();
                    } else {
                        intent2Activity(A0_LoginAty.class);
                    }
                    break;
                case R.id.self_previlige:
                    if (Utility.getInstance().isFastClick()) {
                        return;
                    } else {
                        if (app.getIsLogin()) {
                            intent2Activity(E0_CouponAty.class);
                            break;
                        } else {
                            intent2Activity(A0_LoginAty.class);
                            break;
                        }
                    }
                default:
                    break;
            }
        }
    }
}
