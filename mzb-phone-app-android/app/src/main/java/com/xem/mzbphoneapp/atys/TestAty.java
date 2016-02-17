package com.xem.mzbphoneapp.atys;

import android.os.Bundle;

import com.xem.mzbphoneapp.R;

import java.util.Date;
import java.util.List;

/**
 * Created by xuebing on 15/8/20.
 */
public class TestAty extends MzbActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testaty);


        Date date = new Date();

        String json = "{ " +
                "'str':null, 'date':'2015-08-01', 'b':null, 'i':null, 'd':null, " +
                "'sub':{}, " +
                "'list':[]" +
                "}";

        TestData td = gson.fromJson(json, TestData.class);
        System.out.println(td);

    }

    class TestData
    {
        public String str;
        public String date;
        public Boolean b;
        public Integer i;
        public Double d;

        public SubTestData sub;

        public List<SubTestData> list;
    }

    class SubTestData
    {
        public String name;
    }
}

