package com.xem.mzbphoneapp.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.PerfEmpData;
import com.xem.mzbphoneapp.entity.PerfPpData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/22.
 */
public class E3_PerfEmpofWeekFg extends Fragment {

    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_right)
    ImageView selectRight;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    @InjectView(R.id.pe_crash)
    TextView pe_crash;
    @InjectView(R.id.pe_work)
    TextView pe_work;
    @InjectView(R.id.pe_product)
    TextView pe_product;
    @InjectView(R.id.pe_project)
    TextView pe_project;
    @InjectView(R.id.pe_customer)
    TextView pe_customer;


    private String dayStr;
    protected ImageLoader imageLoader;
    private Date date;
    private Calendar c;
    private String empid = "";
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.e3_perf_emp_fg, null);
        ButterKnife.inject(this, view);
        imageLoader = ImageLoader.getInstance();
        c = Calendar.getInstance();
        if (getArguments() != null) {
            empid = getArguments().getString("empid");
        }
        date = new Date();
        refreshDate(0);

        return view;
    }


    @OnClick({R.id.select_right, R.id.select_left})
    public void click(View v) {


        c.setTime(date);
        switch (v.getId()) {
            case R.id.select_left:
                c.add(c.WEEK_OF_MONTH, -1);
                date = c.getTime();
                refreshDate(0);
                break;
            case R.id.select_right:
                c.add(c.WEEK_OF_MONTH, 1);
                date = c.getTime();
                refreshDate(0);
                break;
            default:
                break;
        }
    }

    private void refreshDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dayStr = sdf.format(date);
        int week = c.get(Calendar.WEEK_OF_MONTH);//获取是本月的第几周
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月");
        selectTitle.setText(sdf1.format(date) + "第" + week + "周");
        getPerfPeofDayData(num);
    }

    public void getPerfPeofDayData(final Integer page) {
        RequestParams params1 = new RequestParams();
        params1.put("cycle", "2");
        params1.put("empid", empid);
        params1.put("date", dayStr.toString());
        params1.put("begin", page.toString());
        params1.put("count", 20 + "");


        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_PERFEMPLOYEE, params1, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        pd.dismiss();
                        PerfEmpData perfEmpData = gson.fromJson(obj.getJSONObject("data").toString(),PerfEmpData.class);
                        pe_crash.setText(perfEmpData.getCash().toString());
                        pe_work.setText(perfEmpData.getWork().toString());
                        pe_product.setText(perfEmpData.getProduct().toString());
                        if (perfEmpData.getProduct() == null){
                            pe_product.setText("0");
                        }else {
                            pe_product.setText(perfEmpData.getProduct().toString());
                        }
                        if (perfEmpData.getCustomer() == null){
                            pe_customer.setText("0");
                        }else {
                            pe_customer.setText(perfEmpData.getCustomer().toString());
                        }
                        view = Utility.getInstance().getDataLayout(getActivity(), !perfEmpData.equals(null), ground, "暂无数据", R.mipmap.nodata_err, view);
                    } else {
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
}