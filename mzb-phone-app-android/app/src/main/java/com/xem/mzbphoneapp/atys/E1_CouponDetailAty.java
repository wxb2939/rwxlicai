package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.Coupdata;
import com.xem.mzbphoneapp.entity.Coupon;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.ShowShare;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/5.
 */
public class E1_CouponDetailAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.coupon_logo)
    ImageView logo;
    @InjectView(R.id.coupon_store)
    TextView store;
    @InjectView(R.id.coupon_name)
    TextView name;
    @InjectView(R.id.coupon_detail)
    TextView detail;
    @InjectView(R.id.coupon_time)
    TextView time;
    @InjectView(R.id.e1_share)
    Button share;

    private Coupon coupon;
    private boolean date_compare;

    //1表示免费优惠券，2表示抵扣优惠券，3表示特价优惠券

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_coupondetail_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("优惠券详情").setLeftImage(R.mipmap.top_view_back);

        coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        date_compare = getIntent().getBooleanExtra("time",false);

        ImageLoader.getInstance().displayImage(coupon.getPic(), logo);
        store.setText(coupon.getPname());
        name.setText(coupon.getName());
        time.setText(coupon.getSdate() + "至" + coupon.getEdate());
        detail.setText(coupon.getDesc());

        if (date_compare){
            share.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick({R.id.titlebar_iv_left, R.id.e1_share})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.e1_share:
                if (Utility.getInstance().isFastClick()){
                    return;
                }
                GetSharecoupdata("1", coupon.getCoupid() + "");
                break;
            default:
                break;
        }
    }


    public void GetSharecoupdata(String type, String coupid) {

        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("coupid", coupid);
        RequestUtils.ClientTokenPost(E1_CouponDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_COUPDATA, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Coupdata coupdata = gson.fromJson(obj.getJSONObject("data").toString(),Coupdata.class);
                        new ShowShare(E1_CouponDetailAty.this).ConponShowshare(coupdata.getTitle(), coupdata.getContent(), coupdata.getLogo(), coupdata.getUrl());
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(E1_CouponDetailAty.this, "请求失败,请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
