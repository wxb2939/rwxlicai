package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.TextAdapter;
import com.xem.mzbemployeeapp.application.MzbApplication;
import com.xem.mzbemployeeapp.entity.GridViewDetail;
import com.xem.mzbemployeeapp.entity.InfoData;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.CommLogin;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.Utility;
import com.xem.mzbemployeeapp.views.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2015/11/19.
 */
public class HomeActivity extends MzbActivity {

    @InjectView(R.id.emp_ground)
    RelativeLayout groundes;

    @InjectView(R.id.employee_img)
    ImageView employeeImg;

    @InjectView(R.id.employee_name)
    TextView employeeName;

    @InjectView(R.id.employee_roles)
    TextView employeeRoles;

    @InjectView(R.id.gridview)
    MyGridView gridview;

    public static String ppid;
    private String branid;
    private String empid;

    View inflater;
    //准备好要显示的数据
    TextAdapter adapter;
    public ArrayList<GridViewDetail> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygridview);
        new CommLogin(this).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
        ButterKnife.inject(this);
        list = new ArrayList();
        //未绑定
        if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
            list.add(new GridViewDetail("预约查询", R.mipmap.yy));
            list.add(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
        }
        //绑定了
        else {
            list.add(new GridViewDetail("门店当日服务查询", R.mipmap.mddrfw));
            list.add(new GridViewDetail("预约查询", R.mipmap.yy));
            list.add(new GridViewDetail("会员137管理", R.mipmap.hy));
            list.add(new GridViewDetail("满意度得分", R.mipmap.myd));
            list.add(new GridViewDetail("业绩统计", R.mipmap.yj));
            list.add(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
        }
        //判断权限
        Set<String> rights = Config.getCachedBrandRights(HomeActivity.this);
//        门店当日服务查询权限没有
        if (!rights.contains("PH004")) {
            list.remove(new GridViewDetail("门店当日服务查询", R.mipmap.mddrfw));
        }
        //满意度得分权限没有
        if (!rights.contains("PH001")) {
            list.remove(new GridViewDetail("满意度得分", R.mipmap.myd));
        }
        //业绩统计权限没有
        if (!rights.contains("PH002")) {
            list.remove(new GridViewDetail("业绩统计", R.mipmap.yj));
        }
        //会员137管理权限没有
        if (!rights.contains("PH005")) {
            list.remove(new GridViewDetail("会员137管理", R.mipmap.hy));
        }
        //登录体验
        if (Config.getCachedIsexp(this) != null) {
            list.remove(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
        }
        adapter = new TextAdapter(this, list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= list.size()) {
                    return;
                }
                //未绑定
                if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
                    intent2Aty(E0_EmployeeBindAty.class);
                    finish();
                    return;
                }
                GridViewDetail obj = list.get(position);
                //门店当日服务查询
                String des = obj.desc;
                if (des.equals("门店当日服务查询")) {
                    Set<String> rights = Config.getCachedBrandCodes(HomeActivity.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent brand = new Intent(HomeActivity.this, E2_BrandServicetppAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    } else {
                        Intent brand = new Intent(HomeActivity.this, E2_TodayServiceCheckAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                //预约查询
                else if (des.equals("预约查询")) {
                    Set<String> rights = Config.getCachedBrandCodes(HomeActivity.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent store = new Intent(HomeActivity.this, E2_StoreOrderQueryAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent order = new Intent(HomeActivity.this, E2_OrderQueryAty.class);
                        order.putExtra("ppid", ppid);
                        order.putExtra("branid", branid);
                        startActivity(order);
                    }
                }
                //满意度得分
                else if (des.equals("满意度得分")) {
                    Set<String> rights = Config.getCachedBrandCodes(HomeActivity.this);
                    int br_id = Integer.parseInt(branid);
                    String date_str;
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date_str = sdf.format(date);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent intent = new Intent(HomeActivity.this, E2_StoreFatisfyAty_New.class);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", branid);
                        intent.putExtra("time", date_str);
                        intent.putExtra("right", "one");
                        startActivity(intent);
                    } else {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,br_id,
//                                HomeActivity.this,E2_StoreFatisfyAty_New.class);
                        Intent intent = new Intent(HomeActivity.this, E2_StoreFatisfyAty_New.class);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", branid);
                        intent.putExtra("time", date_str);
                        intent.putExtra("right", "two");
                        startActivity(intent);
                    }
                }
                //业绩统计
                else if (des.equals("业绩统计")) {
                    Set<String> rights = Config.getCachedBrandCodes(HomeActivity.this);
                    int br_id = Integer.parseInt(branid);
                    String date_str;
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date_str = sdf.format(date);
                    if (Utility.getInstance().CheckStroeRight(rights) && br_id > 0) {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,branid,
//                        HomeActivity.this,E2_CommPerfBranchAty_New.class);
                        Intent intent = new Intent(HomeActivity.this, E2_CommPerfBranchAty_New.class);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", branid);
                        intent.putExtra("time", date_str);
                        intent.putExtra("right", "one");
                        startActivity(intent);
                    } else if (Utility.getInstance().isMlsorGw(rights) && br_id > 0) {
                        Intent mls = new Intent(HomeActivity.this, E3_CommPerfEmployeeAty_New.class);
                        mls.putExtra("empid", Config.getCachedBrandEmpid(HomeActivity.this).toString());
                        mls.putExtra("str", "this is test!");
                        startActivity(mls);
                    } else {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,br_id,
//                                HomeActivity.this,E2_CommPerfBranchAty_New.class);
                        Intent intent = new Intent(HomeActivity.this, E2_CommPerfBranchAty_New.class);
                        intent.putExtra("ppid", ppid);
                        intent.putExtra("branid", branid);
                        intent.putExtra("time", date_str);
                        intent.putExtra("right", "two");
                        startActivity(intent);
                    }
                }
                //会员137管理
                else if (des.equals("会员137管理")) {
                    Intent brand = new Intent(HomeActivity.this, E2_Client137ManagerAty.class);
                    brand.putExtra("ppid", ppid);
                    brand.putExtra("branid", branid);
                    startActivity(brand);
                }
                //员工账号管理
                else if (des.equals("员工账号管理")) {
                    intent2Aty(E1_EmployeeMoreAty.class);
//                    finish();
                }
            }
        });


    }

    boolean flag = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (checkNetAddToast()) {
            if (Config.getCachedBrandEmpid(this) != null) {
                if (flag || A0_HeadPortraitAty.isChanged) {
                    if (Config.getCachedIsemploye(MzbApplication.getmContext())) {
                        GetEmployeInfo(Config.getCachedBrandEmpid(this).toString());
                    }
                }
            }
        } else {
            View grounds = View.inflate(this, R.layout.ground_nonet_back, null);
            groundes.addView(grounds);
        }
        flag = false;
        imageLoader.displayImage(Config.getCachedPortrait(this), employeeImg);
        if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
            list.clear();
            list.add(new GridViewDetail("预约查询", R.mipmap.yy));
            list.add(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
            gridview.setAdapter(new TextAdapter(this, list));
            employeeName.setText("未绑定");
            employeeRoles.setText("");
            employeeImg.setImageResource(R.mipmap.mr);
        }
    }

    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(HomeActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
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

    @OnClick({R.id.titlebar_iv_middle, R.id.titlebar_iv_right})
    public void onDo(View v) {
        //未绑定
        if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
            showToast("请绑定客户档案");
            intent2Aty(E0_EmployeeBindAty.class);
            finish();
            return;
        }
        switch (v.getId()) {
            //消息
            case R.id.titlebar_iv_middle: {
                intent2Aty(E0_InfoAty.class);
                break;
            }
            //more
            case R.id.titlebar_iv_right: {
                intent2Aty(B0_ProfileActivity.class);
                break;
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                if (Config.getCachedIsexp(this) != null) {
                    //清空体验数据
                    Config.cachedIsexp(this, null);
                    Config.cachedPortrait(HomeActivity.this, null);
                    Config.cachedName(HomeActivity.this, null);
                    Config.cachedBrandAccid(HomeActivity.this, null);
                    Config.cachedBrandEmpid(HomeActivity.this, null);
                    Config.cachedBrandCodes(HomeActivity.this, null);
                    Config.cachedBrandRights(HomeActivity.this, null);
                    Config.cachedUid(HomeActivity.this, null);
                    Config.cachedToken(HomeActivity.this, null);
                    Config.cachedIsemploye(HomeActivity.this, null);
                }
                MzbApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}