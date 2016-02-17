package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/27.
 */
public class CdefaultData implements Serializable {

    private Integer uid;
    private cate cate;
    private branch branch;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public cate getCate() {
        return cate;
    }

    public void setCate(cate cate) {
        this.cate = cate;
    }

    public branch getBranch() {
        return branch;
    }

    public void setBranch(branch branch) {
        this.branch = branch;
    }
}
