package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.EmployeData;
import com.xem.mzbphoneapp.entity.LoginData;
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
 * Created by xuebing on 15/9/9.
 */
public class A0_ExpAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.lb_exp)
    View lb_exp;
    @InjectView(R.id.lb_good)
    ImageView lb_good;
    @InjectView(R.id.dz_exp)
    View dz_exp;
    @InjectView(R.id.dz_good)
    ImageView dz_good;
    @InjectView(R.id.gw_exp)
    View gw_exp;
    @InjectView(R.id.gw_good)
    ImageView gw_good;
    @InjectView(R.id.mls_exp)
    View mls_exp;
    @InjectView(R.id.mls_good)
    ImageView mls_good;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a0_exp_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("选择角色").setLeftImage(R.mipmap.top_view_back);

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (application.getCurRoles()){
            case 1:
                lb_good.setVisibility(View.VISIBLE);
                break;
            case 2:
                dz_good.setVisibility(View.VISIBLE);
                break;
            case 3:
                gw_good.setVisibility(View.VISIBLE);
                break;
            case 4:
                mls_good.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.titlebar_iv_left,R.id.lb_exp,R.id.lb_good,
                R.id.dz_exp,R.id.dz_good,R.id.gw_exp,R.id.gw_good,R.id.mls_exp,R.id.mls_good})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.lb_exp:
                application.setCurRoles(1);
                DoLogin("101");
                break;
            case R.id.dz_exp:
                application.setCurRoles(2);
                DoLogin("112");
                break;
            case R.id.gw_exp:
                application.setCurRoles(3);
                DoLogin("114");
                break;
            case R.id.mls_exp:
                application.setCurRoles(4);
                DoLogin("115");
                break;
            default:
                break;
        }
    }


    public void DoLogin( String role) {
        RequestParams params1 = new RequestParams();
        params1.put("type", "B");
        params1.put("role", role);
        RequestUtils.ClientTokenPost(A0_ExpAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_DEMOLOGIN, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        //wxb
                        LoginData loginData = gson.fromJson(obj.getJSONObject("data").toString(), LoginData.class);

                        Config.cachedUid(A0_ExpAty.this, loginData.getUid());
                        Config.cachedToken(A0_ExpAty.this, loginData.getToken());
                        Config.cachedName(A0_ExpAty.this, loginData.getName());
                        Config.cachedPortrait(A0_ExpAty.this, loginData.getPortrait());
                        Config.cachedIsemploye(A0_ExpAty.this, loginData.getIsemploye());
                        Config.cachedIsmember(A0_ExpAty.this, loginData.getIsmember());
                        Config.cachedScore(A0_ExpAty.this, loginData.getScore());

                        EmployeData employeData = loginData.getEmploye();
                        if (employeData != null) {
                            Config.cachedBrandAccid(A0_ExpAty.this, employeData.getAccid());
                            Config.cachedBrandEmpid(A0_ExpAty.this, employeData.getEmpid());
                            Config.cachedBrandCodes(A0_ExpAty.this, employeData.getRoles());
                            Config.cachedBrandRights(A0_ExpAty.this, employeData.getRights());
                        }

                        BranchData branchData = loginData.getBranch();
                        application.setIsLogin(true);
                        if (branchData != null) {
                            application.setBranchData(branchData);
                        }
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
//                pd.dismiss();
                showToast("请求失败,请确认网络连接!");
            }
        });
    }


}
