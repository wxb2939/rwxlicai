package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/22.
 */
//3.22.3	查询员工业绩
public class PerfEmpData implements Serializable {

//    data：获取成功{ empid:2，cash:100，craft:200，product:100，work:100，external:100，
//        customer:10，project:10 }

    private Integer empid;
    private Float cash;
    private Float craft;
    private Float product;
    private Float work;
    private Float external;
    private Integer customer;
    private Integer project;

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
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

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }
}
