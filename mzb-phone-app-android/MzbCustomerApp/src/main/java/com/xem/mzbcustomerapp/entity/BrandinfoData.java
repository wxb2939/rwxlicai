package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/28.
 */
public class BrandinfoData implements Serializable {

//    data：获取成功{
//        branid:1，name:“门店名称”，logo：“http://xxx”，pname：“品牌名称”，
//        tel：“联系电话”，address：“地址”，hours：“营业时间”，
//        condition：[{ bcid:1，pic:“http://xxx”}]
//       }

    private String branid;
    private String name;
    private String logo;
    private String pname;
    private String tel;
    private String address;
    private String hours;
    private BconditionData bconditionData;

    public String getBranid() {
        return branid;
    }

    public void setBranid(String branid) {
        this.branid = branid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public BconditionData getBconditionData() {
        return bconditionData;
    }

    public void setBconditionData(BconditionData bconditionData) {
        this.bconditionData = bconditionData;
    }
}