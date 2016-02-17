package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.ShowShare;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.swipemenulistview.SwipeMenu;
import com.xem.swipemenulistview.SwipeMenuCreator;
import com.xem.swipemenulistview.SwipeMenuItem;
import com.xem.swipemenulistview.SwipeMenuListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_nonet_back;

/**
 * Created by xuebing on 15/6/30.
 */
public class B0_StoreListAty extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.b0_storelist)
    SwipeMenuListView storelist;
    @InjectView(R.id.b0_ground)
    RelativeLayout b0Ground;

    private List<BranchData> data;
    private BranchData branchData;
    private int resultCode = -100;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_storelist_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("门店列表").setLeftImage(R.mipmap.top_view_back);
        data = new ArrayList<BranchData>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkNetAddToast()){
            if (data.size() != 0){
                data.clear();
            }
            GetStoreList(Config.getCachedUid(B0_StoreListAty.this));
        }else {
            View grounds = View.inflate(this, ground_nonet_back,null);
            b0Ground.addView(grounds);
        }
        application = (MzbApplication) getApplication();
    }


    private class OnItemClickListenerImpl implements OnItemClickListener {
        public OnItemClickListenerImpl() {
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            branchData = data.get(position);

            SetCurStore(Config.getCachedUid(B0_StoreListAty.this), branchData.getBranid() + "");
            Intent intent = new Intent();
            intent.putExtra("info", branchData);
            showToast("已成功设置当前门店");
            setResult(resultCode, intent);
            application.setBranchData(branchData);
            finish();
        }
    }


    public void SetCurStore(String uid, String branid) {

        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("branid", branid);
        RequestUtils.ClientTokenPost(B0_StoreListAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_CURBRANCH, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
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


    public void GetSharebrandata(String type, String branid) {

        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("branid", branid);
        RequestUtils.ClientTokenPost(B0_StoreListAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_BRANDATA, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("data");
                        new ShowShare(B0_StoreListAty.this).StoreShowshare(data.getString("title"), data.getString("content"), data.getString("logo"), data.getString("url"));
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(B0_StoreListAty.this, "请求失败,请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void GetStoreList(String uid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);

        RequestUtils.ClientTokenPost(B0_StoreListAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_BRANCHES, params1, new NetCallBack(this) {

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
                            BranchData branchData = gson.fromJson(object.toString(), BranchData.class);
                            data.add(branchData);
                        }

                        Utility.getInstance().hasDataLayout(B0_StoreListAty.this, data.size() != 0, b0Ground, "当前尚未绑定门店", R.mipmap.store_err);

//                        SwipeMenuListView storelist = (SwipeMenuListView) findViewById(R.id.b0_storelist);
                        adapter = new MyAdapter(B0_StoreListAty.this);
                        storelist.setAdapter(adapter);
                        SwipeMenuCreator creator = new SwipeMenuCreator() {
                            @Override
                            public void create(SwipeMenu menu) {
                                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xbc, 0x31, 0x72)));
                                deleteItem.setWidth(dp2px(80));
//                                deleteItem.setIcon(R.mipmap.ic_action_share);
                                deleteItem.setTitle("分享");
                                deleteItem.setTitleSize(15);
                                deleteItem.setTitleColor(Color.WHITE);
                                menu.addMenuItem(deleteItem);
                            }
                        };

                        // set creator
                        storelist.setMenuCreator(creator);

                        storelist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                                GetSharebrandata(1 + "", data.get(position).getBranid() + "");
                            }
                        });
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


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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


            if (data.get(position).getBranid() != application.getBranchData().getBranid()) {
                holder.btnStore.setText("设为当前门店");
                holder.btnStore.setTextColor(getResources().getColor(R.color.recordone));
            } else {
                holder.btnStore.setText("当前门店");
                holder.btnStore.setTextColor(getResources().getColor(R.color.recordtwo));
            }
            return convertView;
        }
    }
}
