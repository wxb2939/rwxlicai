package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/3.
 */
public class CalendarData implements Serializable {
//3.13.6	客户计划日历
//    data：查询成功 [{ planid:10，date:“2015-7-1”，type:1，finished:false }]

    private Integer planid;
    private String date;
    private Integer type;
    private Boolean finished;

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public String getDate() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
