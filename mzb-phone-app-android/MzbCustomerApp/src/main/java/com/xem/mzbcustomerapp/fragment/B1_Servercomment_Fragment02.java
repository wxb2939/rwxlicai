package com.xem.mzbcustomerapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.activity.B2_ServerCommentActivity;
import com.xem.mzbcustomerapp.base.BaseFragment;
import com.xem.mzbcustomerapp.entity.ServiceRecordData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/10/29.
 */
public class B1_Servercomment_Fragment02 extends BaseFragment {


    @InjectView(R.id.list)
    PullToRefreshListView listView;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private JSONArray jsonArray;
    private List<ServiceRecordData> data;
    private int curPage;
    private View footView;
    private View view;
    private MyAdapter adapter;


    @Override
    protected View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.b1_server_comment_fragment, null);
        ButterKnife.inject(this, view);
        footView = View.inflate(getActivity(), R.layout.footview_loading, null);
        return view;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        GetServeRecordList(0);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                GetServeRecordList(0);
            }
        });

        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                GetServeRecordList(curPage + 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(getActivity(), B2_ServerCommentActivity.class);
                mIntent.putExtra("svrid",data.get(i-1).getSvrid());
                startActivity(mIntent);
            }
        });

    }

    @Override
    protected void processClick(View v) {

    }

    // ViewHolder静态类
    static class ViewHolder {
        public CircleImageView logo;
        public TextView branch;  //美疗师
        public ImageView cell_img;
        public TextView cell_text;
        public TextView cell_text_two;
        public TextView click;

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
        public ServiceRecordData getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // Get the row id associated with the specified position in the
            // list.
            // 获取在列表中与指定索引对应的行id
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.b1_servercomment_fragment_cell, null);
                holder.logo = (CircleImageView) convertView.findViewById(R.id.logo);
                holder.branch = (TextView) convertView.findViewById(R.id.branch);
                holder.cell_img = (ImageView) convertView.findViewById(R.id.cell_img);
                holder.cell_text = (TextView) convertView.findViewById(R.id.cell_text);
                holder.cell_text_two = (TextView) convertView.findViewById(R.id.cell_text_two);
                holder.click = (TextView) convertView.findViewById(R.id.click);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String uri = LoadImgUtil.loadbigImg(data.get(position).getLogo());
            imageLoader.displayImage(uri,holder.logo);
            holder.branch.setText(data.get(position).getBranch());
            String buri = LoadImgUtil.loadbigImg(data.get(position).getPic());
            imageLoader.displayImage(buri,holder.cell_img);
            holder.cell_text.setText(data.get(position).getName());
            holder.cell_text_two.setText("服务时间："+data.get(position).getTime());
            return convertView;
        }
    }


    public void GetServeRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", Config.getCachedUid(getActivity()));
        params1.put("finished", "true");
        params1.put("begin", page + "");
        params1.put("count", 20 + "");

        final MzbProgressDialog pd = new MzbProgressDialog(getActivity(), "请稍后....");
        pd.show();
//        MzbUrlFactory.service_records
        MzbHttpClient.ClientTokenPost(getActivity(), MzbUrlFactory.BASE_URL + MzbUrlFactory.service_records, params1, new NetCallBack() {

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
                            ServiceRecordData serviceRecordData = gson.fromJson(object.toString(), ServiceRecordData.class);
                            data.add(serviceRecordData);
                        }
//                        adapter = new MyAdapter(getActivity());
//                        listView.setAdapter(adapter);

                        curPage = data.size();
//                        view = Utility.getInstance().getDataLayout(getActivity(), curPage != 0, ground, "暂无数据", R.mipmap.nodata_err, view);
//                        view = Utility.getInstance().hasDataLayout(getActivity(),curPage != 0,ground,"暂无数据", R.mipmap.nodata_err);
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
