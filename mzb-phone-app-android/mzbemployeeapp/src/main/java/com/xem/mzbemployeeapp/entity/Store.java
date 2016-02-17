package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/6/30.
 */
public class Store implements Serializable {


    private int ppid;
    private int branid;
    private int custid;
    private String logo;
    private String name;
    private String address;
    private String tel;


    public Store() {
    }

    public Store(String address, int branid, int custid, String logo, String name, int ppid, String tel) {
        this.address = address;
        this.branid = branid;
        this.custid = custid;
        this.logo = logo;
        this.name = name;
        this.ppid = ppid;
        this.tel = tel;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBranid() {
        return branid;
    }

    public void setBranid(int branid) {
        this.branid = branid;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
