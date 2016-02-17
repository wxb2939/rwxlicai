package com.xem.mzbcustomerapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.xem.mzbcustomerapp.view.HeadGridView;
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
 * Created by xuebing on 15/11/9.
 */
public class B1_StoreDetailAty extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;

    @InjectView(R.id.headergridview)
    HeadGridView headergridview;

    private View headview;
    private CircleImageView logo;
    private TextView store_name;
    private TextView store_brand;
    private TextView store_time;
    private TextView store_phone;
    private TextView store_address;
    private ImageView call;

    private List<ConditionData> data = new ArrayList<>();
    private ImageAdapter adapter;

    private String branid;
    private int wid;
    private int ppid=-1;
    private String tel_num;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_storedetail_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("门店详情").setLeftImage(R.mipmap.top_view_back);
        branid = getIntent().getStringExtra("branid");
        ppid=getIntent().getIntExtra("ppid", -1);
        storeInfo(branid);

        headview =getLayoutInflater().from(B1_StoreDetailAty.this).inflate(R.layout.b1_storedetail_headview,null);
        logo = (CircleImageView) headview.findViewById(R.id.logo);
        store_name = (TextView) headview.findViewById(R.id.store_name);
        store_brand = (TextView) headview.findViewById(R.id.store_brand);
        store_time = (TextView) headview.findViewById(R.id.store_time);
        store_phone = (TextView) headview.findViewById(R.id.store_phone);
        call = (ImageView) headview.findViewById(R.id.call);
        store_address = (TextView) headview.findViewById(R.id.store_address);
        headergridview.addHeaderView(headview);


    }

    @Override
    protected void initData() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wid = (int) wm.getDefaultDisplay().getWidth();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(B1_StoreDetailAty.this);
                builder.setTitle("拨打电话");
                builder.setMessage("是否呼叫?");
                builder.setPositiveButton("呼叫",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel_num));
                        startActivity(tel);
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });
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
        if (ppid != (-1)){
            params.put("ppid", String.valueOf(ppid));
        }else{
            params.put("ppid", Config.getCachedPpid(B1_StoreDetailAty.this));
        }
//        params.put("ppid", Config.getCachedPpid(B1_StoreDetailAty.this));
        params.put("branid", branid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.branch_info, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                JSONArray jsonArray;
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        StoreInfoData storeInfoData = gson.fromJson(obj.getJSONObject("data").toString(), StoreInfoData.class);

                        imageLoader.displayImage(LoadImgUtil.loadbigImg(storeInfoData.getLogo()), logo);
                        store_name.setText(storeInfoData.getName());
                        store_brand.setText(storeInfoData.getPname());
                        store_time.setText(storeInfoData.getHours());
                        tel_num = storeInfoData.getTel();
                        store_phone.setText(tel_num);
                        store_address.setText(storeInfoData.getAddress());

                        jsonArray = obj.getJSONObject("data").getJSONArray("condition");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ConditionData conditionData = gson.fromJson(object.toString(), ConditionData.class);
                            data.add(conditionData);
                        }


                        adapter = new ImageAdapter(B1_StoreDetailAty.this);

                        headergridview.setAdapter(adapter);
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
        public int getItemViewType(int position) {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;

            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams((int)(wid*0.5), (int)(wid*0.5)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }else{
                imageView = (ImageView) convertView;
            }

            imageLoader.displayImage(LoadImgUtil.loadbigImg(data.get(position).getPic()),imageView);
            return imageView;
        }
    }
}
