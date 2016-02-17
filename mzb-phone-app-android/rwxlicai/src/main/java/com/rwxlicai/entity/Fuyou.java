package com.rwxlicai.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 16/2/1.
 */
public class Fuyou implements Serializable {

//            {"ono":"6217001210019000437",
//            "gatewayUrl":"http://116.239.4.194:18670/mobile_pay/timbnew/timb01.pay",
//            "homeurl":"http://www.rwxlicai.com/web/user/recharge.do",
//            "name":"王学兵",
//            "md5":"3594d164a6950982a9512f7f852da859",
//            "orderid":"E10220160201093146795860",
//            "sfz":"622421198811092939",
//            "backurl":"http://www.rwxlicai.com/web/recvFromFuYou.do",
//            "reurl":"http://www.rwxlicai.com/web/user/recharge.do",
//            "mchntCd":"0002900F0280210”}}


    private String ono;
    private String gatewayUrl;
    private String homeurl;
    private String name;
    private String md5;
    private String orderid;
    private String sfz;
    private String backurl;
    private String reurl;
    private String mchntCd;

    public String getOno() {
        return ono;
    }

    public void setOno(String ono) {
        this.ono = ono;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getHomeurl() {
        return homeurl;
    }

    public void setHomeurl(String homeurl) {
        this.homeurl = homeurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getBackurl() {
        return backurl;
    }

    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getReurl() {
        return reurl;
    }

    public void setReurl(String reurl) {
        this.reurl = reurl;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }
}
