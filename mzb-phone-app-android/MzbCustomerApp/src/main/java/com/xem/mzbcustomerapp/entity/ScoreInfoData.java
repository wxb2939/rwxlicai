package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 *   2.6.2	服务评分详情
 * Created by xuebing on 15/11/8.
 */
public class ScoreInfoData implements Serializable {

//    svrid：服务id
//    consultant：顾问
//    beautician：美疗师
//    finished：是否已经评价
//    conscore：顾问得分
//    beascore：美疗师得分
//    serscore：服务得分
//    rescore：护理效果得分
//    comment：评分备注

    private String svrid;
    private String consultant;
    private String beautician;
    private Boolean finished;
    private String conscore;
    private String beascore;
    private String serscore;
    private String rescore;
    private String comment;

    public int branid;

    public String getSvrid() {
        return svrid;
    }

    public void setSvrid(String svrid) {
        this.svrid = svrid;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
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
