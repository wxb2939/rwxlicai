package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/31.
 */
public class BranchStore implements Serializable {
//    品牌门店列表（branid表示门店id，name表示门店名称）

    private String branid;
    private String name;

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
}
