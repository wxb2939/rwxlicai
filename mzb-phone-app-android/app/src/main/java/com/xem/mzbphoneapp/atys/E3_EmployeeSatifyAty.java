package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.OrderQuery;
import com.xem.mzbphoneapp.entity.Store;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/4.
 */
public class E3_EmployeeSatifyAty extends MzbActivity {

    @InjectView(R.id.select_left)
    ImageView selectLeft;
    @InjectView(R.id.select_right)
    ImageView selectRight;
    @InjectView(R.id.select_title)
    TextView selectTitle;
    @InjectView(R.id.e2_list)
    ListView orderQueryList;

    private String dayStr;
    private Date date;
    private List<OrderQuery> data;
    private Store storeInfo;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_employee_satify_aty);
        ButterKnife.inject(this);

        new TitleBuilder(this).setTitleText("满意度得分（员工）").setLeftImage(R.mipmap.top_view_back)
                .setRightImage(R.mipmap.top_more).setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2Aty(DialogActivity.class);
            }
        });

    }

    @OnClick({R.id.titlebar_iv_left,R.id.select_right,R.id.select_left})
    public void click(View v){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.select_left:
                c.add(c.DATE, -1);
                date = c.getTime();
                refreshDate();
                break;
            case R.id.select_right:
                c.add(c.DATE, 1);
                date = c.getTime();
                refreshDate();
                break;
            default:
                break;
        }
    }


    private void refreshDate()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        dayStr=sdf.format(date);
        selectTitle.setText(dayStr);
        GetServeRecordList(application.getBranchData().getBranid() + "", dayStr);
    }



    public void GetServeRecordList(String branid,String date) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("date", date);
        params1.put("begin", 0+"");
        params1.put("count", 20+"");


        RequestUtils.ClientTokenPost(E3_EmployeeSatifyAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_BRANCH, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            OrderQuery orderQuery = new OrderQuery();
                            orderQuery = gson.fromJson(object.toString(), OrderQuery.class);
                            data.add(orderQuery);
                        }
                        adapter = new MyAdapter(E3_EmployeeSatifyAty.this);
                        orderQueryList.setAdapter(adapter);
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
                showToast("请求失败");
            }
        });
    }



    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView time;  //美疗师
        public TextView waiter;
        public TextView comments;

    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            // 根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // How many items are in the data set represented by this Adapter.
            // 在此适配器中所代表的数据集中的条目数
            return data.size();
        }

        @Override
        public OrderQuery getItem(int position) {
            // Get the data item associated with the specified position in the
            // data set.
            // 获取数据集中与指定索引对应的数据项
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // Get the row id associated with the specified position in the
            // list.
            // 获取在列表中与指定索引对应的行id
            return position;
        }


        // Get a View that displays the data at the specified position in the
        // data set.
        // 获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            // 如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                // 根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.e2_orderquery_aty_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.e2_order_pic);
                holder.name = (TextView) convertView.findViewById(R.id.e2_order_name);
                holder.time = (TextView) convertView.findViewById(R.id.e2_order_time);
                holder.waiter = (TextView) convertView.findViewById(R.id.e2_order_waiter);
                // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getPortrait(), holder.img);
            holder.name.setText(data.get(position).getName());
            holder.time.setText((CharSequence) data.get(position).getTime());
            holder.waiter.setText((CharSequence) data.get(position).getWaiter());

            /*if (data.get(position).getState() != "1") {
                holder.comments.setText("去评价");
                holder.comments.setTextColor(getResources().getColor(R.color.recordone));
            } else {
                holder.comments.setText("已评价");
                holder.comments.setTextColor(getResources().getColor(R.color.recordtwo));
            }*/
            return convertView;
        }
    }


}
