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
import com.xem.mzbemployeeapp.adapter.B0_Info_Fragment_Adapter;
import com.xem.mzbemployeeapp.fragment.B0_Info_Fragment01;
import com.xem.mzbemployeeapp.fragment.B0_Info_Fragment02;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by xuebing on 15/10/22.
 */
public class B0_InfoAty extends FragmentActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.viewpage)
    NoScrollViewPager viewPagers;
    @InjectView(R.id.fratext1)
    TextView frag_text1;
    @InjectView(R.id.fratext2)
    TextView frag_text2;

    private LinearLayout[] linearLayouts;
    private List<Fragment> totalFragement;
    private TextView[] textViews;
    private String flag = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initView() {
        setContentView(R.layout.b0_info_activity);
        new TitleBuilder(this).setTitleText("消息中心").setLeftImage(R.mipmap.top_view_back);
        JPushInterface.resumePush(getApplicationContext());
        ButterKnife.inject(this);
        frag_text1.setText("服务消息");
        frag_text2.setText("系统消息");
        setlinearLayouts();
        settextview();

        totalFragement = new ArrayList<>();
        totalFragement.add(new B0_Info_Fragment01());
        totalFragement.add(new B0_Info_Fragment02());

    }



    protected void initData() {

        viewPagers.setAdapter(new B0_Info_Fragment_Adapter(getSupportFragmentManager(), totalFragement));
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
    public void clickAction(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                Intent mainIntent = new Intent(B0_InfoAty.this,HomeActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("flag",1);
                startActivity(mainIntent);
                finish();
                break;

        }
    }

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
