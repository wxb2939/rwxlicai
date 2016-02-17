package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.adapter.ServiceDetailAdapter;
import com.xem.mzbphoneapp.entity.ServiceingDetail;
import com.xem.mzbphoneapp.entity.TbleacustData;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/12.
 */
public class E3_ServicedoneDetailAty extends MzbActivity{

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.e3_name)
    TextView name;
    @InjectView(R.id.e3_phone)
    TextView phone;
    @InjectView(R.id.e3_time)
    TextView time;
    @InjectView(R.id.e3_title_time)
    TextView title_time;
    @InjectView(R.id.e3_photo)
    ImageView photo;
    @InjectView(R.id.list)
    PullToRefreshListView listView;


    private String cus = "";
    private String ttp = "";
    private String ppid = "";
    private List<ServicedoneDetail> list;
    private ServiceDetailAdapter adapter;
    private View footView;
    private int curPage = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_service_detail_aty);
        new TitleBuilder(E3_ServicedoneDetailAty.this).setLeftImage(R.mipmap.top_view_back).setTitleText("服务详情(已离店)");
        cus = getIntent().getStringExtra("custid");
        ttp = getIntent().getStringExtra("branid");
        ppid = getIntent().getStringExtra("ppid");
        ButterKnife.inject(this);
        list = new ArrayList<ServicedoneDetail>();
        GetServiceinfo();

        adapter = new ServiceDetailAdapter(E3_ServicedoneDetailAty.this,list);
        listView.setAdapter(adapter);
        GetService(0);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetService(0);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetService(curPage + 1);
            }
        });

        footView = View.inflate(E3_ServicedoneDetailAty.this, R.layout.footview_loading, null);
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
        params.put("custid", cus);

        RequestUtils.ClientTokenPost(E3_ServicedoneDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBLEACUST, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {

                        TbleacustData tbleacustData = gson.fromJson(obj.getJSONObject("data").toString(),TbleacustData.class);
                        imageLoader.displayImage(tbleacustData.getPic(), photo);
                        name.setText(tbleacustData.getName());
                        phone.setText(tbleacustData.getPhone());
                        title_time.setText("在店时长：");
                        time.setText(tbleacustData.getVisit()+"-"+tbleacustData.getLeave());
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

    public void GetService(final int page) {
        RequestParams params = new RequestParams();
        params.put("ppid",ppid);
        params.put("branid", ttp);
        params.put("custid", cus);
        params.put("begin", page + "");
        params.put("count", 20 + "");
        RequestUtils.ClientTokenPost(E3_ServicedoneDetailAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_TBLEAITEMS, params, new NetCallBack() {
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
                            ServicedoneDetail servicedoneDetail = gson.fromJson(jsonObject.toString(), ServicedoneDetail.class);
                            list.add(servicedoneDetail);
                        }
                        curPage = list.size();
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

    private void add2removeFooterView() {
        ListView lv = listView.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (list.size() % 20 == 0 && list.equals(0)) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }
}
