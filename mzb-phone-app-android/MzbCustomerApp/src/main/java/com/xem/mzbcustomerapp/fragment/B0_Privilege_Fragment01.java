package com.xem.mzbcustomerapp.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.adapter.CouponAdapter;
import com.xem.mzbcustomerapp.base.BaseFragment;
import com.xem.mzbcustomerapp.entity.CouponData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 15/10/30.
 */
public class B0_Privilege_Fragment01 extends BaseFragment {


    @InjectView(R.id.list)
    PullToRefreshListView listView;
    @InjectView(R.id.ground)
    RelativeLayout ground;


    private View footView;
    private View view;

    private JSONArray jsonArray;
    private List<CouponData> data;
    private int curPage;
    private CouponAdapter adapter;
    private int flag = 0;

    @Override
    protected View initView(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.b0_privilege_fragment, null);
        ButterKnife.inject(this, view);
        footView = View.inflate(getActivity(), R.layout.footview_loading, null);
        return view;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        adapter = new CouponAdapter(getActivity(),data);
        listView.setAdapter(adapter);

        GetServeRecordList(0);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetServeRecordList(0);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
//                GetServeRecordList(curPage + 1);
                GetServeRecordList(curPage + 1);
            }
        });
    }

    @Override
    protected void processClick(View v) {

    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", Config.getCachedUid(getActivity()));
        params1.put("action","1");
        params1.put("begin", page + "");
        params1.put("count", 20 + "");

        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        MzbHttpClient.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.coupons, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        if (page == 0) {
                            data.clear();
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CouponData couponData = gson.fromJson(object.toString(), CouponData.class);
                            data.add(couponData);
                        }

                        curPage = data.size();
                        add2removeFooterView();
                        listView.onRefreshComplete();
                        pd.dismiss();
                    } else {
                        listView.onRefreshComplete();
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
            }
        });
    }


    private void add2removeFooterView() {

        ListView lv = listView.getRefreshableView();
        adapter.notifyDataSetChanged();

        if (data.size() % 20 == 0 && data.size() != 0 && jsonArray.length() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }
}
