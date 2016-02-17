package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class PdtdetailData implements Serializable {

//    pdtdetail:[{ name:“护肤品”，count:2，present:false }]，

    private String name;
    private Integer count;
    private Boolean present;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
