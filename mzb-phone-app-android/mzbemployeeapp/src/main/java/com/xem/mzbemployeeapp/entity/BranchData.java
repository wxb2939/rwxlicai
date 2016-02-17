package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/28.
 */
public class BranchData implements Serializable {

//    branch: { ppid:1, branid:2, custid:3, logo:“http://xxx/xx”, name:“丽丽美容浦东店”, address:“人民南路118号”，tel:“15800558122”}

    private Integer ppid;
    private Integer branid;
    private Integer custid;
    private String logo;
    private String name;
    private String address;
    private String tel;

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Integer getBranid() {
        return branid;
    }

    public void setBranid(Integer branid) {
        this.branid = branid;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
