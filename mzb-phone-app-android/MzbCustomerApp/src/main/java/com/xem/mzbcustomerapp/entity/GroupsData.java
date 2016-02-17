package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class GroupsData implements Serializable {

//    data：获取成功[{groupid:1，name:“面部套餐”，pic:“http://xx/xx”，price:150}]
//    groupid：套餐id
//        name：套餐名称
//        pic：套餐图片
//        price：套餐价

    private String groupid;
    private String name;
    private String pic;
    private String price;

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
}
