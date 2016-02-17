package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.Tbappocust;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/12.
 */
public class E3_ServicetodoDetailAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e3_name)
    TextView name;
    @InjectView(R.id.e3_phone)
    TextView phone;
    @InjectView(R.id.e3_time)
    TextView time;
    @InjectView(R.id.e3_photo)
    ImageView photo;
    @InjectView(R.id.yy_mls)
    TextView mls;
    @InjectView(R.id.yy_rs)
    TextView rs;
    @InjectView(R.id.ll_new)
    LinearLayout ll_new;
    private String appoid = "";
    private String ttp = "";
    private String ppid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_service_todo_aty);
        ButterKnife.inject(E3_ServicetodoDetailAty.this);
        new TitleBuilder(E3_ServicetodoDetailAty.this).setLeftImage(R.mipmap.top_view_back).setTitleText("服务详情(预约中)");
        appoid = getIntent().getStringExtra("appoid");
        ttp = getIntent().getStringExtra("branid");
        ppid = getIntent().getStringExtra("ppid");
        GetServiceinfo();

    }

    @OnClick({R.id.titlebar_iv_left})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
        }
    }

    public void GetServiceinfo() {
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("branid",ttp);
        params.put("appoid", appoid);
        RequestUtils.ClientTokenPost(E3_ServicetodoDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBAPPOCUST, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        Tbappocust tbappocust = gson.fromJson(obj.getJSONObject("data").toString(),Tbappocust.class);
                        imageLoader.displayImage(tbappocust.getPic(), photo);
                        name.setText(tbappocust.getName());
                        phone.setText(tbappocust.getPhone());
                        time.setText(tbappocust.getTime());
                        mls.setText(tbappocust.getWaiter());
//                        xm.setText(tbappocust.getProject());
                        for (int i=0;i<tbappocust.getProject().length;i++){
                            View view = LayoutInflater.from(E3_ServicetodoDetailAty.this).inflate(R.layout.new_view_project,null);
                            TextView textView = (TextView) view.findViewById(R.id.view_xm);
                            textView.setText(tbappocust.getProject()[i]);
                            ll_new.addView(view,i);
                        }


                        Integer person = tbappocust.getPerson();

                        if (person != null){
                            rs.setText(person.toString());
                        }else {
                            rs.setText(0);
                        }
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
}
