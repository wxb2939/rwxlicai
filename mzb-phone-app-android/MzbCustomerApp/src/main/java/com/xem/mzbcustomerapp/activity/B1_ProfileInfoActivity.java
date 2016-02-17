package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.UserInfoData;
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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/10/26.
 */
public class B1_ProfileInfoActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.person_info)
    View person_info;
    @InjectView(R.id.circle_img)
    CircleImageView circle_img;
    @InjectView(R.id.profile_name)
    TextView profile_name;
    @InjectView(R.id.profile_account)
    TextView profile_account;
    @InjectView(R.id.profile_sex)
    TextView profile_sex;
    @InjectView(R.id.profile_info)
    TextView profile_info;
    @InjectView(R.id.ll_name)
    View ll_name;
    @InjectView(R.id.ll_account)
    View ll_account;
    @InjectView(R.id.ll_sex)
    View ll_sex;
    @InjectView(R.id.ll_info)
    View ll_info;


    private static int IMG_REQUEST_CODE = 1;
    private String uri;
    private String strName;
    private String strInfo;
    private String strSex;
    private UserInfoData userInfoData;


    @Override
    protected void initView() {
        setContentView(R.layout.b1_profile_info_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("个人资料").setLeftImage(R.mipmap.top_view_back);
        profile_info.setText(Config.getCachedDesc(B1_ProfileInfoActivity.this));
    }

    @Override
    protected void initData() {
        getProfileInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        uri = Config.getCachedPortrait(B1_ProfileInfoActivity.this);
        imageLoader.displayImage(uri, circle_img);
        profile_info.setText(Config.getCachedDesc(B1_ProfileInfoActivity.this));
//        getProfileInfo();
    }



    @OnClick({R.id.titlebar_iv_left,R.id.person_info,R.id.ll_name,R.id.ll_info,R.id.ll_sex})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.person_info:
                Intent intent = new Intent(B1_ProfileInfoActivity.this, A0_HeadPortraitAty.class);
                intent.putExtra("URI", uri);
                startActivityForResult(intent, IMG_REQUEST_CODE);
                break;
            //用户名
            case R.id.ll_name:
                Intent orderExtra = new Intent(B1_ProfileInfoActivity.this, A0_AddUserNameActivity.class);
                orderExtra.putExtra("title", "添加用户名");
                orderExtra.putExtra("info",profile_name.getText());
                startActivityForResult(orderExtra, 0);
                break;
            case R.id.ll_info:
                Intent infoExtra = new Intent(B1_ProfileInfoActivity.this, A0_AddUserinfpActivity.class);
                infoExtra.putExtra("info", profile_info.getText());
                startActivityForResult(infoExtra, 0);
                break;
            case R.id.ll_sex:
                Intent sexExtra = new Intent(B1_ProfileInfoActivity.this, A0_CheckSexActivity.class);
                sexExtra.putExtra("info",strSex);
                startActivityForResult(sexExtra, 0);
                break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            //修改用户名后返回的新用户名
            case -107:
                strName = data.getStringExtra("extra");
                profile_name.setText("   "+strName);
                break;

            case -105:
                strInfo = data.getStringExtra("extra");
                profile_info.setText("   "+strInfo);
                break;
            case -110:
                strSex = data.getStringExtra("extra");
                if (strSex == null){
                    profile_sex.setText("");
                }
                else if (strSex.equals("F")){
                    profile_sex.setText("\t\t\t\t\t女");
                }else {
                    profile_sex.setText("\t\t\t\t\t男");
                }
            default:
                break;
        }
        return;
    }


    private void getProfileInfo() {
        RequestParams params1 = new RequestParams();

        params1.put("uid", Config.getCachedUid(B1_ProfileInfoActivity.this));

        MzbHttpClient.ClientTokenPost(B1_ProfileInfoActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.user_info, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        userInfoData = gson.fromJson(obj.get("data").toString(),UserInfoData.class);
                        profile_name.setText(userInfoData.getName());
                        profile_account.setText(userInfoData.getMobile());
                        strSex = userInfoData.getSex();
                        if (strSex == null){
                            profile_sex.setText("");
                        }
                        else if(strSex.equals("F")){
                            profile_sex.setText("\t\t\t\t\t\t\t\t\t\t女");
                        }
                        else {
                            profile_sex.setText("\t\t\t\t\t\t\t\t\t\t男");
                        }
                        profile_info.setText(userInfoData.getDesc());
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
