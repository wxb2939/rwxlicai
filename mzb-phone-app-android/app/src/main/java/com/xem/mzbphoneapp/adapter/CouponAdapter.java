package com.xem.mzbphoneapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.E1_CouponDetailAty;
import com.xem.mzbphoneapp.entity.Coupon;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.ShowShare;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.swipemenulistview.SwipeMenuListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by xuebing on 15/7/25.
 */
public class CouponAdapter extends BaseAdapter {

    Context context;
    LayoutInflater mInflater;
    TreeSet<Coupon> set;
    List<Coupon> temp;
    List<Coupon> list = new ArrayList<Coupon>();
    Map<String, List<Coupon>> map = new HashMap<String, List<Coupon>>();
    int count;

    public CouponAdapter(Context context, List<Coupon> list) {
        this.list = list;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        set = new TreeSet<Coupon>(new Comparator<Coupon>() {
            @Override
            public int compare(Coupon lhs, Coupon rhs) {
                return lhs.getPname().compareToIgnoreCase(rhs.pname);
            }
        });

        set.addAll(list);
        temp = new ArrayList<Coupon>(set);
        count = set.size();
        for (Coupon coupon : list)// 循环对传递进来的数据进行分类，将同一种类的Card分配到同一个值中
        {
            if (map.containsKey(coupon.getPname()))// 判断map中有没有这个种类
            // 有的话直接将card 添加到这个种类下
            {
                map.get(coupon.getPname()).add(coupon);
            } else// 没有这个种类 就创建这个种类 将card 添加到这个种类下
            {
                List<Coupon> ds = new ArrayList<Coupon>();
                ds.add(coupon);
                map.put(coupon.getPname(), ds);
            }
        }
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.coupon_item_list, null);
            holder = new Holder();
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            holder.list = (SwipeMenuListView) convertView.findViewById(R.id.list2);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (temp.get(position) != null) {
            holder.text.setText(temp.get(position).getPname());
            ConponItemAdapter adapter = new ConponItemAdapter(context, map.get(temp.get(position).getPname()));
            holder.list.setAdapter(adapter);
/*
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {

                    SwipeMenuItem deleteItem = new SwipeMenuItem(context.getApplicationContext());
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                    deleteItem.setWidth(dp2px(80));
                    deleteItem.setIcon(R.mipmap.ic_action_share);
                    menu.addMenuItem(deleteItem);
                }
            };

            // set creator
            holder.list.setMenuCreator(creator);

            holder.list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                    GetSharecoupdata(1 + "", list.get(position).getCoupid() + "");
//                    new ShowShare(context).showShare();
                }
            });*/

            holder.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    GetSharecoupdata(1 + "", list.get(position).getCoupid() + "");
                    Intent intent=new Intent(context, E1_CouponDetailAty.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coupon", (Serializable) map.get(temp.get(position).getPname()).get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);

//                    intent.putExtra("coupon", (Serializable) map.get(temp.get(position).getPname()));
//                    context.startActivity(intent);


                    /*final SerializableMap myMap=new SerializableMap();
                    myMap.setMap((Map<String, Object>) map.get(temp.get(position).getPname()));//将map数据添加到封装的myMap<span></span>中
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("map", myMap);
                    intent.putExtras(bundle);*/

                    context.startActivity(intent);
                }
            });

            Utility.setListViewHeightBasedOnChildren(holder.list);
        }
        return convertView;
    }

    class Holder {
        TextView text;
        SwipeMenuListView list;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }


    public void GetSharecoupdata(String type, String coupid) {

        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("coupid", coupid);
        RequestUtils.ClientTokenPost(context, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_COUPDATA, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("data");
                        new ShowShare(context).ConponShowshare(data.getString("title"), data.getString("content"), data.getString("logo"), data.getString("url"));
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请求失败,请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
