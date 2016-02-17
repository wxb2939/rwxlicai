package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/16.
 */
public class ServiceDetailData implements Serializable {

//    vrid：服务id
//    branid：门店id
//    pid：项目id
//    pic：项目图片
//    name：项目名称
//    consultant：顾问
//    beautician：美疗师
//    time：服务时间
//    finished：是否已经评价
//    conscore：顾问得分
//    beascore：美疗师得分
//    serscore：服务得分
//    rescore：护理效果得分
//    comment：评分备注

    private Integer vrid;
    private Integer branid;
    private Integer pid;
    private String pic;
    private String name;
    private String consultant;
    private String beautician;
    private String time;
    private String finished;
    private String conscore;
    private String beascore;
    private String serscore;
    private String rescore;
    private String comment;

    public Integer getVrid() {
        return vrid;
    }

    public void setVrid(Integer vrid) {
        this.vrid = vrid;
    }

    public Integer getBranid() {
        return branid;
    }

    public void setBranid(Integer branid) {
        this.branid = branid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getBeautician() {
        return beautician;
    }

    public void setBeautician(String beautician) {
        this.beautician = beautician;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getConscore() {
        return conscore;
    }

    public void setConscore(String conscore) {
        this.conscore = conscore;
    }

    public String getBeascore() {
        return beascore;
    }

    public void setBeascore(String beascore) {
        this.beascore = beascore;
    }

    public String getSerscore() {
        return serscore;
    }

    public void setSerscore(String serscore) {
        this.serscore = serscore;
    }

    public String getRescore() {
        return rescore;
    }

    public void setRescore(String rescore) {
        this.rescore = rescore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
