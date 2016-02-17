package com.xem.mzbphoneapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.A0_LoginAty;
import com.xem.mzbphoneapp.atys.B0_OrderAty;
import com.xem.mzbphoneapp.atys.B0_StoreListAty;
import com.xem.mzbphoneapp.atys.B1_MemberInfoAty;
import com.xem.mzbphoneapp.atys.B1_QRcodeAty;
import com.xem.mzbphoneapp.atys.B1_ServeRecordAty;
import com.xem.mzbphoneapp.atys.E1_EmployeeInfoAty;
import com.xem.mzbphoneapp.atys.MzbApplication;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.PrepareDate;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.DecodeUrl;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbListView;
import com.xem.mzbphoneapp.view.NewPopWindow;
import com.xem.zxing.activity.CaptureActivity;

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

/**
 * Created by xuebing on 15/6/3.
 */
public class B0_indexFragment extends MzbFragment {

    @InjectView(R.id.titlebar_iv_right)
    ImageView addPopUp;

    private MzbListView mzbListView;
    private ImageView storeImg;
    private TextView storeName, storeAddress, storeTel, Tel, Address;
    private View viewone, viewtwo, llthree, order, memInfo, memRecord, employee_entry;
    private ViewPager viewPager;
    private BranchData branchData;
    private int currentItem = 0; // 当前图片的索引号
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private List<ImageView> imageViews;
    private ScheduledExecutorService scheduledExecutorService;
    private NewPopWindow addPopWindow;
    private ImageLoader loader;
    private MzbApplication app;
    private String token;
    private String uid;

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        app = (MzbApplication) getActivity().getApplication();
        branchData = app.getBranchData();
        if (branchData != null) {

            if (branchData.getBranid() != null && Config.getCachedUid(getActivity()) != null) {
                loader.displayImage(branchData.getLogo(), storeImg);
                storeName.setText(branchData.getName());
                storeAddress.setText(branchData.getAddress());
                storeTel.setText(branchData.getTel());
                Address.setVisibility(View.VISIBLE);
                storeAddress.setVisibility(View.VISIBLE);
                Tel.setVisibility(View.VISIBLE);
            } else {
                storeImg.setImageDrawable(getResources().getDrawable(R.mipmap.ic_default));
                storeName.setText("亲...");

                storeTel.setText("您尚未绑定任何门店!");
                Tel.setVisibility(View.GONE);
                Address.setVisibility(View.GONE);
                storeAddress.setVisibility(View.GONE);
            }

        } else {
            storeImg.setImageDrawable(getResources().getDrawable(R.mipmap.ic_default));
            storeName.setText("亲...");

            storeTel.setText("您尚未登陆!");
            Tel.setVisibility(View.GONE);
            Address.setVisibility(View.GONE);
            storeAddress.setVisibility(View.GONE);
        }

        if (Config.getCachedIsemploye(getActivity()) && Config.getCachedUid(getActivity()) != null) {
            employee_entry.setVisibility(View.VISIBLE);
        } else {
            employee_entry.setVisibility(View.GONE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View footView = inflater.inflate(R.layout.b0_index_fg, container, false);
        ButterKnife.inject(this, footView);

        new TitleBuilder(footView).setTitleText("美之伴")
                .setRightImage(R.mipmap.top_more)
                .setRightOnClickListener(new setClicklistener());

        token = Config.getCachedToken(getActivity());
        uid = Config.getCachedUid(getActivity());
        loader = ImageLoader.getInstance();

        imageResId = PrepareDate.getImg();
        imageViews = new ArrayList<ImageView>();


        // 初始化图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_cell, null);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }

        viewone = LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner, null);
        viewtwo = LayoutInflater.from(getActivity()).inflate(R.layout.b0_viewtwo, null);


        LinearLayout lltwo = (LinearLayout) viewtwo.findViewById(R.id.lltwo);
        storeImg = (ImageView) viewtwo.findViewById(R.id.store_img);
        storeName = (TextView) viewtwo.findViewById(R.id.store_name);
        storeAddress = (TextView) viewtwo.findViewById(R.id.store_adrress);
        storeTel = (TextView) viewtwo.findViewById(R.id.store_tel);
        Tel = (TextView) viewtwo.findViewById(R.id.tel);
        Address = (TextView) viewtwo.findViewById(R.id.adress);


        lltwo.setLayoutParams(Utility.getInstance().getLayoutThreeParams(lltwo, this));
        lltwo.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(lltwo);


        llthree = viewtwo.findViewById(R.id.llthree);
        llthree.setLayoutParams(Utility.getInstance().getLayoutParams(llthree, this));

        memInfo = viewtwo.findViewById(R.id.mem_info);
        memInfo.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(memInfo);

        memRecord = viewtwo.findViewById(R.id.mem_record);
        memRecord.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(memRecord);


        order = viewtwo.findViewById(R.id.b0_order);
        order.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(order);


        employee_entry = viewtwo.findViewById(R.id.employer_entry);
        employee_entry.setOnClickListener(new setClicklistener());
        Utility.getInstance().clickEffect(employee_entry);

        viewPager = (ViewPager) viewone.findViewById(R.id.vp);
        dots = new ArrayList<View>();
        dots.add(viewone.findViewById(R.id.v_dot0));
        dots.add(viewone.findViewById(R.id.v_dot1));
        dots.add(viewone.findViewById(R.id.v_dot2));

        // 设置填充ViewPager页面的适配器
        viewPager.setAdapter(new MyAdapter());
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        mzbListView = (MzbListView) footView.findViewById(R.id.home_listview);
        mzbListView.addHeaderView(viewone);
        mzbListView.addHeaderView(viewtwo);

        mzbListView.setPullLoadEnable(false);
        mzbListView.setAdapter(null);
        mzbListView.setXListViewListener(this, 0);
        mzbListView.setRefreshTime();
        return footView;
    }


