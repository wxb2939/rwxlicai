package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/8.
 */
public class OrderProjectData implements Serializable {

    //    project：预约项目（pid表示项目id，pname表示项目名称）

    private String pid;
    public String pname;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}