package com.xem.mzbemployeeapp.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



/**
 * Created by xuebing on 15/6/29.
 */
public class Utility {

    public int wid;
    private static Utility utility = new Utility();
    public static Utility getInstance() {
        return utility;
    }


    // 角色权限控制

    public boolean CheckStroeRight(Set<String> rights)
    {
        if (rights == null || rights.size() ==0)
            return false;
        List list = new ArrayList();
        list.add("101");
        list.add("102");
        list.add("103");
        list.add("108");
        list.add("110");

        Iterator<String> it = rights.iterator();
        while (it.hasNext())
        {
            if (list.contains(it.next())){
                return true;
            }
        }
        return false;
    }

    //美疗师，服务权限控制
    public boolean isMlsorGw(Set<String> rights)
    {
        if (rights == null || rights.size() ==0)
            return false;
        List list = new ArrayList();
        list.add("114");
        list.add("115");
        Iterator<String> it = rights.iterator();
        while (it.hasNext())
        {
            if (list.contains(it.next())){
                return true;
            }
        }
        return false;
    }




    public LayoutParams getLayoutParams(View view, Fragment fragment) {
        LayoutParams params = view.getLayoutParams();
        wid = getDisplayMetricsWidth(fragment);
        params.width = wid;
        params.height = (int) (wid * 0.5);
        view.setLayoutParams(params);
        return params;
    }


    public LayoutParams getLayoutThreeParams(View view, Fragment fragment) {
        LayoutParams params = view.getLayoutParams();
        wid = getDisplayMetricsWidth(fragment);
        params.width = wid;
        params.height = (int) (wid * 0.3);
        view.setLayoutParams(params);
        return params;
    }

    public LayoutParams getLayoutThreeParamsAty(View view, Context context) {
        LayoutParams params = view.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wid = (int) wm.getDefaultDisplay().getWidth();
        params.width = wid;
        params.height = (int) (wid * 0.3);
        view.setLayoutParams(params);
        return params;
    }


    //获取屏幕宽度
    public int getDisplayMetricsWidth(Fragment fragment) {
        int i = fragment.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        return i;
    }


    private static long lastClickTime;

    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public void hasDataLayout(Context context,Boolean hasData,ViewGroup view ,String title,int imgId){
        View netGrounds = View.inflate(context, R.layout.ground_comm_back, null);
        ImageView iv_img = (ImageView) netGrounds.findViewById(R.id.def_img);
        TextView tv_title = (TextView) netGrounds.findViewById(R.id.def_title);

        if (hasData) {
//            view.removeView(netGrounds);
        }else {
            iv_img.setImageResource(imgId);//R.mipmap.default_store
            tv_title.setText(title);
            view.addView(netGrounds);
        }
    }


    public View getDataLayout(Context context,Boolean hasData,ViewGroup view ,String title,int imgId,View sub){
        View netGrounds = View.inflate(context, R.layout.ground_comm_back, null);
        ImageView iv_img = (ImageView) netGrounds.findViewById(R.id.def_img);
        TextView tv_title = (TextView) netGrounds.findViewById(R.id.def_title);

        if (hasData) {
            view.removeViewInLayout(sub);
            return null;
        }else {
            view.removeViewInLayout(sub);
            iv_img.setImageResource(imgId);//R.mipmap.default_store
            tv_title.setText(title);
            view.addView(netGrounds);
        }
        return netGrounds;
    }



    public String getNullStr(String value)
    {
        if (value == null){
            return "";
        }
        return value.toString();
    }



    public void clickEffect(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.color.click_effect);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.color.common_white);
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.color.common_white);
                }
                return false;
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //stAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //lstView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public Date toDate(String time){
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }




}
