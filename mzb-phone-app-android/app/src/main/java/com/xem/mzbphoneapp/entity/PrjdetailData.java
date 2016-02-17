package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class PrjdetailData implements Serializable {

//    prjdetail:[{ itemid:1，prjid:1，name:“面部按摩”，count:5，present:false }]，

    private Integer itemid;
    private Integer prjid;
    private String name;
    private Integer count;
    private Boolean present;

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getPrjid() {
        return prjid;
    }

    public void setPrjid(Integer prjid) {
        this.prjid = prjid;
    }

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
