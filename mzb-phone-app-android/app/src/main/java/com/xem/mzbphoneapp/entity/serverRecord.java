package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/3.
 */
public class serverRecord implements Serializable {

    private int id;
    private String logo;
    private String serverItem;
    private String servergu;
    private String serverml;
    private String serverTiem;

    public serverRecord() {
    }

    public serverRecord(int id, String logo, String serverItem, String servergu, String serverml, String serverTiem) {
        this.id = id;
        this.logo = logo;
        this.serverItem = serverItem;
        this.servergu = servergu;
        this.serverml = serverml;
        this.serverTiem = serverTiem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getServerItem() {
        return serverItem;
    }

    public void setServerItem(String serverItem) {
        this.serverItem = serverItem;
    }

    public String getServergu() {
        return servergu;
    }

    public void setServergu(String servergu) {
        this.servergu = servergu;
    }

    public String getServerml() {
        return serverml;
    }

    public void setServerml(String serverml) {
        this.serverml = serverml;
    }

    public String getServerTiem() {
        return serverTiem;
    }

    public void setServerTiem(String serverTiem) {
        this.serverTiem = serverTiem;
    }
}
