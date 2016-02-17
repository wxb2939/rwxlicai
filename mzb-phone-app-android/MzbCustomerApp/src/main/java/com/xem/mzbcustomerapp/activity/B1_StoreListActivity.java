package com.xem.mzbcustomerapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BranchData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.other.zxing.activity.CaptureActivity;
import com.xem.mzbcustomerapp.utils.CommLogin;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.DecodeUrl;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/23.
 */
public class B1_StoreListActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.list)
    PullToRefreshListView storelist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    public  List<BranchData> data;
    private MyAdapter adapter;
    private BranchData branchData;
    private int resultCode = -100;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_storelist_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("门店列表").setLeftImage(R.mipmap.top_view_back).setRightText("添加");
        data = new ArrayList<>();
    }

    @Override
    protected void initData() {
        GetStoreList(Config.getCachedUid(B1_StoreListActivity.this));
    }

    @OnClick({R.id.titlebar_iv_left,R.id.titlebar_tv_right})
    public void clickActon(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.titlebar_tv_right:{
                startActivityForResult(new Intent(this, CaptureActivity.class), 0);
                break;
            }
            default:
                break;
        }
    }


    public void GetStoreList(String uid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);

        MzbHttpClient.ClientTokenPost(B1_StoreListActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.user_branches, params1, new NetCallBack() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            BranchData mbranchData = gson.fromJson(object.toString(), BranchData.class);
                            data.add(mbranchData);
                        }
                        adapter = new MyAdapter(B1_StoreListActivity.this);
                        storelist.setAdapter(adapter);
                        storelist.setOnItemClickListener(new OnItemClickListenerImpl());
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
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


    public void SetCurStore(String uid, String branid) {

        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("branid", branid);
        MzbHttpClient.ClientTokenPost(B1_StoreListActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.curbranch, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("info", branchData);
                        showToast("已成功切换门店");
                        setResult(resultCode, intent);
                        Config.cachedBrandid(B1_StoreListActivity.this,branchData.getBranid());
                        finish();

                    } else {
                        showToast("设置失败");
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



    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        public OnItemClickListenerImpl() {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            branchData = data.get(position-1);

//            SetCurStore(Config.getCachedUid(B1_StoreListActivity.this), branchData.getBranid());
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("branchData", branchData);
            intent.putExtras(bundle);
            setResult(resultCode, intent);
            finish();
        }
    }


    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView address;
        public TextView phone;
        public TextView btnStore;

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
        public BranchData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.b0_storelist_aty_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.b0_storelist_img);
                holder.name = (TextView) convertView.findViewById(R.id.b0_storelist_name);
                holder.address = (TextView) convertView.findViewById(R.id.b0_storelist_adrress);
                holder.phone = (TextView) convertView.findViewById(R.id.b0_storelist_phone);
                holder.btnStore = (TextView) convertView.findViewById(R.id.btn_curstore);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getLogo(), holder.img);
            holder.name.setText(data.get(position).getName());
            holder.address.setText(data.get(position).getAddress());
            holder.phone.setText(data.get(position).getTel());


//            if (data.get(position).getBranid() == Config.getCachedBrandid(B1_StoreListActivity.this)) {
//                holder.btnStore.setText("当前门店");
//                holder.btnStore.setTextColor(getResources().getColor(R.color.recordtwo));
//            } else {
//                holder.btnStore.setText("设为当前门店");
//                holder.btnStore.setTextColor(getResources().getColor(R.color.recordone));
//            }
            return convertView;
        }
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
                    if (operationNum.equals("1")) {//签到
                        MemberSign(Config.getCachedUid(this), obj.getString("PpId"), obj.getString("BranchId"));
                    } else if (operationNum.equals("3")) { //绑定
                        Config.cachedCustid(this,obj.getString("CustId"));
                        MemberBound(Config.getCachedUid(this), obj.getString("CustId"));
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

            default:
                break;
        }

    }

    public void MemberBound(String uid, String custid) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("custid", custid);

        MzbHttpClient.ClientTokenPost(this, MzbUrlFactory.BASE_URL + MzbUrlFactory.bind, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        showToast("绑定成功");
//                        startActivity(new Intent(MainActivity.this, SplashAty.class));
                        new CommLogin(B1_StoreListActivity.this).Login(Config.getCachedPhone(B1_StoreListActivity.this),
                                Config.getCachedPassword(B1_StoreListActivity.this), application);
//                        intent2Aty(MainActivity.class);
                        initView();
                        initData();
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

        MzbHttpClient.ClientTokenPost(B1_StoreListActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.signin, params1, new NetCallBack() {
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

}
