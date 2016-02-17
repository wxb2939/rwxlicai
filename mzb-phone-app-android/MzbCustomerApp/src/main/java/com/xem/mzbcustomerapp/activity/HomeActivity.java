package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.internal.Utils;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.adapter.TextAdapter;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.base.BaseApplication;
import com.xem.mzbcustomerapp.entity.GridViewDetail;
import com.xem.mzbcustomerapp.entity.PrepareDate;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.other.zxing.activity.CaptureActivity;
import com.xem.mzbcustomerapp.utils.CommLogin;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.DecodeUrl;
import com.xem.mzbcustomerapp.utils.UpDateManager;
import com.xem.mzbcustomerapp.utils.Utility;
import com.xem.mzbcustomerapp.view.HeadGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/25.
 */
public class HomeActivity extends BaseActivity {

    @InjectView(R.id.headergridview)
    HeadGridView headergridview;
    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.titlebar_iv_middle)
    ImageView titlebar_iv_middle;
    @InjectView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;

    private View headview;
    private int currentItem = 0; // 当前图片的索引号
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    ViewPager viewPager;
    private List<ImageView> imageViews;
    private ScheduledExecutorService scheduledExecutorService;

    private ArrayList<GridViewDetail> list;
    private TextAdapter adapter;


    @Override
    protected void initView() {
        setContentView(R.layout.home_activity);
        ButterKnife.inject(this);
        new UpDateManager(this).checkUpdate();
        ButterKnife.inject(this);
        list=new ArrayList();
        dots = new ArrayList<View>();
        headview =getLayoutInflater().from(HomeActivity.this).inflate(R.layout.home_headview,null);
        viewPager = (ViewPager) headview.findViewById(R.id.vp);
        dots.add(headview.findViewById(R.id.v_dot0));
        dots.add(headview.findViewById(R.id.v_dot1));
        dots.add(headview.findViewById(R.id.v_dot2));
        headergridview.addHeaderView(headview);
        initImage();
        new CommLogin(context).Login(Config.getCachedPhone(this), Config.getCachedPassword(this), application);
    }



    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_iv_middle, R.id.titlebar_iv_right})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left: //扫一扫
                if (application.getIsLogin()){
                    startActivityForResult(new Intent(HomeActivity.this, CaptureActivity.class), 0);
                }else {
                    intent2Aty(A0_LoginActivity.class);
                }
