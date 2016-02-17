package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class ExperEmploye implements Serializable {
    private static final long serialVersionUID = 719444187L;
    private long branid;
    private List<String> roles;
    private long accid;
    private String name;
    private String pname;
    private String position;
    private String sex;
    private List<String> rights;
    private String bname;
    private long empid;
    private String portrait;
    private long ppid;

    public long getBranid() {
        return this.branid;
    }

    public void setBranid(long branid) {
        this.branid = branid;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public long getAccid() {
        return this.accid;
    }

    public void setAccid(long accid) {
        this.accid = accid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getRights() {
        return this.rights;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }

    public String getBname() {
        return this.bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public long getEmpid() {
        return this.empid;
    }

    public void setEmpid(long empid) {
        this.empid = empid;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public long getPpid() {
        return this.ppid;
    }

    public void setPpid(long ppid) {
        this.ppid = ppid;
    }

    public ExperEmploye() {}

    public ExperEmploye(long branid, List<String> roles, long accid, String name, String pname, String position, String sex, List<String> rights, String bname, long empid, String portrait, long ppid){
        super();
        this.branid = branid;
        this.roles = roles;
        this.accid = accid;
        this.name = name;
        this.pname = pname;
        this.position = position;
        this.sex = sex;
        this.rights = rights;
        this.bname = bname;
        this.empid = empid;
        this.portrait = portrait;
        this.ppid = ppid;
    }
}
