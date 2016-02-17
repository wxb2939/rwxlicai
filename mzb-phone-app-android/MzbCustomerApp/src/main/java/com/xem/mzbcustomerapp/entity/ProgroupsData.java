package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class ProgroupsData implements Serializable {

//    pid：套餐促销id
//    groupid：套餐id
//    name：套餐名称
//    pic：套餐图片
//    price：套餐价格
//    rule：促销规则类型（参见通用定义）
//    hasfree：是否包含赠送

    private String pid;
    private String groupid;
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

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
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
