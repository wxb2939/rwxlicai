package com.xem.mzbcustomerapp.entity;

/**
 * Created by xuebing on 15/11/25.
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
