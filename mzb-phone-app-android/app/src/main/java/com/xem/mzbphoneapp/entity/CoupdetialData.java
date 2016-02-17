package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class CoupdetialData implements Serializable {

//    coupdetial:[{ name:“优惠券”，edate：“2015-7-23”}]

    private String name;
    private String edate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
