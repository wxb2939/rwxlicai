package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.TextAdapter;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.entity.GridViewDetail;
import com.xem.mzbemployeeapp.entity.InfoData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xuebing on 15/6/24.
 */
public class TextAty extends MzbActivity {

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
    @InjectView(R.id.gridview)
    GridView gridview;
    View inflater;
    private String ppid;
    private String branid;
    private String empid;
    //准备好要显示的数据
    //文字
    ArrayList<String> desc;
    //图片
    ArrayList<Integer> images;
    TextAdapter adapter;
    ArrayList<GridViewDetail> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employ_inform_activity);
        new TitleBuilder(this).setTitleText("美之伴").setLeftImage(R.mipmap.top_view_back).setRightImage(R.mipmap.news);
        ButterKnife.inject(this);
        desc=new ArrayList();
        images=new ArrayList();
//        selfIntegral.setVisibility(View.GONE); //业绩统计隐藏
        //未绑定
        if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
            list.add(new GridViewDetail("预约查询",R.mipmap.e_check));
            list.add(new GridViewDetail("员工账号管理",R.mipmap.e_more));
        }
        adapter=new TextAdapter(this,list);
        gridview.setAdapter(adapter);
        Set<String> rights = Config.getCachedBrandRights(TextAty.this);



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

        if (Config.getCachedIsexp(TextAty.this) != null) {
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
            if (Config.getCachedBrandEmpid(TextAty.this) != null){
                GetEmployeInfo(Config.getCachedBrandEmpid(TextAty.this).toString());
            }
        } else {
            View grounds = View.inflate(this, R.layout.ground_nonet_back, null);
            ground.addView(grounds);
        }
    }
    //    self_check
    @OnClick({R.id.self_check,R.id.self_msg,R.id.self_previlige,R.id.self_integral,R.id.self_manager,R.id.more_employee,R.id.titlebar_iv_right})
    public void onDo(View v) {
        //未绑定
        if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
            showToast("请绑定客户档案");
            intent2Aty(E0_EmployeeBindAty.class);
            return;
        }
        switch (v.getId()) {
            //门店当日服务查询
            case R.id.self_check: {
                if (Config.getCachedUid(TextAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(TextAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent brand = new Intent(TextAty.this, E2_BrandServicetppAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    } else {
                        Intent brand = new Intent(TextAty.this, E2_TodayServiceCheckAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                break;
            }
            //预约查询
            case R.id.self_msg: {
                if (Config.getCachedUid(TextAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(TextAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent store = new Intent(TextAty.this, E2_StoreOrderQueryAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent order = new Intent(TextAty.this, E2_OrderQueryAty.class);
                        order.putExtra("ppid", ppid);
                        order.putExtra("branid", branid);
                        startActivity(order);
                    }
                }
                break;
            }
            //满意度得分
            case R.id.self_previlige: {
                if (Config.getCachedUid(TextAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(TextAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent store = new Intent(TextAty.this, E2_StoreFatisfyAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent comm = new Intent(TextAty.this, E1_CommSelectAty.class);
                        comm.putExtra("ppid", ppid);
                        comm.putExtra("branid", branid);
                        startActivity(comm);
                    }

                }

                break;
            }
            //业绩统计
            case R.id.self_integral: {
                if (Config.getCachedUid(TextAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Set<String> rights = Config.getCachedBrandCodes(TextAty.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent mIntent = new Intent(TextAty.this, E1_CommPerfPpAty.class);
                        mIntent.putExtra("ppid", ppid);
                        startActivity(mIntent);
                    } else if (Utility.getInstance().isMlsorGw(rights)) {
                        Intent mls = new Intent(TextAty.this, E3_CommPerfEmployeeAty.class);
                        mls.putExtra("empid", Config.getCachedBrandEmpid(TextAty.this).toString());
                        mls.putExtra("str", "this is test!");
                        startActivity(mls);
                    } else {
                        Intent brand = new Intent(TextAty.this, E2_CommPerfBranchAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                break;
            }
            //顾客137管理
            case R.id.self_manager: {
                if (Config.getCachedUid(TextAty.this) == null && ppid == null && branid == null) {
                    showToast("请先登陆，绑定门店");
                    intent2Aty(A0_LoginAty.class);
                } else {
                    Intent brand = new Intent(TextAty.this, E2_Client137ManagerAty.class);
                    brand.putExtra("ppid", ppid);
                    brand.putExtra("branid", branid);
                    startActivity(brand);
                }
                break;
            }
            //更多
            case R.id.more_employee: {
                intent2Aty(E1_EmployeeMoreAty.class);
                finish();
                break;
            }
            //消息
            case R.id.titlebar_iv_right: {
                intent2Aty(E1_EmployeeNewsAty.class);
                finish();
                break;
            }
        }
    }

    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(TextAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
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



}
