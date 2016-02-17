package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/4.
 */
public class ConditionData implements Serializable {

//    condition：[{ bcid:1，pic:“http://xxx”}]

    private int bcid;
    private String name;

    public int getBcid() {
        return bcid;
    }

    public void setBcid(int bcid) {
        this.bcid = bcid;
    }

    public String getPic() {
        return name;
    }

    public void setPic(String name) {
        this.name = name;
    }
}
