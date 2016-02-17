package com.xem.mzbemployeeapp.entity;

/**
 * Created by Administrator on 2015/11/19.
 */
public class GridViewDetail {
    public String desc;
    public Integer images;

    public GridViewDetail(String desc, Integer images){
        this.desc=desc;
        this.images=images;
    }

    @Override
    public boolean equals(Object o) {
        GridViewDetail gvd=(GridViewDetail)o;
        return gvd.desc.equals(this.desc);
    }
}
