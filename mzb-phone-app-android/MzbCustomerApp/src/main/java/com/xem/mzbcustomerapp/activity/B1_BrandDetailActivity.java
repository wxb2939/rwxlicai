package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BranchStore;
import com.xem.mzbcustomerapp.entity.BrandDetailData;
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
 * Created by xuebing on 15/10/31.
 */
public class B1_BrandDetailActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.list)
    ListView list;

    private View headview;
    private CircleImageView logo;
    private TextView branch;
    private TextView branch_info;
    private TextView branch_range;
    private List<BranchStore> data;
    private MyAdapter adapter;
    int ppid=-1;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_branddetail_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("品牌详情").setLeftImage(R.mipmap.top_view_back).setRightImage(R.mipmap.m_more);
        data = new ArrayList<>();
        headview =getLayoutInflater().from(B1_BrandDetailActivity.this).inflate(R.layout.b1_branddetail_call_aty,null);

        logo = (CircleImageView) headview.findViewById(R.id.logo);
        branch = (TextView) headview.findViewById(R.id.branch);
        branch_info = (TextView) headview.findViewById(R.id.brand_info);
        branch_range = (TextView) headview.findViewById(R.id.brand_range);
        list.addHeaderView(headview,null,false);
        Intent intent=getIntent();
        if (intent != null ){
            ppid=intent.getIntExtra("ppid",-1);
            if (ppid != (-1)){
                brandInfo(String.valueOf(ppid));
            }else{
                brandInfo(Config.getCachedPpid(B1_BrandDetailActivity.this));
            }
        }else{
            brandInfo(Config.getCachedPpid(B1_BrandDetailActivity.this));
        }
//        brandInfo(Config.getCachedPpid(B1_BrandDetailActivity.this));
    }

    @Override
    protected void initData() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(B1_BrandDetailActivity.this,B1_StoreDetailAty.class);
                mIntent.putExtra("branid", data.get(i - 1).getBranid());
                if (ppid != (-1)){
                    mIntent.putExtra("ppid",ppid);
                }
                startActivity(mIntent);
//                intent2Aty(B1_StoreDetailActivity.class);
            }
        });
    }

    @OnClick({R.id.titlebar_iv_left,R.id.titlebar_iv_right})
    public void clickActon(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            //点击更多时
            case R.id.titlebar_iv_right:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            default:
                break;
        }
    }

    private void brandInfo(String ppid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pp_info, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                JSONArray jsonArray;
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        BrandDetailData brandDetailData = gson.fromJson(obj.getJSONObject("data").toString(), BrandDetailData.class);
                        imageLoader.displayImage(LoadImgUtil.loadbigImg(brandDetailData.getLogo()), logo);
                        branch.setText(brandDetailData.getName());
                        if (brandDetailData.getIntro() == null){
                            branch_info.setText("");
                        }else {
                            branch_info.setText("      " + brandDetailData.getIntro());
                        }
                        if(brandDetailData.getScope()== null){
                            branch_range.setText("");
                        }else {
                            branch_range.setText("      " + brandDetailData.getScope());
                        }
                        jsonArray = obj.getJSONObject("data").getJSONArray("branch");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            BranchStore branchStore = gson.fromJson(object.toString(), BranchStore.class);
                            data.add(branchStore);
                        }
                        adapter = new MyAdapter(B1_BrandDetailActivity.this);
                        list.setAdapter(adapter);
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



    // ViewHolder静态类
    static class ViewHolder {
        public TextView name;

    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public BranchStore getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.b1_branddetail_activity_cell, null);
                holder.name = (TextView) convertView.findViewById(R.id.store_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(data.get(position).getName());
            return convertView;
        }
    }

}
