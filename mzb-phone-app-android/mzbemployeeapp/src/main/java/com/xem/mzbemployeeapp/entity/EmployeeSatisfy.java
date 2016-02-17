package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/4.
 */
public class EmployeeSatisfy implements Serializable {

    private int id;
    private String empid;
    private String name;
    private String position;
    private String score;
    private String tscore;
    public String pic;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTscore() {
        return tscore;
    }

    public void setTscore(String tscore) {
        this.tscore = tscore;
    }
}