//                intent2Aty(HomeActivity.class);
                break;
            case R.id.titlebar_iv_middle://消息
                if (application.getIsLogin()){
                    intent2Aty(B0_InfoAty.class);
                }else {
                    intent2Aty(A0_LoginActivity.class);
                }

                break;
            case R.id.titlebar_iv_right://个人，更多
                if (application.getIsLogin()){
                    intent2Aty(B0_ProfileActivity.class);
                }else {
                    intent2Aty(A0_LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void initData() {
        list.add(new GridViewDetail("我的门店",R.mipmap.m_store));
        list.add(new GridViewDetail("预约",R.mipmap.m_order));
        list.add(new GridViewDetail("美丽档案",R.mipmap.m_beauty));
        list.add(new GridViewDetail("服务点评",R.mipmap.m_comment));
        list.add(new GridViewDetail("我的账户",R.mipmap.m_account));
        list.add(new GridViewDetail("优惠券",R.mipmap.m_conpon));
        adapter=new TextAdapter(this,list);
        headergridview.setAdapter(adapter);
        headergridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= list.size()){
                    return;
                }
                switch (position){
                    case 0://我的门店
                        if (application.getIsLogin()){
                            if (Config.getCachedBrandid(HomeActivity.this) == null) {
                                showToast("请先绑定客户档案，谢谢");
                            }else{
                                intent2Aty(B0_MyStoreActivity.class);
                            }
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }

                        break;

                    case 1://预约
                        if (application.getIsLogin()){
                            if (Config.getCachedBrandid(HomeActivity.this) == null) {
                                showToast("请先绑定客户档案，谢谢");
                            }else{
                                intent2Aty(B0_OrderActivity.class);
                            }
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }
                        break;

                    case 2://美丽档案
                        if (application.getIsLogin()){
                            if (Config.getCachedBrandid(HomeActivity.this) == null) {
                                showToast("请先绑定客户档案，谢谢");
                            }else{
                                intent2Aty(B0_BeautyFileActivity.class);
                            }
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }

                        break;

                    case 3://服务点评
                        if (application.getIsLogin()){
                            if (Config.getCachedBrandid(HomeActivity.this) == null) {
                                showToast("请先绑定客户档案，谢谢");
                            }else{
                                intent2Aty(B0_ServerCommentActivity.class);
                            }
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }
                        break;

                    case 4://我的账户
                        if (application.getIsLogin()){
                            if (Config.getCachedBrandid(HomeActivity.this) == null) {
                                showToast("请先绑定客户档案，谢谢");
                            }else{
                                intent2Aty(B0_MyAccountActivity.class);
                            }
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }

                        break;

                    case 5://优惠券
                        if (application.getIsLogin()){
                            intent2Aty(B0_PrivilegeActivity.class);
                        }else {
                            intent2Aty(A0_LoginActivity.class);
                        }

                        break;

                }


            }
        });
    }

    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
                TimeUnit.SECONDS);
        super.onStart();
    }


    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
            isExit = false;
        }
    };

    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    private boolean isExit = false;


    private void initImage() {
        imageResId = PrepareDate.getImg();
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.b0_index_cell, null);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        viewPager.setAdapter(new MzbPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private class MzbPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            // tv_title.setText(titles[position]);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                BaseApplication.getInstance().exit();
                return false;
            }
        }
        return true;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）

        switch (resultCode) {
            case -1:
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                String strDate = DecodeUrl.TruncateUrlPage(scanResult).substring(2);
                try {
                    byte[] strdata = Base64.decode(strDate.getBytes("UTF-8"), Base64.DEFAULT);
                    String string = new String(strdata, "UTF-8");

                    String[] sourceStrArray = string.split("/");
                    String operationNum = sourceStrArray[1];
                    JSONObject obj = new JSONObject(sourceStrArray[2]);
                    if (operationNum.equals("1")) {//签到
                        MemberSign(Config.getCachedUid(HomeActivity.this), obj.getString("PpId"), obj.getString("BranchId"));
                    } else if (operationNum.equals("3")) { //绑定
                        Config.cachedCustid(HomeActivity.this,obj.getString("CustId"));
                        MemberBound(Config.getCachedUid(HomeActivity.this), obj.getString("CustId"));
                    } else {
                        showToast("无效的二维码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast("无效的二维码");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    showToast("无效的二维码");
                }
                break;

            default:
                break;
        }

    }


    public void MemberBound(String uid, String custid) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("custid", custid);

        MzbHttpClient.ClientTokenPost(HomeActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.bind, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        showToast("绑定成功");
//                        startActivity(new Intent(MainActivity.this, SplashAty.class));
                        new CommLogin(HomeActivity.this).Login(Config.getCachedPhone(HomeActivity.this),
                                Config.getCachedPassword(HomeActivity.this), application);
//                        intent2Aty(MainActivity.class);
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


    public void MemberSign(String uid, String ppid, String branid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);
        params1.put("ppid", ppid);
        params1.put("branid", branid);

        MzbHttpClient.ClientTokenPost(HomeActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.signin, params1, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
//                    showToast(obj.getString("message"));
                    switch (obj.getInt("code")) {
                        case 0:
                            showToast("签到成功");
                            break;
                        case 1:
                            showToast("会员不存在");
                            break;
                        case 2:
                            showToast("没有关联该门店");
                            break;
                        case 3:
                            showToast("已经在店");
                            break;
                        default:
                            showToast("无效二维码");
                            break;
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
