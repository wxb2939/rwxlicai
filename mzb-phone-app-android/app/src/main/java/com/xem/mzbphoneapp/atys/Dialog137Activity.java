package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.view.MzbDialog;

/**
 * Created by xuebing on 15/9/4.
 */
public class Dialog137Activity extends MzbActivity implements View.OnClickListener {

    View layout01;
    View layout02;
    Intent intent;
    private MzbDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_137_dialog);
        initView();
    }

    private void initView() {
        //得到布局组件对象并设置监听事件
        layout01 = findViewById(R.id.ll1);
        layout02 = findViewById(R.id.ll2);

        layout01.setOnClickListener(this);
        layout02.setOnClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        intent = new Intent();
        switch (v.getId()) {
            case R.id.ll1:
                intent.putExtra("age", 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.ll2:
                showmToast();

                break;
            default:
                break;
        }
    }

    private void showmToast(){
        String exit = "取消";
        String ensure_exit = "是否确定取消关注！";

        mDialog = new MzbDialog(Dialog137Activity.this, exit, ensure_exit);
        mDialog.show();
        mDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                intent.putExtra("age", 2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mDialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }
}
