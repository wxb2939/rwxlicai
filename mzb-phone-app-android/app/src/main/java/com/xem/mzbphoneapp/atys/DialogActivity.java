package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.xem.mzbphoneapp.R;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/25.
 */
public class DialogActivity extends MzbActivity implements OnClickListener {

    View layout01;
    View layout02;
    View layout03;
    View layout04;
    Intent intent;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        flag = getIntent().getIntExtra("flag",0);
        initView();
    }

    private void initView(){
        //得到布局组件对象并设置监听事件
        layout01 = findViewById(R.id.llayout01);
        layout02 = findViewById(R.id.llayout02);
        layout03 = findViewById(R.id.llayout03);
        layout04 = findViewById(R.id.llayout04);
        if (flag == 0){
            layout01.setVisibility(View.GONE);
        }else {
            layout01.setVisibility(View.VISIBLE);
        }


        layout01.setOnClickListener(this);
        layout02.setOnClickListener(this);
        layout03.setOnClickListener(this);
        layout04.setOnClickListener(this);
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
            case R.id.llayout01:
                intent.putExtra("age", 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.llayout02:
                intent.putExtra("age", 2);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.llayout03:
                intent.putExtra("age", 3);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.llayout04:
                intent.putExtra("age", 4);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
