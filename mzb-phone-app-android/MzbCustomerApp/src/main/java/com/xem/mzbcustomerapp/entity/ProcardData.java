package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class ProcardData implements Serializable {

//    pid：会员卡促销id
//    name：名称
//    pic：图片
//    money：储值金额
//    rule：促销规则类型（参见通用定义）
//    hasfree：是否包含赠送

    private String pid;
    private String name;
    private String pic;
    private String money;
    private String rule;
    private Boolean hasfree;

    public Boolean getHasfree() {
        return hasfree;
    }

    public void setHasfree(Boolean hasfree) {
        this.hasfree = hasfree;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
