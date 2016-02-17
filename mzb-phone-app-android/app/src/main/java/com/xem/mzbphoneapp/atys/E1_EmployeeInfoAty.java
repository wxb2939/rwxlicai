package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.InfoData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_nonet_back;

/**
 * Created by xuebing on 15/6/24.
 */
public class E1_EmployeeInfoAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.titlebar_iv_right)
    ImageView more;
    @InjectView(R.id.employee_img)
    ImageView employeeImg;
    @InjectView(R.id.employee_name)
    TextView employeeName;
    @InjectView(R.id.employee_roles)
    TextView employeeRoles;
    @InjectView(R.id.self_check)
    View selfCheck;
    @InjectView(R.id.self_manager)
    View selfManager;
    @InjectView(R.id.self_msg)
    View selfMsg;
    @InjectView(R.id.self_previlige)
    View selfPrevilige;
    @InjectView(R.id.self_integral)
    View selfIntegral; //业绩统计
    @InjectView(R.id.e1_studyplat)
    View studyPlat;
    @InjectView(R.id.e1_storepurchase)
    View storePurchase;
    @InjectView(R.id.more_employee)
    View employeeMore;
    @InjectView(R.id.emp_ground)
    RelativeLayout ground;

    View inflater;
    private String ppid;
    private String branid;
    private String empid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_info_aty);
        new TitleBuilder(this).setTitleText("我是员工").setLeftImage(R.mipmap.top_view_back).setRightImage(R.mipmap.news);
        ButterKnife.inject(this);

//        selfIntegral.setVisibility(View.GONE); //业绩统计隐藏

        Set<String> rights = Config.getCachedBrandRights(E1_EmployeeInfoAty.this);


        if (!rights.contains("PH004")) {
            selfCheck.setVisibility(View.GONE);
        }

        if (!rights.contains("PH001")) {
            selfPrevilige.setVisibility(View.GONE);
        }

        if (!rights.contains("PH002")) {
            selfIntegral.setVisibility(View.GONE);
        }

        if (!rights.contains("PH005")) {
            selfManager.setVisibility(View.GONE);
        }

        if (Config.getCachedIsexp(E1_EmployeeInfoAty.this) != null) {
            employeeMore.setVisibility(View.GONE);
        } else {
            employeeMore.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        Utility.getInstance().clickEffect(selfMsg);
        Utility.getInstance().clickEffect(selfCheck);
        Utility.getInstance().clickEffect(selfPrevilige);
        Utility.getInstance().clickEffect(selfIntegral);
        Utility.getInstance().clickEffect(studyPlat);
        Utility.getInstance().clickEffect(storePurchase);
        Utility.getInstance().clickEffect(employeeMore);
        Utility.getInstance().clickEffect(selfManager);
        if (checkNetAddToast()) {
            GetEmployeInfo(Config.getCachedBrandEmpid(E1_EmployeeInfoAty.this).toString());
        } else {
            View grounds = View.inflate(this, ground_nonet_back, null);
            ground.addView(grounds);
        }
    }

    @OnClick({R.id.titlebar_iv_left, R.id.more_employee, R.id.titlebar_iv_right,
            R.id.e1_storepurchase, R.id.e1_studyplat,
            R.id.self_msg, R.id.self_previlige,
            R.id.self_check, R.id.self_manager, R.id.self_integral})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                Intent mainIntent = new Intent(E1_EmployeeInfoAty.this, MainTabAty.class);
                mainIntent.putExtra("flag", 1);
                startActivity(mainIntent);
                finish();
                break;
            case R.id.more_employee:
                intent2Aty(E1_EmployeeMoreAty.class);
                finish();
                break;
            case R.id.titlebar_iv_right:
                intent2Aty(E1_EmployeeNewsAty.class);
                finish();
                break;
            case R.id.e1_studyplat:
                intent2Aty(E1_StudyPlatAty.class);
                break;
            case R.id.e1_storepurchase:
                intent2Aty(E1_StorePurchaseAty.class);
                break;
            case R.id.self_integral: //业绩查询
                if (Config.getCachedUid(E1_EmployeeInfoAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(E1_EmployeeInfoAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent mIntent = new Intent(E1_EmployeeInfoAty.this, E1_CommPerfPpAty.class);
                        mIntent.putExtra("ppid", ppid);
                        startActivity(mIntent);
                    } else if (Utility.getInstance().isMlsorGw(rights)) {
                        Intent mls = new Intent(E1_EmployeeInfoAty.this, E3_CommPerfEmployeeAty.class);
                        mls.putExtra("empid", Config.getCachedBrandEmpid(E1_EmployeeInfoAty.this).toString());
                        mls.putExtra("str", "this is test!");
                        startActivity(mls);
                    } else {
                        Intent brand = new Intent(E1_EmployeeInfoAty.this, E2_CommPerfBranchAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                break;


            case R.id.self_msg: //预约查询
                if (Config.getCachedUid(E1_EmployeeInfoAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(E1_EmployeeInfoAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent store = new Intent(E1_EmployeeInfoAty.this, E2_StoreOrderQueryAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent order = new Intent(E1_EmployeeInfoAty.this, E2_OrderQueryAty.class);
                        order.putExtra("ppid", ppid);
                        order.putExtra("branid", branid);
                        startActivity(order);
                    }
                }
                break;

            case R.id.self_previlige: //满意度得分
                if (Config.getCachedUid(E1_EmployeeInfoAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(E1_EmployeeInfoAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {

                        Intent store = new Intent(E1_EmployeeInfoAty.this, E2_StoreFatisfyAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent comm = new Intent(E1_EmployeeInfoAty.this, E1_CommSelectAty.class);
                        comm.putExtra("ppid", ppid);
                        comm.putExtra("branid", branid);
                        startActivity(comm);
                    }
                    break;
                }

            case R.id.self_check:  //门店当日服务查询
                if (Config.getCachedUid(E1_EmployeeInfoAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(E1_EmployeeInfoAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent brand = new Intent(E1_EmployeeInfoAty.this, E2_BrandServicetppAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    } else {
                        Intent brand = new Intent(E1_EmployeeInfoAty.this, E2_TodayServiceCheckAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                break;

            case R.id.self_manager: //137管理
                if (Config.getCachedUid(E1_EmployeeInfoAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Intent brand = new Intent(E1_EmployeeInfoAty.this, E2_Client137ManagerAty.class);
                    brand.putExtra("ppid", ppid);
                    brand.putExtra("branid", branid);
                    startActivity(brand);
                }
            default:
                break;
        }
    }


    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(E1_EmployeeInfoAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        InfoData infoData = gson.fromJson(obj.getJSONObject("data").toString(), InfoData.class);
                        imageLoader.displayImage(infoData.getPortrait(), employeeImg);
                        employeeName.setText(infoData.getName());
                        employeeRoles.setText(infoData.getPosition());

                        ppid = infoData.getPpid().toString();
                        branid = infoData.getBranid().toString();
                        empid = infoData.getEmpid().toString();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            Intent mIntent = new Intent(E1_EmployeeInfoAty.this, MainTabAty.class);
            mIntent.putExtra("flag", 1);
            startActivity(mIntent);
            finish();
            return true;
        }
        return true;
    }

}
