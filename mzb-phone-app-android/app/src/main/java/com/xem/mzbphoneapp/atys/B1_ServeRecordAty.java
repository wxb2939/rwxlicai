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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.ServiceRecord;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

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
 * Created by xuebing on 15/7/11.
 */
public class B1_ServeRecordAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.serve_list)
    PullToRefreshListView serveList;
    @InjectView(R.id.serve_ground)
    RelativeLayout ground;


    private List<ServiceRecord> data;
    private MyAdapter adapter;
    private String svrid;
    private int curPage = 0;
    private View footView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serve_record_aty);
        new TitleBuilder(this).setTitleText("服务记录").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        data = new ArrayList<ServiceRecord>();
        serveList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetServeRecordList(0);
            }
        });

        serveList.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetServeRecordList(curPage + 1);
            }
        });
        serveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(B1_ServeRecordAty.this, B1_Publish_Evaluate_Aty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("serviceRecord", data.get(position-1));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
        footView = View.inflate(B1_ServeRecordAty.this, R.layout.footview_loading, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serveList.setAdapter(adapter);
        if (checkNetAddToast()){
            GetServeRecordList(0);
            adapter = new MyAdapter(B1_ServeRecordAty.this);
            serveList.setAdapter(adapter);
        }else {
            View grounds = View.inflate(this, ground_nonet_back,null);
            ground.addView(grounds);
        }
    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", application.getBranchData().getBranid() + "");
        params1.put("custid", application.getBranchData().getCustid() + "");
        params1.put("begin", page + "");
        params1.put("count", "20");
        RequestUtils.ClientTokenPost(B1_ServeRecordAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_SER_RECORDS, params1, new NetCallBack(this) {
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
                            ServiceRecord serviceRecord = gson.fromJson(object.toString(), ServiceRecord.class);
                            data.add(serviceRecord);
                        }
                        Utility.getInstance().hasDataLayout(B1_ServeRecordAty.this,data.size() != 0, ground, "服务纪录为空", R.mipmap.server_err);

                        curPage = data.size();
                        add2removeFooterView();
                        serveList.onRefreshComplete();
                    } else {
                        serveList.onRefreshComplete();
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

    private void add2removeFooterView() {
        ListView lv = serveList.getRefreshableView();
        adapter.notifyDataSetChanged();
        if (data.size() % 20 == 0 && data.size() != 0) {
            lv.addFooterView(footView);
        } else {
            lv.removeFooterView(footView);
        }
    }

    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView serverItem;
        public TextView servergu;  //顾问
        public TextView serverml;  //美疗师
        public TextView serverTime;
        public TextView btn_comment;
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
        public ServiceRecord getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.b1_serverecord_aty_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.b1_record_img);
                holder.serverItem = (TextView) convertView.findViewById(R.id.b1_record_itm);
                holder.servergu = (TextView) convertView.findViewById(R.id.b1_record_gw);
                holder.serverml = (TextView) convertView.findViewById(R.id.b1_record_ml);
                holder.serverTime = (TextView) convertView.findViewById(R.id.b1_record_time);
                holder.btn_comment = (TextView) convertView.findViewById(R.id.btn_comment);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPic(), holder.img);
            holder.serverItem.setText(data.get(position).getName());
            holder.servergu.setText(data.get(position).getConsultant());
            holder.serverml.setText(data.get(position).getBeautician());
            holder.serverTime.setText(data.get(position).getTime());
            if (data.get(position).getFinished().equals(false)) {
                holder.btn_comment.setText("去评价");
                holder.btn_comment.setTextColor(getResources().getColor(R.color.recordone));
            } else {
                holder.btn_comment.setText("已评价");
                holder.btn_comment.setTextColor(getResources().getColor(R.color.recordtwo));
            }

            return convertView;
        }
    }

    @OnClick({R.id.titlebar_iv_left, R.id.serve_ground})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.serve_ground:
//                intent2Aty(B1_Publish_Evaluate_Aty.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                Bundle b = data.getExtras();
                svrid = b.getString("svrid");
                intent2Aty(DialogCommentActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
