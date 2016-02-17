package com.xem.mzbcustomerapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xem.mzbcustomerapp.R;

/**
 * Created by xuebing on 15/10/27.
 */
public class TestDemo extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdemo);
        findViewById(R.id.dianji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        login();
                    }
                }.start();


            }
        });


    }


    private void login(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.163.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                System.out.println("@@@!!!!");
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("============" + response);
            }


            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                System.out.println("============!!!");
            }


            @Override
            public void onFinish() {
                super.onFinish();
                System.out.println("============!00000");
            }
        });
    }
}