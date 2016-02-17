package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/29.
 */
public class SalegroupCateData implements Serializable {

//    ppid：品牌id
//    cateid：分类id
//    name：分类名称
//    count：数量

    private String ppid;
    private String cateid;
    private String name;
    private String count;

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
