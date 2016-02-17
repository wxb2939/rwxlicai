package com.rwxlicai.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 16/1/26.
 */
public class AccountRecord implements Serializable {

//    "type":"xxx",//类型
//            "date":"xxx",//时间
//            "amount":"xxx",//金额
//            "totalAmount":"xxx"//可用金额


    private String type;
    private String date;
    private String amount;
    private String totalAmount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
