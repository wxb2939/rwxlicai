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
import com.xem.mzbemployeeapp.entity.ExperEmploye;
import com.xem.mzbemployeeapp.entity.GridViewDetail;
import com.xem.mzbemployeeapp.entity.InfoData;
import com.xem.mzbemployeeapp.net.NetCallBack;
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
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2015/11/19.
 */
public class ExperienceActivity extends MzbActivity{

    @InjectView(R.id.emp_ground)
    RelativeLayout groundes;

    @InjectView(R.id.gridview)
    MyGridView gridview;

    @InjectView(R.id.employee_img)
    ImageView employeeImg;

    @InjectView(R.id.employee_name)
    TextView employeeName;

    @InjectView(R.id.employee_roles)
    TextView employeeRoles;


    public static String ppid;
    private String branid;
    private String empid;

    View inflater;
    //准备好要显示的数据
    TextAdapter adapter;
    ArrayList<GridViewDetail> list;
    public ExperEmploye employe=A0_ExpAty.employ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_layout);
        MzbApplication.getInstance().addActivity(this);
//      new TitleBuilder(this).setTitleText("美之伴").setRightImage(R.mipmap.news).setMoreImage(R.mipmap.m_more);
        ButterKnife.inject(this);
        list=new ArrayList();
        //判断权限
        List<String> rights = A0_ExpAty.employ.getRights();
        if (rights != null){
            //未绑定
            if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
                list.add(new GridViewDetail("预约查询",R.mipmap.yy));
                list.add(new GridViewDetail("员工账号管理",R.mipmap.ygzh));
            }
            //绑定了
            else{
                list.add(new GridViewDetail("门店当日服务查询",R.mipmap.mddrfw));
                list.add(new GridViewDetail("预约查询",R.mipmap.yy));
                list.add(new GridViewDetail("会员137管理",R.mipmap.hy));
                list.add(new GridViewDetail("满意度得分",R.mipmap.myd));
                list.add(new GridViewDetail("业绩统计",R.mipmap.yj));
                list.add(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
            }

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
            //员工账号管理没有
            if (Config.getCachedIsexp(this) != null) {
                list.remove(new GridViewDetail("员工账号管理", R.mipmap.ygzh));
            }
        }else{
            list.add(new GridViewDetail("预约查询",R.mipmap.yy));
        }

        if (checkNetAddToast()) {
            imageLoader.displayImage(Config.getCachedPortrait(ExperienceActivity.this), employeeImg);
            employeeName.setText(employe.getName());
            employeeRoles.setText(employe.getPosition());
            ppid =employe.getPpid()+"";
            branid =employe.getBranid()+"";
            empid = employe.getEmpid()+"";

        } else {
            View grounds = View.inflate(this, R.layout.ground_nonet_back, null);
            groundes.addView(grounds);
        }

        adapter=new TextAdapter(this,list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= list.size()){
                    return;
                }
                //未绑定
                if (!Config.getCachedIsemploye(MzbApplication.getmContext())) {
                    intent2Aty(E0_EmployeeBindAty.class);
                    finish();
                    return;
                }
                GridViewDetail obj=list.get(position);
                //门店当日服务查询
                String des=obj.desc;
                if (des.equals("门店当日服务查询")){
                    Set<String> rights = Config.getCachedBrandCodes(ExperienceActivity.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent brand = new Intent(ExperienceActivity.this, E2_BrandServicetppAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    } else {
                        Intent brand = new Intent(ExperienceActivity.this, E2_TodayServiceCheckAty.class);
                        brand.putExtra("ppid", ppid);
                        brand.putExtra("branid", branid);
                        startActivity(brand);
                    }
                }
                //预约查询
                else if(des.equals("预约查询")){
                    Set<String> rights = Config.getCachedBrandCodes(ExperienceActivity.this);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
                        Intent store = new Intent(ExperienceActivity.this, E2_StoreOrderQueryAty.class);
                        store.putExtra("ppid", ppid);
                        store.putExtra("branid", branid);
                        startActivity(store);
                    } else {
                        Intent order = new Intent(ExperienceActivity.this, E2_OrderQueryAty.class);
                        order.putExtra("ppid", ppid);
                        order.putExtra("branid", branid);
                        startActivity(order);
                    }
                }
                //满意度得分
                else if(des.equals("满意度得分")){
                    Set<String> rights = Config.getCachedBrandCodes(ExperienceActivity.this);
                    int br_id=Integer.parseInt(branid);
                    String date_str;
                    Date date=new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date_str=sdf.format(date);
                    if (Utility.getInstance().CheckStroeRight(rights)) {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,branid,
//                                ExperienceActivity.this,E2_StoreFatisfyAty_New.class);
                        Intent intent=new Intent(ExperienceActivity.this, E2_StoreFatisfyAty_New.class);
                        intent.putExtra("ppid",ppid);
                        intent.putExtra("branid",branid);
                        intent.putExtra("time",date_str);
                        intent.putExtra("right","one");
                        startActivity(intent);
                    } else {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,br_id,
//                                ExperienceActivity.this,E2_StoreFatisfyAty_New.class);
                        Intent intent=new Intent(ExperienceActivity.this, E2_StoreFatisfyAty_New.class);
                        intent.putExtra("ppid",ppid);
                        intent.putExtra("branid",branid);
                        intent.putExtra("time",date_str);
                        intent.putExtra("right","two");
                        startActivity(intent);
                    }
                }
                //业绩统计
                else if(des.equals("业绩统计")){
                    Set<String> rights = Config.getCachedBrandCodes(ExperienceActivity.this);
                    int br_id=Integer.parseInt(branid);
                    String date_str;
                    Date date=new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date_str=sdf.format(date);
                    if (Utility.getInstance().CheckStroeRight(rights) && br_id > 0) {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,branid,
//                                ExperienceActivity.this,E2_CommPerfBranchAty_New.class);
                        Intent intent=new Intent(ExperienceActivity.this, E2_CommPerfBranchAty_New.class);
                        intent.putExtra("ppid",ppid);
                        intent.putExtra("branid",branid);
                        intent.putExtra("time",date_str);
                        intent.putExtra("right","one");
                        startActivity(intent);
                    } else if (Utility.getInstance().isMlsorGw(rights) && br_id > 0) {
                        Intent mls = new Intent(ExperienceActivity.this, E3_CommPerfEmployeeAty_New.class);
                        mls.putExtra("empid", Config.getCachedBrandEmpid(ExperienceActivity.this).toString());
                        mls.putExtra("str", "this is test!");
                        startActivity(mls);
                    } else {
//                        RightUtil.getInstance().getPerfPpofDayData(ppid,date_str,br_id,
//                                ExperienceActivity.this,E2_CommPerfBranchAty_New.class);
                        Intent intent=new Intent(ExperienceActivity.this, E2_CommPerfBranchAty_New.class);
                        intent.putExtra("ppid",ppid);
                        intent.putExtra("branid",branid);
                        intent.putExtra("time",date_str);
                        intent.putExtra("right","two");
                        startActivity(intent);
                    }
                }
                //会员137管理
                else if(des.equals("会员137管理")){
                    Intent brand = new Intent(ExperienceActivity.this, E2_Client137ManagerAty.class);
                    brand.putExtra("ppid", ppid);
                    brand.putExtra("branid", branid);
                    startActivity(brand);
                }
                //员工账号管理
                else if(des.equals("员工账号管理")){
                    intent2Aty(E1_EmployeeMoreAty.class);
//                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginOrRegActivity.str=null;
        if (A0_HeadPortraitAty.isChanged){
            if (checkNetAddToast()) {
                imageLoader.displayImage(Config.getCachedPortrait(ExperienceActivity.this), employeeImg);
                employeeName.setText(employe.getName());
                employeeRoles.setText(employe.getPosition());
                ppid =employe.getPpid()+"";
                branid =employe.getBranid()+"";
                empid = employe.getEmpid()+"";

            } else {
                View grounds = View.inflate(this, R.layout.ground_nonet_back, null);
                groundes.addView(grounds);
            }
        }
    }

    public void GetEmployeInfo(String empids) {
        RequestParams params1 = new RequestParams();
        params1.put("empid", empids);

        RequestUtils.ClientTokenPost(ExperienceActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_INFO, params1, new NetCallBack(this) {
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
//                        showToast(obj.getString("message"));
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

    @OnClick({R.id.titlebar_iv_middle,R.id.titlebar_iv_right,R.id.switch_text})
    public void onDo(View v) {
        //未绑定
        if (!Config.getCachedIsemploye(MzbApplication.getmContext()) && v.getId() != R.id.switch_text) {
            showToast("请绑定客户档案");
            intent2Aty(E0_EmployeeBindAty.class);
            return;
        }
        switch (v.getId()){
            //消息
            case R.id.titlebar_iv_middle: {
                intent2Aty(E0_InfoAty.class);
                break;
            }
            //more
            case R.id.titlebar_iv_right:{
                intent2Aty(B0_ProfileActivity.class);
                break;
            }
            //角色切换
            case R.id.switch_text:{
                intent2Aty(A0_ExpAty.class);
                break;
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //清空体验数据
                Config.cachedIsexp(this, null);
                Config.cachedUid(this, null);
                Config.cachedToken(this, null);
                Config.cachedPortrait(this, null);
                Config.cachedName(this, null);
                Config.cachedBrandAccid(this, null);
                Config.cachedBrandEmpid(this, null);
                Config.cachedBrandCodes(this, null);
                Config.cachedBrandRights(this, null);
                MzbApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}