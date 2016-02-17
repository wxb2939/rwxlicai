package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/27.
 */
public class CloginData implements Serializable {

//    2.1.3	会员端登录
//    data: 登录成功 {
//        uid:1234, token:”xxxxx”, name:””张三”, score:90, sex：“F”，
//        desc:“个人说明”，portrait:“http://xxx.xxx.file/xx-xxx-xx”，
//        branch: { ppid:1，branid:2，custid:3 }
//    }；

    private Integer uid;
    private String token;
    private String name;
    private Float score;
    private String sex;
    private String desc;
    private String portrait;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

}
