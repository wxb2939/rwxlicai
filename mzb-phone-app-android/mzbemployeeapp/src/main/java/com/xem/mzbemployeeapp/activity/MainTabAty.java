import android.support.v4.app.FragmentActivity;

//package com.xem.mzbemployeeapp.activity;
//
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.xem.mzbemployeeapp.R;
//import com.xem.mzbemployeeapp.receiver.MzbReceiver;
//
//import java.util.ArrayList;
//
//import cn.jpush.android.api.JPushInterface;
//
///**
// * Created by xuebing on 15/6/3.
// */
public class MainTabAty extends FragmentActivity {
//
//    //定义四个fragment
//    private B0_indexFragment b0_indexFg;
//    private C0_StoreFragment c0_storeFg;
//    private D0_FindFragment d0_findFg;
//    private E0_ProfileFragment e0_profileFg;
//
//    //定义一个ViewPage容器
//    private ViewPager mPager;
//    private ArrayList<Fragment> fragmentsList;
//    private AtyMainTabAdapter mAdapter;
//
//    // 下面每个Layout对象
//    private RelativeLayout weixin_layout;
//    private RelativeLayout tongxunlu_layout;
//    private RelativeLayout faxian_layout;
//    private RelativeLayout me_layout;
//    // 依次获得ImageView与TextView
//    private ImageView weixin_img;
//    private ImageView tongxunlu_img;
//    private ImageView faxian_img;
//    private ImageView me_img;
//    private TextView weixin_txt;
//    private TextView tongxunlu_txt;
//    private TextView faxian_txt;
//    private TextView me_txt;
//    // 定义颜色值
//    private int Gray = 0xFF666666;
//    private int Green = 0xFFbc3172;
//    // 定义FragmentManager对象
//    public FragmentManager fManager;
//    // 定义一个Onclick全局对象
//    public MyOnClick myclick;
//    public MyPageChangeListener myPageChange;
//
//    private MzbReceiver mMzbReceiver;
//    private int flag = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.maintab_aty);
//        JPushInterface.resumePush(getApplicationContext());
//        if (getIntent() != null) {
//            flag = getIntent().getIntExtra("flag", 0);
//        }
//        fManager = getSupportFragmentManager();
//        new UpDateManager(this).checkUpdate();
//        initViewPager();
//        initViews();
//        initState(flag);
//
//    }
//
//
//    protected void onResume() {
//        super.onResume();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//    }
//
//    protected void onPause() {
//        super.onPause();
////        JPushInterface.onPause(this);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    private void initViews() {
//        myclick = new MyOnClick();
//        myPageChange = new MyPageChangeListener();
//        mPager = (ViewPager) findViewById(R.id.vPager);
//        weixin_layout = (RelativeLayout) findViewById(R.id.weixin_layout);
//        tongxunlu_layout = (RelativeLayout) findViewById(R.id.tongxunlu_layout);
//        faxian_layout = (RelativeLayout) findViewById(R.id.faxian_layout);
//        me_layout = (RelativeLayout) findViewById(R.id.me_layout);
//        weixin_img = (ImageView) findViewById(R.id.weixin_img);
//        tongxunlu_img = (ImageView) findViewById(R.id.tongxunlu_img);
//        faxian_img = (ImageView) findViewById(R.id.faxian_img);
//        me_img = (ImageView) findViewById(R.id.me_img);
//        weixin_txt = (TextView) findViewById(R.id.weixin_txt);
//        tongxunlu_txt = (TextView) findViewById(R.id.tongxunlu_txt);
//        faxian_txt = (TextView) findViewById(R.id.faxian_txt);
//        me_txt = (TextView) findViewById(R.id.me_txt);
//        mPager.setAdapter(mAdapter);
//        mPager.setOnPageChangeListener(myPageChange);
//        weixin_layout.setOnClickListener(myclick);
////        tongxunlu_layout.setOnClickListener(myclick);
////        faxian_layout.setOnClickListener(myclick);
//        me_layout.setOnClickListener(myclick);
//    }
//
//    private void initViewPager() {
//        fragmentsList = new ArrayList<Fragment>();
//        b0_indexFg = new B0_indexFragment();
//        c0_storeFg = new C0_StoreFragment();
//        d0_findFg = new D0_FindFragment();
//        e0_profileFg = new E0_ProfileFragment();
//        fragmentsList.add(b0_indexFg);
////        fragmentsList.add(c0_storeFg);
////        fragmentsList.add(d0_findFg);
//        fragmentsList.add(e0_profileFg);
//        mAdapter = new AtyMainTabAdapter(fManager, fragmentsList);
//    }
//
//    // 定义一个设置初始状态的方法
//    private void initState(final int strFlag) {
//        if (strFlag == 1) {
//            me_img.setImageResource(R.mipmap.ahn);
//            me_txt.setTextColor(Green);
//            mPager.setCurrentItem(1);
//        } else {
//            weixin_img.setImageResource(R.mipmap.ahj);
//            weixin_txt.setTextColor(Green);
//            mPager.setCurrentItem(0);
//        }
//    }
//
//
//    public class MyOnClick implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            clearChioce();
//            iconChange(view.getId());
//        }
//    }
//
//    // 建立一个清空选中状态的方法
//    public void clearChioce() {
//        weixin_img.setImageResource(R.mipmap.ahk);
//        weixin_txt.setTextColor(Gray);
//        tongxunlu_img.setImageResource(R.mipmap.ahi);
//        tongxunlu_txt.setTextColor(Gray);
//        faxian_img.setImageResource(R.mipmap.ahm);
//        faxian_txt.setTextColor(Gray);
//        me_img.setImageResource(R.mipmap.aho);
//        me_txt.setTextColor(Gray);
//    }
//
//    // 定义一个底部导航栏图标变化的方法
//    public void iconChange(int num) {
//        switch (num) {
//            case R.id.weixin_layout:
//            case 0:
//                weixin_img.setImageResource(R.mipmap.ahj);
//                weixin_txt.setTextColor(Green);
//                mPager.setCurrentItem(0);
//                break;
//            /*case R.id.tongxunlu_layout:
//            case 1:
//                tongxunlu_img.setImageResource(R.mipmap.ahh);
//                tongxunlu_txt.setTextColor(Green);
//                mPager.setCurrentItem(1);
//                break;
//            case R.id.faxian_layout:
//            case 2:
//                faxian_img.setImageResource(R.mipmap.ahl);
//                faxian_txt.setTextColor(Green);
//                mPager.setCurrentItem(2);
//                break;*/
//            case R.id.me_layout:
//            case 1:
//                me_img.setImageResource(R.mipmap.ahn);
//                me_txt.setTextColor(Green);
//                mPager.setCurrentItem(1);
//                break;
//        }
//
//    }
//
//    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            if (arg0 == 2) {
//                int i = mPager.getCurrentItem();
//                clearChioce();
//                iconChange(i);
//            }
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//
//        @Override
//        public void onPageSelected(int index) {
//        }
//
//    }
//
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            isExit = false;
//        }
//    };
//
//    private boolean isExit = false;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (isExit == false) {
//                isExit = true;
//                Toast.makeText(getApplicationContext(), "再按一次退出",
//                        Toast.LENGTH_SHORT).show();
//                handler.sendEmptyMessageDelayed(0, 3000);
//                return true;
//            } else {
//                finish();
//                return false;
//            }
//        }
//        return true;
//    }
//
}
