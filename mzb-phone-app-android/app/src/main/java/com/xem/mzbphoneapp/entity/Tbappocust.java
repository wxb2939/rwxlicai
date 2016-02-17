package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class Tbappocust implements Serializable {

//    data：获取成功{ appoid:2，name:“王美丽”，phone:“13186741510”，pic:“http://xxx/xx”，
//    time:“15:00”，waiter:“小李”，project:“预约项目”，person:2 }
    private Integer appoid;
    private String name;
    private String phone;
    private String pic;
    private String time;
    private String waiter;
    private String[] project;

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    private Integer person;

    public Integer getAppoid() {
        return appoid;
    }

    public void setAppoid(Integer appoid) {
        this.appoid = appoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String[] getProject() {
        return project;
    }

    public void setProject(String[] project) {
        this.project = project;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }
}
