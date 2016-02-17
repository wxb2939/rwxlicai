package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/28.
 */
public class EmployeData implements Serializable {

    //    employe：{ accid:1, empid:1, roles:[“101”,”102”,”103”]，rights:[“PH001”,”PH002”,”PH003”] }

    public int accid;
    public Integer empid;
    public String[] roles;
    public String[] rights;
    public String name;
    public String bname;
    public String portrait;
    public String positon;
    public String ppid;
    public String sex;
    public String firstbranid;
//    public String
    public void setAccid(int accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getAccid() {
        return accid;
    }

    public void setAccid(Integer accid) {
        this.accid = accid;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getRights() {
        return rights;
    }

    public void setRights(String[] rights) {
        this.rights = rights;
    }
}
