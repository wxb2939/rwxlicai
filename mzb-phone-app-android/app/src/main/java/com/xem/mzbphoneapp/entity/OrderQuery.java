package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * 预约查询
 *
 * Created by xuebing on 15/8/3.
 */
public class OrderQuery implements Serializable {

    private int id;
    private String appoid;
    private String name;
    private String clitId;
    private String level;
    private String portrait;
    private String time;
    private String waiter;
    private String state;

    public OrderQuery() {
    }

    public OrderQuery(int id, String appoid, String name, String clitId, String level, String portrait, String time, String waiter, String state) {
        this.id = id;
        this.appoid = appoid;
        this.name = name;
        this.clitId = clitId;
        this.level = level;
        this.portrait = portrait;
        this.time = time;
        this.waiter = waiter;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppoid() {
        return appoid;
    }

    public void setAppoid(String appoid) {
        this.appoid = appoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClitId() {
        return clitId;
    }

    public void setClitId(String clitId) {
        this.clitId = clitId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
