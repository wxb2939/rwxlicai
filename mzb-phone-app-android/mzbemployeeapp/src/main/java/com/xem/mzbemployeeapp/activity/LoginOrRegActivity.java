package com.xem.mzbemployeeapp.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.application.MzbApplication;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2015/11/20.
 */
public class LoginOrRegActivity extends  MzbActivity{

    //登录体验
    @InjectView(R.id.logty)
    Button logty;
    //登录
    @InjectView(R.id.log)
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logreg);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            MzbApplication.getInstance().exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String str;

    @OnClick({R.id.logty, R.id.log})
    public void onDo(View v) {
        switch (v.getId()){
            //登录体验
            case R.id.logty:{
                str="flag";
                intent2Aty(A0_ExpAty.class);
                finish();
                break;
            }
            //登录
            case R.id.log:{
                intent2Aty(A0_LoginAty.class);
                finish();
                break;
            }
        }
    }

}
