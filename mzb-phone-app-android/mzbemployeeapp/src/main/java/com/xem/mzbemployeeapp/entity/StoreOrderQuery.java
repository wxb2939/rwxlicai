package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class StoreOrderQuery implements Serializable {
//    data：查询成功{ branid:2, name：“门店”，logo：“http://xx/xx”，count:8 }
    private Integer branid;
    private String name;
    private String logo;
    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
