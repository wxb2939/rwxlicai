package com.rwxlicai.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 16/1/26.
 */
public class TenderRecord implements Serializable {

//            "title":"xxx",//标题
//            "rate":"xxx",//年利率
//            "amount":"xxx",//本金
//            "interest":"xxx",//利息
//            "startDate":"xxx",//开始时间
//            "endDate":"xxx",//结束时间
//            "tenderStatus":"xxx"//状态

    private String title;
    private String rate;
    private String amount;
    private String interest;
    private String startDate;
    private String endDate;
    private String tenderStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

}
