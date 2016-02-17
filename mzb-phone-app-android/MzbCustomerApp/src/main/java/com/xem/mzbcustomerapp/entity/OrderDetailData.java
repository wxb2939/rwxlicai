package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/8.
 */
public class OrderDetailData implements Serializable {

//    data：查询成功{
//        appoid:10, custid:1，name：“王美丽”，stime：“2015-7-9 14:20”，
//        etime：“2015-7-9 16:00”，person:2，memo：“备注”，
//        waiterid：1，waiter：“李慧”，state:1，
//        project：[{ pid:1，pname:“项目1”}，{ pid:1，pname:“项目1”}]
//    }


//    appoid：预约id
//    custid：客户id
//    name：客户名称
//    stime：开始时间
//    etime：结束时间
//    person：预约人数
//    memo：预约备注
//    waiterid：服务员工id
//    waiter：服务员工名称
//    state：预约状态
//    project：预约项目（pid表示项目id，pname表示项目名称）

    private String appoid;
    private String custid;
    private String name;
    public String stime;
    private String etime;
    private int person;
    private String memo;
    private String waiterid;
    private String waiter;
    private String state;
    public List<OrderProjectData> project;

    public String getAppoid() {
        return appoid;
    }

    public void setAppoid(String appoid) {
        this.appoid = appoid;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWaiterid() {
        return waiterid;
    }

    public void setWaiterid(String waiterid) {
        this.waiterid = waiterid;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<OrderProjectData> getProject() {
        return project;
    }

    public void setProject(List<OrderProjectData> project) {
        this.project = project;
    }
}

