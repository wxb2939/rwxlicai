package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/28.
 */
public class EmployeData implements Serializable {

    //    employe：{ accid:1, empid:1, roles:[“101”,”102”,”103”]，rights:[“PH001”,”PH002”,”PH003”] }

    private int accid;
    private Integer empid;
    private String[] roles;
    private String[] rights;

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
