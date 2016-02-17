package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.adapter.ConponItemAdapter;
import com.xem.mzbphoneapp.entity.Coupon;
import com.xem.mzbphoneapp.utils.CompareTime;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_coupon_back;
import static com.xem.mzbphoneapp.R.layout.ground_nonet_back;
import static com.xem.mzbphoneapp.R.layout.ground_store_back;

/**
 * Created by xuebing on 15/7/25.
 */
public class E0_CouponAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.list1)
    PullToRefreshListView listView;
    @InjectView(R.id.e0_coupon_ground)
    RelativeLayout ground;


    private ConponItemAdapter couponAdapter;
    private int curPage = 0;
    private View footView;
    ArrayList<Coupon> list = new ArrayList<Coupon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e0_coupon_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("优惠券").setLeftImage(R.mipmap.top_view_back);

        couponAdapter = new ConponItemAdapter(E0_CouponAty.this, list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(E0_CouponAty.this, E1_CouponDetailAty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("coupon", list.get(position-1));
                if (CompareTime.compare(list.get(position-1).getEdate())){
                    intent.putExtra("time",true);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetCoupon(0);
            }
        });


        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetCoupon(curPage + 1);
            }
        });
        footView = View.inflate(E0_CouponAty.this,R.layout.footview_loading,null);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkNetAddToast()){
            GetCoupon(0);
        }else {
            View grounds = View.inflate(this, ground_nonet_back,null);
            ground.addView(grounds);
        }



    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }


    public void GetCoupon(final int page) {
        RequestParams params = new RequestParams();
        params.put("uid", Config.getCachedUid(E0_CouponAty.this));
        params.put("begin", page + "");
        params.put("count", 10 + "");

        RequestUtils.ClientTokenPost(E0_CouponAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_COUPONS, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        if (page == 0) {
                            list.clear();
                        }
                        JSONArray data = obj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            Coupon coupon = gson.fromJson(jsonObject.toString(), Coupon.class);
                            list.add(coupon);
                        }

                        curPage = list.size();
                        listView.setAdapter(couponAdapter);
                        Utility.getInstance().hasDataLayout(E0_CouponAty.this, list.size() != 0, ground, "暂无优惠券", R.mipmap.conpon_err);
                        add2removeFooterView();
                        listView.onRefreshComplete();
                    } else {
                        listView.onRefreshComplete();
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                listView.onRefreshComplete();
                showToast("请求失败");
            }
        });
    }

    private void add2removeFooterView(){
        ListView lv = listView.getRefreshableView();
        couponAdapter.notifyDataSetChanged();
        if (list.size()%10 == 0 && list.equals(0)){
            lv.addFooterView(footView);
        }else {
            lv.removeFooterView(footView);
        }
    }
}