    class setClicklistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.employer_entry:
                    if (!app.getIsLogin()) {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    } else {
                        intent2Activity(E1_EmployeeInfoAty.class);
                        getActivity().finish();
                        break;
                    }

                case R.id.titlebar_iv_right:

                    if (Config.getCachedUid(getActivity()) == null) {
                        Intent login = new Intent(getActivity(), A0_LoginAty.class);
                        startActivity(login);
                        break;
                    } else {

                        addPopWindow = new NewPopWindow(getActivity());
                        addPopWindow.showPopupWindow(addPopUp);

                        addPopWindow.addTaskLayout.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
                                addPopWindow.dismiss();
                            }
                        });

                        addPopWindow.teamMemberLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), B1_QRcodeAty.class));
                                addPopWindow.dismiss();
                            }
                        });
                        break;
                    }

                case R.id.lltwo:

                    if (!app.getIsLogin()) {
                        Intent login = new Intent(getActivity(), A0_LoginAty.class);
                        startActivity(login);
                        break;
                    } else if (app.getBranchData() == null) {
//                        showToast("登陆已失效，请先登陆，谢谢！");
                        intent2Activity(A0_LoginAty.class);
                        break;
                    } else {
                        Intent store = new Intent(getActivity(), B0_StoreListAty.class);
                        startActivityForResult(store, 0);
                        break;
                    }
                case R.id.b0_order:  //预约
                    if (app.getBranchData() == null || !app.getIsLogin()) {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    } else if (app.getBranchData().getBranid() == null) {
                        showToast("请先绑定门店，谢谢！");
                        break;
                    } else {
                        Intent order = new Intent();
                        order.setClass(getActivity(), B0_OrderAty.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("store", branchData);
                        order.putExtras(bundle);
                        startActivity(order);
                        break;
                    }
                case R.id.mem_info: //会员信息
                    if (app.getBranchData() == null || !app.getIsLogin()) {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    } else if (app.getBranchData().getBranid() == null) {
                        showToast("请先绑定门店，谢谢！");
                        break;
                    } else {
                        intent2Activity(B1_MemberInfoAty.class);
                        break;
                    }
                case R.id.mem_record: //服务纪录
                    if (app.getBranchData() == null || !app.getIsLogin()) {
                        intent2Activity(A0_LoginAty.class);
                        break;
                    } else if (app.getBranchData().getBranid() == null) {
                        showToast("请先绑定门店，谢谢！");
                        break;
                    } else {
                        intent2Activity(B1_ServeRecordAty.class);
//                        intent2Activity(DifferentMenuActivity.class);
                        break;
                    }
                default:
                    break;
            }
        }
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
                    if (operationNum.equals("1")) {
                        MemberSign(Config.getCachedUid(getActivity()), obj.getString("PpId"), obj.getString("BranchId"));
                    } else if (operationNum.equals("3")) {
                        app.setCustId(obj.getString("CustId"));
                        MemberBound(Config.getCachedUid(getActivity()), obj.getString("CustId"));
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

            case -100:
                branchData = (BranchData) data.getSerializableExtra("info");
                if (branchData != null) {
                    loader.displayImage(branchData.getLogo(), storeImg);
                    storeName.setText(branchData.getName());
                    storeAddress.setText(branchData.getAddress());
                    storeTel.setText(branchData.getTel());
                }
                break;
        }

    }

    public void MemberBound(String uid, String custid) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("custid", custid);

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_BIND, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        showToast("绑定成功");
                        intent2Activity(B0_StoreListAty.class);
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

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_SIGNIN, params1, new NetCallBack() {
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


    private class MyAdapter extends PagerAdapter {

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
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    /**
     * 换行切换任务
     *
     * @author Administrator
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }

    }
}
