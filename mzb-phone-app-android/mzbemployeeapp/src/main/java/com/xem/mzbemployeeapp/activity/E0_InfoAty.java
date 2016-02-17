package com.xem.mzbemployeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.adapter.E1_InfoFgAdapter;
import com.xem.mzbemployeeapp.fragment.E1_infoFg01;
import com.xem.mzbemployeeapp.fragment.E1_infoFg02;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xuebing on 15/6/29.
 */
public class E0_InfoAty extends FragmentActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.viewpage)
    NoScrollViewPager viewPagers;

    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_profile_info);
        new TitleBuilder(this).setTitleText("消息中心").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        if (getIntent().getStringExtra("flag") == null){
            flag = "3";
        }else {
            flag = getIntent().getStringExtra("flag");
        }

        setlinearLayouts();
        settextview();

        List<Fragment> totalFragement = new ArrayList<Fragment>();
        totalFragement.add(new E1_infoFg01());
        totalFragement.add(new E1_infoFg02());

        viewPagers.setAdapter(new E1_InfoFgAdapter(getSupportFragmentManager(), totalFragement));
        //设置显示那页
        viewPagers.setCurrentItem(0);

        viewPagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                Intent mainIntent;
                if (Config.getCachedIsexp(this) != null){
                   mainIntent = new Intent(E0_InfoAty.this,ExperienceActivity.class);
                }else{
                   mainIntent = new Intent(E0_InfoAty.this,HomeActivity.class);
                }
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("flag",1);
                startActivity(mainIntent);
                finish();
                break;

        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent mIntent = new Intent(E0_InfoAty.this,MainTabAty.class);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            mIntent.putExtra("flag",1);
//            startActivity(mIntent);
//            finish();
//            return true;
//        }
//        return true;
//
//    }

    /** 点击linerlayout实现切换fragment的效果 */
    public void LayoutOnclick(View v) {
        // 每次点击都重置linearLayouts的背景、textViews字体颜色
        switch (v.getId()) {
            case R.id.lay1:
                resetlaybg();
                viewPagers.setCurrentItem(0);
                linearLayouts[0].setBackgroundResource(R.mipmap.tab2);
                textViews[0].setTextColor(getResources().getColor(R.color.color_deep_text));
                break;

            case R.id.lay2:
                resetlaybg();
                viewPagers.setCurrentItem(1);
                linearLayouts[1].setBackgroundResource(R.mipmap.tab2);
                textViews[1].setTextColor(getResources().getColor(R.color.color_deep_text));
                break;
        }
    }


    /** 初始化linerlayout */
    public void setlinearLayouts() {
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = (LinearLayout) findViewById(R.id.lay1);
        linearLayouts[1] = (LinearLayout) findViewById(R.id.lay2);
        if (flag.startsWith("3")){
            linearLayouts[0].setBackgroundResource(R.mipmap.tab2);
            linearLayouts[1].setBackgroundResource(R.mipmap.tab1);
        }else {
            linearLayouts[0].setBackgroundResource(R.mipmap.tab1);
            linearLayouts[1].setBackgroundResource(R.mipmap.tab2);
        }


    }

    /** 初始化textview */
    public void settextview() {
        textViews = new TextView[3];
        textViews[0] = (TextView) findViewById(R.id.fratext1);
        textViews[1] = (TextView) findViewById(R.id.fratext2);
        if (flag.startsWith("3")){
            textViews[1].setTextColor(getResources().getColor(R.color.color_deep_text));
        }else {
            textViews[0].setTextColor(getResources().getColor(R.color.color_deep_text));
        }
    }

    /** 重置linearLayouts、textViews */
    public void resetlaybg() {
        for (int i = 0; i < 2; i++) {
            textViews[i].setTextColor(getResources().getColor(R.color.color_middle_text));
            linearLayouts[i].setBackgroundResource(R.mipmap.tab1);
        }
    }
}
