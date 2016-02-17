package com.rwxlicai.activity;

import android.view.View;
import android.widget.Button;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.view.RoundProgressBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class CircleTest extends RwxActivity {

    @InjectView(R.id.roundProgressBar2)
    RoundProgressBar mRoundProgressBar;
    @InjectView(R.id.button1)
    Button button;

    private int progress = 0;
    private int Max = 81;


    @Override
    protected void initView() {
        setContentView(R.layout.cirlcetest);
        ButterKnife.inject(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.button1})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.button1:
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (progress <= Max) {
                            progress += 5;
                            System.out.println(progress);
                            mRoundProgressBar.setProgress(progress);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
//                mRoundProgressBar.setProgress(progress);
                break;
        }
    }
}
