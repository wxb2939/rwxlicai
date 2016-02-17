package com.xem.mzbemployeeapp.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import view.XListView;


/**
 * Created by xuebing on 15/6/3.
 */
public class MzbListView extends XListView implements XListView.IXListViewListener {

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    public FrameLayout bannerView;

    public MzbListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        mGestureDetector = new GestureDetector(new YScrollDetector());

        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            if (distanceY != 0 && distanceX != 0) {

            }

            if (null != bannerView) {
                Rect rect = new Rect();
                bannerView.getHitRect(rect);

                if (null != e1) {
                    if (rect.contains((int) e1.getX(), (int) e1.getY())) {
                        return false;
                    }
                }

                if (null != e2) {
                    if (rect.contains((int) e2.getX(), (int) e2.getY())) {
                        return false;
                    }
                }

            }
            return true;
        }
    }

}
