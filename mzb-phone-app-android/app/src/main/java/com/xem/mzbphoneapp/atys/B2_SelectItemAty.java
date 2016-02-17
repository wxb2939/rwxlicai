package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.ItemqueryData;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/6.
 */
public class B2_SelectItemAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e2_storelist)
    PullToRefreshListView orderlist;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private List<ItemqueryData> data = new ArrayList<ItemqueryData>();
    private List<Integer> num ;
    private int resultCode = -101;
    private myAdapter adapter;
    private List<ItemqueryData> selectedPrj = new ArrayList<ItemqueryData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_storefatisfy_aty);
        new TitleBuilder(this).setTitleText("选择项目").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        num = new ArrayList<>();
        getEmployeeList(0);
        adapter = new myAdapter(B2_SelectItemAty.this);
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int value = position - 1;
                ItemqueryData prj = data.get(value);

                prj.setIsChecked(!prj.getIsChecked());
                if (prj.getIsChecked()) {
                    if (num.size() > 4) {
                        showToast("最多能选择5个项目");
                    } else {
                        num.add(prj.getProjectid());
                        selectedPrj.add(data.get(value));
                    }
                } else {
                    num.remove(prj.getProjectid());
                    selectedPrj.remove(prj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                Intent intent = new Intent();
                intent.putExtra("info",(Serializable)selectedPrj);
                setResult(resultCode, intent);
                finish();
                break;
            default:
                break;
        }
    }



    public void getEmployeeList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("ppid", application.getBranchData().getPpid().toString());
        params1.put("custid", application.getBranchData().getCustid().toString());
        RequestUtils.ClientTokenPost(B2_SelectItemAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_PRJACC, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        if (page == 0) {
                            data.clear();
                        }
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ItemqueryData itemqueryData = gson.fromJson(object.toString(), ItemqueryData.class);
                            itemqueryData.setIsChecked(false);
                            data.add(itemqueryData);
                        }
                        orderlist.setAdapter(adapter);
                        Utility.getInstance().hasDataLayout(B2_SelectItemAty.this, data.size() != 0, ground, "暂无项目", R.mipmap.nodata_err);

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




    static class ViewHolder {
        public ImageView mlspic;
        public TextView mlsname;
        public ImageView mslgood;
    }

    public class myAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private myAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public ItemqueryData getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.b2_selectmls_aty_cell, null);
                holder.mlspic = (ImageView) convertView.findViewById(R.id.mls_pic);
                holder.mlsname = (TextView) convertView.findViewById(R.id.mls_name);
                holder.mslgood = (ImageView) convertView.findViewById(R.id.mls_good);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.mlspic);
            holder.mlsname.setText(data.get(position).getName());

            if (num.contains(data.get(position).getProjectid())){
                holder.mslgood.setVisibility(View.VISIBLE);
            }else {
                holder.mslgood.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }
}
