package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuebing on 15/11/27.
 */
public class GroupItem implements Serializable {

    private String ppid;
    private String cateid;
    private String name;
    private String count;
    private int selNum;
    private List<PprojectData> children = new ArrayList<>();


    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<PprojectData> getChildren() {
        return children;
    }

    public int getSelNum() {
        return selNum;
    }

    public void setSelNum(int selNum) {
        this.selNum = selNum;
    }

    public void setChildren(List<PprojectData> children) {
        this.children = children;
    }
}
