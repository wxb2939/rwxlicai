package com.xem.mzbcustomerapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.adapter.B0_Info_Fragment_Adapter;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.fragment.B0_Info_Fragment01;
import com.xem.mzbcustomerapp.fragment.B0_Info_Fragment02;
import com.xem.mzbcustomerapp.fragment.B0_Privilege_Fragment01;
import com.xem.mzbcustomerapp.fragment.B0_Privilege_Fragment02;
import com.xem.mzbcustomerapp.fragment.B0_Privilege_Fragment03;
import com.xem.mzbcustomerapp.view.NoScrollViewPager;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class B0_PrivilegeActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.viewpage)
    NoScrollViewPager viewPagers;
    @InjectView(R.id.fratext1)
    TextView frag_text1;
    @InjectView(R.id.fratext2)
    TextView frag_text2;
    @InjectView(R.id.fratext3)
    TextView frag_text3;

    private LinearLayout[] linearLayouts;
    private List<Fragment> totalFragement;
    private TextView[] textViews;

    @Override
    protected void initView() {
        setContentView(R.layout.b0_privilege_activity);
        new TitleBuilder(this).setTitleText("优惠券").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        frag_text1.setText("未使用");
        frag_text2.setText("已使用");
        frag_text3.setText("已过期");

        setlinearLayouts();
        settextview();

        totalFragement = new ArrayList<Fragment>();
        totalFragement.add(new B0_Privilege_Fragment01());
        totalFragement.add(new B0_Privilege_Fragment02());
        totalFragement.add(new B0_Privilege_Fragment03());
    }

    @Override
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
                finish();
                break;

        }
    }
    /**
     * 点击linerlayout实现切换fragment的效果
     */
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
            case R.id.lay3:
                resetlaybg();
                viewPagers.setCurrentItem(2);
                linearLayouts[2].setBackgroundResource(R.mipmap.tab2);
                textViews[2].setTextColor(getResources().getColor(R.color.color_deep_text));
                break;
        }
    }

    /**
     * 初始化linerlayout
     */
    public void setlinearLayouts() {
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = (LinearLayout) findViewById(R.id.lay1);
        linearLayouts[1] = (LinearLayout) findViewById(R.id.lay2);
        linearLayouts[2] = (LinearLayout) findViewById(R.id.lay3);
        linearLayouts[0].setBackgroundResource(R.mipmap.tab2);
        linearLayouts[1].setBackgroundResource(R.mipmap.tab1);
        linearLayouts[2].setBackgroundResource(R.mipmap.tab1);


    }

    /**
     * 初始化textview
     */
    public void settextview() {
        textViews = new TextView[3];
        textViews[0] = (TextView) findViewById(R.id.fratext1);
        textViews[1] = (TextView) findViewById(R.id.fratext2);
        textViews[2] = (TextView) findViewById(R.id.fratext3);
        textViews[0].setTextColor(getResources().getColor(R.color.color_deep_text));
    }

    /**
     * 重置linearLayouts、textViews
     */
    public void resetlaybg() {
        for (int i = 0; i < 3; i++) {
            textViews[i].setTextColor(getResources().getColor(R.color.color_middle_text));
            linearLayouts[i].setBackgroundResource(R.mipmap.tab1);
        }
    }


}
