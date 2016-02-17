package com.rwxlicai.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rwxlicai.R;
import com.rwxlicai.base.BaseActivity;
import com.rwxlicai.base.BaseApplication;
import com.rwxlicai.fragment.ContentFragment;
import com.rwxlicai.fragment.FundFragment;
import com.rwxlicai.fragment.InfoFragment;
import com.rwxlicai.fragment.InviteFragment;
import com.rwxlicai.fragment.MoreFragment;
import com.rwxlicai.fragment.ProfileFragment;
import com.rwxlicai.utils.Config;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer)
    View mDrawer;
    @InjectView(R.id.person_info)
    View person_info;
    @InjectView(R.id.invest_fg)
    View invest_fg;
    @InjectView(R.id.profile_fg)
    View profile_fg;
    @InjectView(R.id.fund_fg)
    View fund_fg;
    @InjectView(R.id.sign_fg)
    View sign_fg;
    @InjectView(R.id.invite_fg)
    View invite_fg;
    @InjectView(R.id.more_fg)
    View more_fg;
    @InjectView(R.id.store_phone)
    View store_phone;
    @InjectView(R.id.call_text)
    TextView call_text;
    @InjectView(R.id.profile_img)
    CircleImageView profile_img;
    @InjectView(R.id.profile_name)
    TextView profile_name;


    private ArrayList<String> menuLists;
    private ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private ActionBar actionBar;
    MaterialDialog mMaterialDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTitle = (String) getTitle();
        menuLists = new ArrayList<>();
        actionBar = getSupportActionBar();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle("请选择");
                invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //开启ActionBar上APP ICON的功能
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        setDefaultFragment();
    }

    @Override
    protected void initData() {
        profile_name.setText(Config.getCachedPhone(MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        profile_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageLoader.displayImage(Config.getCachedPortrait(MainActivity.this), profile_img);
    }

    @OnClick({R.id.invest_fg, R.id.profile_fg, R.id.fund_fg, R.id.sign_fg, R.id.invite_fg,
            R.id.more_fg,R.id.store_phone,R.id.person_info})
    public void clickAction(View view) {
        FragmentManager fm = getFragmentManager();
        switch (view.getId()) {
            case R.id.person_info:
                if (Config.getLogin(MainActivity.this)){
                    ProfileFragment profileFragment = new ProfileFragment();
                    fm.beginTransaction().replace(R.id.content_frame, profileFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "我的账户";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }
                break;
            case R.id.store_phone:
                mMaterialDialog = new MaterialDialog(this);
                mMaterialDialog.setTitle("拨打电话");
                mMaterialDialog.setMessage("是否呼叫？");
                mMaterialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Uri uri = Uri.parse("tel:" + call_text.getText().toString());
                        Intent it = new Intent();   //实例化Intent
                        it.setAction(Intent.ACTION_CALL);   //指定Action
                        it.setData(uri);   //设置数据
                        startActivity(it);//启动Acitivity
                    }
                });
                mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
                mMaterialDialog.show();
//                mDrawerLayout.closeDrawer(mDrawer);
                break;

            case R.id.invest_fg:
                ContentFragment contentFragment = new ContentFragment();
//                Bundle args = new Bundle();
//                args.putString("text", menuLists.get(position));
//                contentFragment.setArguments(args);
                fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                mDrawerLayout.closeDrawer(mDrawer);
                mTitle = "仁我行理财";
                break;
            case R.id.profile_fg:
                if (Config.getLogin(MainActivity.this)){
                    ProfileFragment profileFragment = new ProfileFragment();
                    fm.beginTransaction().replace(R.id.content_frame, profileFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "我的账户";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }
                break;

            case R.id.fund_fg:
                if (Config.getLogin(MainActivity.this)){
                    FundFragment fundFragment = new FundFragment();
                    fm.beginTransaction().replace(R.id.content_frame,fundFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "资金管理";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }
                break;

            case R.id.sign_fg:
//                SignFragment signFragment = new SignFragment();
//                fm.beginTransaction().replace(R.id.content_frame,signFragment).commit();
                if (Config.getLogin(MainActivity.this)){
                    InfoFragment infoFragment = new InfoFragment();
                    fm.beginTransaction().replace(R.id.content_frame,infoFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "站内消息";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }

                break;

            case R.id.invite_fg:
                if (Config.getLogin(MainActivity.this)){
                    InviteFragment inviteFragment = new InviteFragment();
                    fm.beginTransaction().replace(R.id.content_frame,inviteFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "邀请拿现金";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }
                break;

            case R.id.more_fg:
                if (Config.getLogin(MainActivity.this)){
                    MoreFragment moreFragment = new MoreFragment();
                    fm.beginTransaction().replace(R.id.content_frame,moreFragment).commit();
                    mDrawerLayout.closeDrawer(mDrawer);
                    mTitle = "更多";
                }else {
                    startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                    mDrawerLayout.closeDrawer(mDrawer);
                }
                break;
            default:
                break;
        }
    }


    private void setDefaultFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ContentFragment mContentFragment = new ContentFragment();
        transaction.replace(R.id.content_frame, mContentFragment);
        transaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
        menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //将ActionBar上的图标与Drawer结合起来
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_websearch:
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri uri = Uri.parse("http://rwxlicai.com/content/contentList/0/55.do");
//                intent.setData(uri);
//                startActivity(intent);

//                startActivity(new Intent(MainActivity.this, GestureActivity.class));
                startActivity(new Intent(MainActivity.this, A0_LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //需要将ActionDrawerToggle与DrawerLayout的状态同步
        //将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
// long arg3) {
//        // 动态插入一个Fragment到FrameLayout当中
//        Fragment contentFragment = new ContentFragment();
//        Bundle args = new Bundle();
//        args.putString("text", menuLists.get(position));
//        contentFragment.setArguments(args);
//
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
//
//        mDrawerLayout.closeDrawer(mDrawer);
//    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
