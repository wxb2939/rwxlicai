package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.OrderRecord;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;
import com.xem.swipemenulistview.SwipeMenu;
import com.xem.swipemenulistview.SwipeMenuCreator;
import com.xem.swipemenulistview.SwipeMenuItem;
import com.xem.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/2.
 */
public class B1_OrderRecordAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.b1_orderlist_ground)
    RelativeLayout orderlistGround;
    @InjectView(R.id.b1_orderlist)
    SwipeMenuListView mListView;


    private List<OrderRecord> data;
    private OrderRecordAdapter adapter;
    private String branid;
    private String custid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1_order_record_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("预约纪录").setLeftImage(R.mipmap.top_view_back);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        branid = bundle.getInt("branid") + "";
        custid = bundle.getInt("custid") + "";
        GetOrderRecordList(0);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 1:
                        createMenu1(menu);
                        break;
                    case 2:
                        createMenu1(menu);
                        break;
                    default:
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xbc, 0x31, 0x72)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("取消预约");
                deleteItem.setTitleSize(15);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                OrderRecord item = data.get(position);
                CancelOrder(item.getAppoid(), position);
//                adapter.notifyDataSetChanged();
            }
        });

       /* mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderRecord item = data.get(position);
                CancelOrder(item.getAppoid(), position);
            }
        });
*/

       /* mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            default:
                break;
        }
    }


    public void GetOrderRecordList(final int page) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("custid", custid);
        params1.put("begin", page + "");
        params1.put("count", 20 + "");


        RequestUtils.ClientTokenPost(B1_OrderRecordAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_RECORDS, params1, new NetCallBack(this) {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {

                data = new ArrayList<OrderRecord>();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    Gson gson = new Gson();//new一个Gson对象
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            OrderRecord orderRecord = new OrderRecord();
                            orderRecord = gson.fromJson(object.toString(), OrderRecord.class);
                            data.add(orderRecord);
                        }

                        Utility.getInstance().hasDataLayout(B1_OrderRecordAty.this, data.size() != 0, orderlistGround, "预约纪录为空", R.mipmap.yuyue_err);
                        adapter = new OrderRecordAdapter(B1_OrderRecordAty.this);
                        mListView.setAdapter(adapter);
                    } else {
                        Toast.makeText(B1_OrderRecordAty.this, obj.getString("message"), Toast.LENGTH_LONG).show();
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

    public void CancelOrder(int appoid, final int positon) {
        RequestParams params1 = new RequestParams();
        params1.put("appoid", appoid + "");


        RequestUtils.ClientTokenPost(B1_OrderRecordAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_CANCEL, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("预约取消成功");
                        data.get(positon).setState(3);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.notifyDataSetChanged();
                        showToast("预约取消失败");
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


    // ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView storename;
        public TextView ordertime;
        public TextView servicename;
        public TextView btn;
    }

    private class OrderRecordAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private OrderRecordAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {

            return data.get(position).getState();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.b1_order_record_cell, null);
                holder.img = (ImageView) convertView.findViewById(R.id.b1_orderrecord_img);
                holder.storename = (TextView) convertView.findViewById(R.id.b1_orderrecord_name);
                holder.ordertime = (TextView) convertView.findViewById(R.id.b1_orderrecord_time);
                holder.servicename = (TextView) convertView.findViewById(R.id.b1_orderrecord_service);
                holder.btn = (TextView) convertView.findViewById(R.id.b1_orderrecord_btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            imageLoader.displayImage(data.get(position).getLogo(), holder.img);
            holder.storename.setText(data.get(position).getName());
            holder.ordertime.setText(data.get(position).getTime());
            String name = data.get(position).getWaiter();
            if (name == (null)) {
                holder.servicename.setText("");
            } else {
                holder.servicename.setText(name);
            }


            switch (data.get(position).getState()) {
                case 1:
                    holder.btn.setText(getResources().getString(R.string.order_one));
                    holder.btn.setTextColor(getResources().getColor(R.color.recordone));
                    break;
                case 2:
                    holder.btn.setText(getResources().getString(R.string.order_two));
                    holder.btn.setTextColor(getResources().getColor(R.color.recordtwo));
                    break;
                case 3:
                    holder.btn.setText(getResources().getString(R.string.order_three));
                    holder.btn.setTextColor(getResources().getColor(R.color.recordthree));
                    break;
                case 4:
                    holder.btn.setText(getResources().getString(R.string.order_four));
                    holder.btn.setTextColor(getResources().getColor(R.color.recordfour));
                    break;
                default:
                    break;
            }
            return convertView;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
