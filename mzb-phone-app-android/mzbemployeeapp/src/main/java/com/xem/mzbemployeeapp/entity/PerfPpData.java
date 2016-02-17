package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/22.
 */
//3.22.1	查询品牌门店业绩
public class PerfPpData implements Serializable {

//    [{ branid:2，name:“门店名称”，logo:“品牌图标”，cash:100，work:100 }]
//    data：获取成功[{ branid:2，name:“门店名称”，logo:“品牌图标”，
//        cash:100，craft:200，product:100，work:100，external:100 }]

    private Integer branid;
    private String name;
    private String logo;
    private Float cash;
    private Float craft;
    private Float product;
    private Float work;
    private Float external;

    public Integer getBranid() {
        return branid;
    }

    public void setBranid(Integer branid) {
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

    public Float getCash() {
        return cash;
    }

    public void setCash(Float cash) {
        this.cash = cash;
    }

    public Float getCraft() {
        return craft;
    }

    public void setCraft(Float craft) {
        this.craft = craft;
    }

    public Float getProduct() {
        return product;
    }

    public void setProduct(Float product) {
        this.product = product;
    }

    public Float getWork() {
        return work;
    }

    public void setWork(Float work) {
        this.work = work;
    }

    public Float getExternal() {
        return external;
    }

    public void setExternal(Float external) {
        this.external = external;
    }
}
