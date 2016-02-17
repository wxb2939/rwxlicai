package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/27.
 */
public class branch implements Serializable {

//    branch: { ppid:1，branid:2，custid:3 }

    private String ppid;
    private String branid;
    private String custid;

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getBranid() {
        return branid;
    }

    public void setBranid(String branid) {
        this.branid = branid;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }
}
