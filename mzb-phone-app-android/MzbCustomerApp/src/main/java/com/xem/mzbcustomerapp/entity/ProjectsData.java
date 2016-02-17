package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/28.
 */
public class ProjectsData implements Serializable {

//    data：获取成功[{projectid:1，name:“项目”，pic:“http://xx/xx”，price:150 }]
//    projectid：项目id
//        name：项目名称
//        pic：项目图片
//        price：项目会员价

    private String projectid;
    private String name;
    private String pic;
    private String price;

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
