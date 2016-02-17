package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BeautiFilePictureData;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.utils.Utility;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/3.
 */
public class B1_BeautyImgActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.viewGroup)
    ViewGroup group;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.tv_info)
    TextView tv_info;
    @InjectView(R.id.picture)
    View picture;

    private ImageView[] tips; //装点点的ImageView数组
    private ImageView[] mImageViews; //装ImageView数组
    private int[] imgIdArray; //图片资源id
    private List<String> imglist = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<BeautiFilePictureData> pictureList = new ArrayList<>();
    int screenWidth,screenHeight;
    @Override
    protected void initView() {
        setContentView(R.layout.b1_beautyimg_activity);
        new TitleBuilder(this).setTitleText("照片对比").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        picture.setLayoutParams(Utility.getInstance().getLayoutParams(picture, B1_BeautyImgActivity.this));
        pictureList = (List<BeautiFilePictureData>) getIntent().getSerializableExtra("picture");
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight=outMetrics.heightPixels;
    }

    @Override
    protected void initData() {
         List<String> imglist_text = new ArrayList<>();
         List<String> titles_text = new ArrayList<>();
        for (int i=0;i< pictureList.size();i++){
            imglist.add(pictureList.get(i).getValue());
            titles.add(pictureList.get(i).getKey());
            imglist_text.add(pictureList.get(i).getKey()+pictureList.get(i).getValue());
            titles_text.add(pictureList.get(i).getKey());
        }
        Collections.sort(imglist_text);
        Collections.sort(titles_text);
        for (int i=0; i<imglist_text.size(); i++){
            //找到http的索引位置
            int http_index=imglist_text.get(i).indexOf("http");
            String img_str=imglist_text.get(i).substring(http_index);
            imglist_text.set(i,img_str);
        }
        titles=titles_text;
        imglist=imglist_text;
       tips = new ImageView[pictureList.size()];

        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(5, 5));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(screenWidth,
//                    screenHeight));
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            group.addView(imageView,layoutParams);
        }
        tv_info.setText(titles.get(0));
        mImageViews = new ImageView[imglist.size()];
        LayoutParams params = new LayoutParams(screenWidth, screenHeight);
        for (int i= 0;i<mImageViews.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            View v= LayoutInflater.from(this).inflate(R.layout.image_item,null);
//            imageView=(ImageView)v.findViewById(R.id.item_image);
//            imageView.setLayoutParams(params);
            mImageViews[i] = imageView;
            imageLoader.displayImage(LoadImgUtil.loadbigImg(imglist.get(i)),imageView);
        }
//        viewPager.setLayoutParams(params);
//        picture.setLayoutParams(params);
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
//        viewPager.setCurrentItem((mImageViews.length) * 100);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            return Integer.MAX_VALUE;
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
//            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);
            ((ViewPager) container).removeView(mImageViews[position ]);
        }

        @Override
        public Object instantiateItem(View container, final int position) {
//            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            ((ViewPager) container).addView(mImageViews[position], 0);
            mImageViews[position].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView=(ImageView)v;
                    Intent intent = new Intent(B1_BeautyImgActivity.this, SpaceImageDetailActivity.class);
                    intent.putExtra("images", (ArrayList<String>) imglist);//非必须
                    intent.putExtra("position", position);
                    int[] location = new int[2];
                    imageView.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);//必须
                    intent.putExtra("locationY", location[1]);//必须

                    intent.putExtra("width", imageView.getWidth());//必须
                    intent.putExtra("height", imageView.getHeight());//必须
                    startActivity(intent);
                    overridePendingTransition(0,0);
//                    Log.v("tag",titles.get(position));
                }
            });
//            return mImageViews[position % mImageViews.length];
            return mImageViews[position];
        }
    }


    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        setImageBackground(position % mImageViews.length);
//        tv_info.setText(titles.get(position % mImageViews.length));
        setImageBackground(position);
        tv_info.setText(titles.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}
