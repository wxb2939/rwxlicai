package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class ProproductData implements Serializable {

//    data：获取成功[{
//        pid:1，pdtid:2，name:“名称”，pic:“http://xx/xx”，
//        price:150，rule:1，hasfree:false
//    }]

    private String pid;
    private String pdtid;
    private String name;
    private String pic;
    private String price;
    private String rule;
    private Boolean hasfree;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPdtid() {
        return pdtid;
    }

    public void setPdtid(String pdtid) {
        this.pdtid = pdtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Boolean getHasfree() {
        return hasfree;
    }

    public void setHasfree(Boolean hasfree) {
        this.hasfree = hasfree;
    }
}
