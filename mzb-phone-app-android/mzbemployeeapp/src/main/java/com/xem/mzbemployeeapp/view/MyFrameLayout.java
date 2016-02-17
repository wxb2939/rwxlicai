package com.xem.mzbemployeeapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.xem.mzbemployeeapp.activity.E2_CommPerfBranchAty_New;

/**
 * Created by wellke on 2015/12/14.
 */
public class MyFrameLayout extends FrameLayout{
    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (E2_CommPerfBranchAty_New.isVisible){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
