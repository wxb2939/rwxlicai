package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/29.
 */
public class PstdetailData implements Serializable {

//    pstdetail：赠金账户明细（amount表示金额，edate表示截至日期）

    private String amount;
    private String edate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
