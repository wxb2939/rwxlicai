package com.rwxlicai.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rwxlicai.R;
import com.rwxlicai.entity.IndexResult;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.view.RoundProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 16/1/14.
 */
public class Invest_List_Fragment extends Fragment {

    @InjectView(R.id.list)
    PullToRefreshListView listView;
    private List<IndexResult> data;
    private MyAdapter adapter;
    private JSONArray jsonArray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        data = new ArrayList<>();
        index();
        adapter = new MyAdapter(getActivity());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void index() {
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.getBorrow, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
//                        Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(obj.getString("result"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            IndexResult indexResult = gson.fromJson(object.toString(), IndexResult.class);
                            data.add(indexResult);
                        }
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), obj.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    // ViewHolder静态类
    static class ViewHolder {
        public RoundProgressBar mRoundProgressBar;
        public TextView title;
        public TextView interestRate;
        public TextView type;
        public TextView borrowSum;
        public TextView tenderSum;
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
        public IndexResult getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.employ_myd_item, null);
                holder.mRoundProgressBar = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.interestRate = (TextView) convertView.findViewById(R.id.interestRate);
                holder.type = (TextView) convertView.findViewById(R.id.type);
                holder.borrowSum = (TextView) convertView.findViewById(R.id.borrowSum);
                holder.tenderSum = (TextView) convertView.findViewById(R.id.tenderSum);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            convertView.setEnabled(false);
            convertView.setClickable(false);
            double mfloat = Float.parseFloat(data.get(position).getProgressRate());
            holder.mRoundProgressBar.setProgress((int) mfloat);
//            holder.mRoundProgressBar.setProgress(50);
            String flag = data.get(position).getType();
            String dFlag = data.get(position).getIsDay();
            String nFlag = data.get(position).getBorrowTimeLimit();
            if (dFlag.equals("1")) {
                holder.type.setText(nFlag + "天");
            } else {
                holder.type.setText(nFlag + "月");
            }
            if (flag == null) {
                holder.title.setText("月标" + "-" + data.get(position).getBorrowTitle());
            } else {
                if (flag.equals("year")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                } else if (flag.equals("quarter")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                } else if (flag.equals("day")) {
                    holder.title.setText(data.get(position).getTitle() + "-" + data.get(position).getBorrowTitle());
                }
            }
            holder.interestRate.setText(data.get(position).getInterestRate() + "％");
            holder.borrowSum.setText(data.get(position).getBorrowSum());
            holder.tenderSum.setText(data.get(position).getTenderSum());
            return convertView;
        }
    }
}
