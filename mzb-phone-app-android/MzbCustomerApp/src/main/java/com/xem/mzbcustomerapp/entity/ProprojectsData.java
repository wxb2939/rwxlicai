package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class ProprojectsData implements Serializable {

//    pid：疗程促销id
//    prjid：疗程id
//    name：疗程名称
//    pic：疗程图片
//    count：疗程次数
//    price：疗程价格
//    rule：促销规则类型（参见通用定义）
//    hasfree：是否包含赠送


    private String pid;
    private String prjid;
    private String name;
    private String pic;
    private String count;
    private String price;
    private String rule;
    private Boolean hasfree;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrjid() {
        return prjid;
    }

    public void setPrjid(String prjid) {
        this.prjid = prjid;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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
