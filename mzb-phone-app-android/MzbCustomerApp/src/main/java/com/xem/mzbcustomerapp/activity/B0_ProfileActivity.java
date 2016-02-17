package com.xem.mzbcustomerapp.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/10/22.
 */
public class B0_ProfileActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.person_info)
    View person_info;
    @InjectView(R.id.about_us)
    View about_us;
    @InjectView(R.id.setting)
    View setting;
    @InjectView(R.id.opinion_feedback)
    View opinion_feedback;
    @InjectView(R.id.qr_code)
    View qr_code;
    @InjectView(R.id.profile_img)
    CircleImageView profile_img;
    @InjectView(R.id.profile_name)
    TextView profile_name;
    @InjectView(R.id.profile_info)
    TextView profile_info;


    @Override
    protected void initView() {
        setContentView(R.layout.b0_profile_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("更多").setLeftImage(R.mipmap.top_view_back);
//        imageLoader.displayImage(LoadImgUtil.loadbigImg(Config.getCachedPortrait(B0_ProfileActivity.this)),profile_img);
//        profile_name.setText(Config.getCachedName(B0_ProfileActivity.this));
//        profile_info.setText(Config.getCachedDesc(B0_ProfileActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageLoader.displayImage(LoadImgUtil.loadbigImg(Config.getCachedPortrait(B0_ProfileActivity.this)), profile_img);
        profile_name.setText(Config.getCachedName(B0_ProfileActivity.this));
        profile_info.setText(Config.getCachedDesc(B0_ProfileActivity.this));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left,R.id.person_info,R.id.about_us,R.id.setting,R.id.opinion_feedback,R.id.qr_code})
    public void clickAction(View view){
        switch (view.getId()){
            //后退
            case R.id.titlebar_iv_left:
                finish();
                break;
            //个人资料
            case R.id.person_info:
                intent2Aty(B1_ProfileInfoActivity.class);
                break;
            //关于我们
            case R.id.about_us:
                intent2Aty(B1_AboutUsActivity.class);
                break;
            //setting
            case R.id.setting:
                intent2Aty(B1_SettingActivity.class);
                break;
            //意见反馈
            case R.id.opinion_feedback:
                intent2Aty(B1_OpinionFeedbackActivity.class);
                break;
            //我的二维码
            case R.id.qr_code:
                intent2Aty(B1_MyQrActivity.class);
                break;
            default:
                break;
        }
    }
}
