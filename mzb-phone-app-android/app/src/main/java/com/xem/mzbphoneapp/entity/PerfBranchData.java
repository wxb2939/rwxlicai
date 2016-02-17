package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/22.
 */
//3.22.2	查询门店员工业绩
public class PerfBranchData implements Serializable {

//    data：获取成功[{ empid:2，name:“王美丽”，position:“美疗师”，
//        cash:100，craft:200，product:100，work:100，external:100 }]
    private Integer empid;
    private String name;
    private String position;
    private Float cash;
    private Float craft;
    private Float product;
    private Float work;
    private Float external;

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
