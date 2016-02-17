package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
 * Created by xuebing on 15/10/26.
 */
public class B1_ProfileInfoActivity extends MzbActivity {

    @InjectView(R.id.profile_img)
    CircleImageView profile_img;
    @InjectView(R.id.profile_name)
    TextView profile_name;
    @InjectView(R.id.profile_info)
    TextView profile_info;
    @InjectView(R.id.work_name)
    TextView work_name;
    @InjectView(R.id.pority)
    LinearLayout pority;
    @InjectView(R.id.sex)
    TextView sex;


    private static int IMG_REQUEST_CODE = 1;
//    private String uri;
    private String strName;
    private String strInfo;
    private String strSex;
    String uri;
//    private UserInfoData userInfoData;
    public ExperEmploye employe=A0_ExpAty.employ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.b1_profile_info_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("岗位名片").setLeftImage(R.mipmap.top_view_back);
//        profile_info.setText(Config.getCachedDesc(B1_ProfileInfoActivity.this));
        if (Config.getCachedIsexp(this)!= null){
            imageLoader.displayImage(employe.getPortrait(), profile_img);
            profile_name.setText(employe.getName());
            work_name.setText(employe.getPosition());
            if (employe.getSex().equals("F")){
                sex.setText("\t\t\t女");
            }
            else if(employe == null){
                sex.setText("");
            }
            else{
                sex.setText("\t\t\t男");
            }
            if (employe.getBname() != null) {
                profile_info.setText("所属:" + employe.getBname());
            }
        }else {
            if (Config.getCachedBrandEmpid(this) != null) {
                if (Config.getCachedIsemploye(MzbApplication.getmContext())) {
                    if (Config.getCachedSex(this)!= null &&  Config.getCachedSex(this).equals("F")){
                        sex.setText("\t\t\t女");
                    }
                    else if (Config.getCachedSex(this)== null){
                        sex.setText("");
                    }
                    else{
                        sex.setText("\t\t\t男");
                    }
                    if (Config.getCachedBrand(this) != null){
                        profile_info.setText("所属:"+Config.getCachedBrand(this));
                    }
                    GetEmployeInfo(Config.getCachedBrandEmpid(this).toString());

                }
            }
        }
        if (Config.getCachedIsexp(this)!= null){
            uri=A0_ExpAty.employ.getPortrait();
        }else{
            uri = Config.getCachedPortrait(B1_ProfileInfoActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uri = Config.getCachedPortrait(B1_ProfileInfoActivity.this);
        imageLoader.displayImage(uri, profile_img);
    }



    @OnClick({R.id.titlebar_iv_left,R.id.pority})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            //修改头像
            case R.id.pority:
                Intent intent = new Intent(B1_ProfileInfoActivity.this, A0_HeadPortraitAty.class);
                intent.putExtra("URI", uri);
                startActivityForResult(intent, IMG_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(B1_ProfileInfoActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        InfoData infoData = gson.fromJson(obj.getJSONObject("data").toString(), InfoData.class);
                        imageLoader.displayImage(infoData.getPortrait(), profile_img);
                        profile_name.setText(infoData.getName());
                        work_name.setText(infoData.getPosition());
//                        if (infoData.sex.equals("F")){
//                            sex.setText("女");
//                        }else{
//                            sex.setText("男");
//                        }
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
