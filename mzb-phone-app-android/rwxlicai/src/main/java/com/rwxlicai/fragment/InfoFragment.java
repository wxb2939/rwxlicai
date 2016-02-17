package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.entity.ContentMessage;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 16/1/26.
 */
public class InfoFragment extends Fragment {

    @InjectView(R.id.list)
    PullToRefreshListView listView;

    private MyAdapter adapter;
    private JSONArray jsonArray = new JSONArray();

    private List<ContentMessage> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        getContentMessage();
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
        return view;
    }

    public void getContentMessage() {
        RequestParams params = new RequestParams();
        params.put("pageNum", "1");
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.getContentMessage, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONObject obj1 = obj.getJSONObject("result");
                        jsonArray = obj1.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ContentMessage contentMessage = gson.fromJson(object.toString(), ContentMessage.class);
                            data.add(contentMessage);
                        }
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
        public TextView title;
        public TextView date;
        public TextView content;
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
        public ContentMessage getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.info_fragment_cell, null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.date = (TextView) convertView.findViewById(R.id.date);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(data.get(position).getTitle());
            holder.date.setText(data.get(position).getDate());
            holder.content.setText(data.get(position).getContent());
            return convertView;
        }
    }


}
