package com.xem.mzbcustomerapp.activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.ConditionData;
import com.xem.mzbcustomerapp.entity.StoreInfoData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/11/4.
 */
public class B1_StoreDetailActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.logo)
    CircleImageView logo;
    @InjectView(R.id.store_name)
    TextView store_name;
    @InjectView(R.id.store_brand)
    TextView store_brand;
    @InjectView(R.id.store_time)
    TextView store_time;
    @InjectView(R.id.store_phone)
    TextView store_phone;
    @InjectView(R.id.store_address)
    TextView store_address;
    @InjectView(R.id.gridview)
    GridView gridview;

    private String branid;
//    private StoreInfoData storeInfoData;
    private List<ConditionData> data = new ArrayList<>();
    private ImageAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_storedetail_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("门店详情").setLeftImage(R.mipmap.top_view_back);
        branid = getIntent().getStringExtra("branid");
    }

    @Override
    protected void initData() {

        storeInfo(branid);
    }

    @OnClick({R.id.titlebar_iv_left})
    public void clickActon(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }


    private void storeInfo(String branid){
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B1_StoreDetailActivity.this));
        params.put("branid", branid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.branch_info, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                JSONArray jsonArray;
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        StoreInfoData storeInfoData = gson.fromJson(obj.getJSONObject("data").toString(), StoreInfoData.class);

                        imageLoader.displayImage(LoadImgUtil.loadsmallImg(storeInfoData.getLogo()), logo);
                        store_name.setText(storeInfoData.getName());
                        store_brand.setText(storeInfoData.getPname());
                        store_time.setText(storeInfoData.getHours());
                        store_phone.setText(storeInfoData.getTel());
                        store_address.setText(storeInfoData.getAddress());

                        jsonArray = obj.getJSONObject("data").getJSONArray("condition");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ConditionData conditionData = gson.fromJson(object.toString(), ConditionData.class);
                            data.add(conditionData);
                        }
                        adapter = new ImageAdapter(B1_StoreDetailActivity.this);
                        gridview.setAdapter(adapter);
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
    }



    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context context) {
            this.mContext=context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }else{
                imageView = (ImageView) convertView;
            }
            imageLoader.displayImage(data.get(position).getPic(),imageView);
            return imageView;
        }



    }

}
