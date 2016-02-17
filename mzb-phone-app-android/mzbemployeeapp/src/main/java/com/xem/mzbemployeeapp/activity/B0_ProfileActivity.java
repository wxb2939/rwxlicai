package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.entity.ExperEmploye;
import com.xem.mzbemployeeapp.entity.InfoData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/10/22.
 */
public class B0_ProfileActivity extends MzbActivity {

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
    public ExperEmploye employe=A0_ExpAty.employ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.b0_profile_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("更多").setLeftImage(R.mipmap.top_view_back);
        if (Config.getCachedIsexp(this)!= null) {
            setting.setVisibility(View.GONE);
        }
        if (Config.getCachedIsexp(this)!= null){
            imageLoader.displayImage(employe.getPortrait(), profile_img);
            profile_name.setText(employe.getName());
            profile_info.setText("职位"+employe.getPosition());
        }else {
            if (Config.getCachedBrandEmpid(this) != null) {
                if (Config.getCachedIsemploye(MzbApplication.getmContext())) {
                    GetEmployeInfo(Config.getCachedBrandEmpid(this).toString());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageLoader.displayImage(Config.getCachedPortrait(B0_ProfileActivity.this), profile_img);
    }


    @OnClick({R.id.titlebar_iv_left,R.id.setting,R.id.about_us,R.id.opinion_feedback,R.id.qr_code})
    public void clickAction(View view){
        switch (view.getId()) {
            case R.id.titlebar_iv_left:{
                finish();
                break;
            }
            //设置
            case R.id.setting:{
                intent2Aty(B1_SettingActivity.class);
                break;
            }
            //关于我们
            case R.id.about_us:{
                intent2Aty(B1_AboutUsActivity.class);
                break;
            }
            //意见反馈
            case R.id.opinion_feedback:{
                intent2Aty(B1_OpinionFeedbackActivity.class);
                break;
            }
            //岗位名片
            case R.id.qr_code:{
                intent2Aty(B1_ProfileInfoActivity.class);
                break;
            }
        }
    }

    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(B0_ProfileActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        InfoData infoData = gson.fromJson(obj.getJSONObject("data").toString(), InfoData.class);
                        imageLoader.displayImage(infoData.getPortrait(), profile_img);
                        Config.cachedPortrait(B0_ProfileActivity.this, infoData.getPortrait());
                        profile_name.setText(infoData.getName());
                        profile_info.setText("职位:"+infoData.getPosition());
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
