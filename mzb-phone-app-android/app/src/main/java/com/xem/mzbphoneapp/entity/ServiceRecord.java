package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/8.
 */
public class ServiceRecord implements Serializable {

    private int id;
    private String svrid;//服务id
    private String pid; //项目id
    private String pic; //项目图片
    private String name; //项目名称
    private String consultant; //顾问
    private String beautician; //美疗师
    private String time; //服务时间
    private Boolean finished; //finished

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSvrid() {
        return svrid;
    }

    public void setSvrid(String svrid) {
        this.svrid = svrid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
