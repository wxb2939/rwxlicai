package com.rwxlicai.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.entity.IndexResult;
import com.rwxlicai.view.RoundProgressBar;
import com.rwxlicai.view.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/21.
 */
public class Borrow_getTenderRecord_Activity extends RwxActivity {

    @InjectView(R.id.list)
    PullToRefreshListView listView;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;

    private View headview;
    private MyAdapter adapter;
    private List<IndexResult> data;

    @Override
    protected void initView() {
        setContentView(R.layout.borrow_gettenderrecord_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("投资管理").setLeftImage(R.mipmap.top_view_back);
        data = new ArrayList<>();
        headview = LayoutInflater.from(Borrow_getTenderRecord_Activity.this).inflate(R.layout.borrow_headview, null);
        ListView list_view = listView.getRefreshableView();
        list_view.addHeaderView(headview);
        adapter = new MyAdapter(Borrow_getTenderRecord_Activity.this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

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
            holder.mRoundProgressBar.setProgress((int)mfloat);
//            holder.mRoundProgressBar.setProgress(50);
            holder.title.setText(data.get(position).getTitle()+"-"+data.get(position).getBorrowTitle());
            holder.interestRate.setText(data.get(position).getInterestRate()+"％");
            holder.type.setText(data.get(position).getType());
            holder.borrowSum.setText(data.get(position).getBorrowSum());
            holder.tenderSum.setText(data.get(position).getTenderSum());
            return convertView;
        }
    }
}
