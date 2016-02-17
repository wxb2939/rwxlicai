package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class PcardsData implements Serializable {

//    data：获取成功[{
//        cardid:1，name:“白金卡”，pic:“http://xx/xx”，
//        money:150，hasfree:false
//    }]


    private String cardid;
    private String name;
    private String pic;
    private String money;
    private Boolean hasfree;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
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

    public Boolean getHasfree() {
        return hasfree;
    }

    public void setHasfree(Boolean hasfree) {
        this.hasfree = hasfree;
    }
}
