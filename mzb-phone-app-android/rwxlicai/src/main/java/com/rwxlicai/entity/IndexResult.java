package com.rwxlicai.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 16/1/19.
 */
public class IndexResult implements Serializable {

//    "result": [
//            "type":"xxx",//(day、quarter、half、year)
//            "title":"xxx",//标题
//            ("borrowTitle":"xxx",//标题
//            "interestRate":"xxx",//年利率
//            "borrowSum":"xxx",//借款总额
//            "tenderSum":"xxx",//已投金额
//            "progressRate":"xxx"//进度)
//            ]

//    "type":"xxx",//(day、quarter、half、year)
//            "title":"xxx",//标题
//            ("bid":"xxx",//标id（加密）
//            "borrowTitle":"xxx",//标题
//            "isDay":"xxx",//是否天标(1.天，2.月)
//            "borrowTimeLimit":"xxx",//借款期限
//            "repaymentStyle":"xxx",//还款方式
//            "interestRate":"xxx",//年利率
//            "borrowSum":"xxx",//借款总额
//            "tenderSum":"xxx",//已投金额
//            "progressRate":"xxx",//进度
//            "tenderTime":"xxx"//发布时间)


    private String type;
    private String title;
    private String bid;
    private String isDay;
    private String borrowTimeLimit;
    private String repaymentStyle;
    private String interestRate;
    private String borrowTitle;
    private String borrowSum;
    private String tenderSum;
    private String tenderTime;

    public String getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(String tenderTime) {
        this.tenderTime = tenderTime;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }

    public String getBorrowTimeLimit() {
        return borrowTimeLimit;
    }

    public void setBorrowTimeLimit(String borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
    }

    private String progressRate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getBorrowSum() {
        return borrowSum;
    }

    public void setBorrowSum(String borrowSum) {
        this.borrowSum = borrowSum;
    }

    public String getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(String tenderSum) {
        this.tenderSum = tenderSum;
    }

    public String getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }
}
