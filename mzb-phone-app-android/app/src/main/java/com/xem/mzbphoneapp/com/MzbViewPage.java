package com.xem.mzbphoneapp.com;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;

import com.xem.mzbphoneapp.fragment.MzbFragment;

/**
 * Created by xuebing on 15/6/3.
 */
public class MzbViewPage extends ViewPager {

    private MzbFragment fragment;
    private float curX;
    private float curY;
    private float minMove = 200;

    public MzbViewPage(Context context) {
        super(context);
    }

    public void setFrgment(MzbFragment _frgment) {
        fragment = _frgment;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean ret = true;
        final int action = ev.getAction();
        Log.d("mzb", "onInterceptTouchEvent " + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                curX = ev.getX();
                curY = ev.getY();
                if (fragment != null)
                    fragment.onStop();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (fragment != null) {
                    //ret = ;
                    if (!doTouchEvent(ev.getX(), ev.getY())) {
                        fragment.onStop();
                        super.onTouchEvent(ev);

                    }
                    fragment.onStop();
                }

                break;
        }
        return ret;
    }

    private boolean doTouchEvent(float x, float y) {
        boolean ret = true;
        float mX = x - this.curX;

        if (mX < -this.minMove) {//left
            fragment.onStart();
            fragment.onPictureTouch(true);

        } else if (mX <= this.minMove) {//click
            ret = false;
        } else {//right
            fragment.onStart();
            fragment.onPictureTouch(false);
        }
        //boolean moveDown = (y- this.curY) >this.minMove;

//		fragment.onStop();
        return ret;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //super.onInterceptTouchEvent(ev);
        getParent().requestDisallowInterceptTouchEvent(true);
        float mX = ev.getX() - this.curX;
        System.out.println("====AAA====" + Math.abs(mX));

        if (Math.abs(mX) < this.minMove) {
            fragment.onStop();
            return false;
        }
        return true;
    }

}
