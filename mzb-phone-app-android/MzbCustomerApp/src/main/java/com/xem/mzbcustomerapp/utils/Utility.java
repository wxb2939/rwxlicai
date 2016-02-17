package com.xem.mzbcustomerapp.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbcustomerapp.R;

/**
 * Created by xuebing on 15/10/29.
 */
public class Utility {

    public int wid;
    private static Utility utility = new Utility();
    public static Utility getInstance() {
        return utility;
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

    public View hasDataLayout(Context context, Boolean hasData, ViewGroup view, String title, int imgId){
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
        return netGrounds;
    }



    public ViewGroup.LayoutParams getLayoutParams(View view, Context context) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wid = (int) wm.getDefaultDisplay().getWidth();
        params.width = (int) (wid*0.8);
        params.height = (int) (wid * 0.6);
        view.setLayoutParams(params);
        return params;
    }

    //获取屏幕宽度
    public int getDisplayMetricsWidth(Fragment fragment) {
        int i = fragment.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        return i;
    }

}
