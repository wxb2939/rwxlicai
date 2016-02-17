package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class InfoData implements Serializable {

//    data：获取成功 { empid:100, ppid:1, branid:2, name:””张三”,
//    portrait:””http://xxx.xxx.file/xx-xxx-xx”，position:”美疗师”， performance:10 }；

    private Integer empid;
    private Integer ppid;
    private Integer branid;
    private String name;
    private String portrait;
    private String position;
    private Float performance;


    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Integer getBranid() {
        return branid;
    }

    public void setBranid(Integer branid) {
        this.branid = branid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Float getPerformance() {
        return performance;
    }

    public void setPerformance(Float performance) {
        this.performance = performance;
    }
}
