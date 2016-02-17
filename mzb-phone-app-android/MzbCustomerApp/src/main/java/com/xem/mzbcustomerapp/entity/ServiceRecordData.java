package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/29.
 */
public class ServiceRecordData implements Serializable {

//    svrid：服务id
//    logo：品牌logo
//    branch：门店名称
//    pic：项目图片
//    name：项目名称
//    time：服务时间


    private String svrid;
    private String logo;
    private String branch;
    private String pic;
    private String name;
    private String time;

    public String getSvrid() {
        return svrid;
    }

    public void setSvrid(String svrid) {
        this.svrid = svrid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
