package com.xem.mzbphoneapp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuebing on 15/7/25.
 */
public class Coupon implements Serializable {

    private int coupid; //优惠券id
    private int ppid;   //品牌id
    public String pname; //品牌名称
    private String type; //优惠券类型
    private String name; //优惠券名称
    private String pic; //优惠券图片
    private String sdate; //开始时间
    private String edate; //结束时间
    private String share; //是否支持分享
    private String scode; //分享码
    private String desc;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Coupon() {
        super();
    }

    public Coupon(int coupid, int ppid, String pname, String type, String name, String pic, String sdate, String edate, String share, String scode) {
        this.coupid = coupid;
        this.ppid = ppid;
        this.pname = pname;
        this.type = type;
        this.name = name;
        this.pic = pic;
        this.sdate = sdate;
        this.edate = edate;
        this.share = share;
        this.scode = scode;
    }

    public int getCoupid() {
        return coupid;
    }

    public void setCoupid(int coupid) {
        this.coupid = coupid;
    }

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }
}
