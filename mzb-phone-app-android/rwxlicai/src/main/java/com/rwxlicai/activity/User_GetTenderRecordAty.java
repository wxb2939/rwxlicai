package com.rwxlicai.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.entity.TenderRecord;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/26.
 */
public class User_GetTenderRecordAty extends RwxActivity {

    @InjectView(R.id.list)
    PullToRefreshListView listView;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;


    private List<TenderRecord> data;
    private MyAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.user_gettenderrecord_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("投资记录").setLeftImage(R.mipmap.top_view_back);
        data = new ArrayList<>();
        getTenderRecord();
        adapter = new MyAdapter(User_GetTenderRecordAty.this);
    }

    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

    }


    private void getTenderRecord() {
        RequestParams params = new RequestParams();
        params.put("pageNum", "1");
        RwxHttpClient.ClientTokenPost(User_GetTenderRecordAty.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.getTenderRecord, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONObject data1 = obj.getJSONObject("result");
                        JSONArray array = data1.getJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            TenderRecord tenderRecord = gson.fromJson(object.toString(), TenderRecord.class);
                            data.add(tenderRecord);
                            listView.setAdapter(adapter);
                        }
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }

    // ViewHolder静态类
    static class ViewHolder {
        public TextView title;
        public TextView tenderStatus;
        public TextView rate;
        public TextView interest;
        public TextView amount;
        public TextView startDate;
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
        public TenderRecord getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.tender_record_cell, null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.tenderStatus = (TextView) convertView.findViewById(R.id.tenderStatus);
                holder.rate = (TextView) convertView.findViewById(R.id.rate);
                holder.interest = (TextView) convertView.findViewById(R.id.interest);
                holder.amount = (TextView) convertView.findViewById(R.id.amount);
                holder.startDate = (TextView) convertView.findViewById(R.id.startDate);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            convertView.setEnabled(false);
            convertView.setClickable(false);
            holder.title.setText(data.get(position).getTitle());
            holder.tenderStatus.setText(data.get(position).getTenderStatus());
            holder.rate.setText(data.get(position).getRate());
            holder.interest.setText(data.get(position).getInterest());
            holder.amount.setText(data.get(position).getAmount());
            holder.startDate.setText(data.get(position).getStartDate());
            return convertView;
        }
    }
}
