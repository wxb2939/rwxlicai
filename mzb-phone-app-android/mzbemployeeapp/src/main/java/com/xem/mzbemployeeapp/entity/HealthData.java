package com.xem.mzbemployeeapp.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/21.
 */
public class HealthData {
    public ArrayList<Data> data;

    class Data{
        public long rcount;
        public String description;
        public String keywords;
        public String title;
        public long count;
        public long loreclass;
        public long id;
        public String img;
        public long fcount;
        public long time;
    }

}
