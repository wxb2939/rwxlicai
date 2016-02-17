package com.xem.mzbphoneapp.entity;

/**
 * Created by xuebing on 15/7/2.
 */
public class OrderRecord {

    private int appoid;
    private String logo;
    private String name;
    private String time;
    private String waiter;// technician;
    private int state;
    private String statename;


    public int getAppoid() {
        return appoid;
    }

    public void setAppoid(int appoid) {
        this.appoid = appoid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
