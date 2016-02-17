package com.xem.mzbphoneapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by xuebing on 15/12/14.
 */
public class MzbFramLayout extends FrameLayout {
    private Button button;



    public MzbFramLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
