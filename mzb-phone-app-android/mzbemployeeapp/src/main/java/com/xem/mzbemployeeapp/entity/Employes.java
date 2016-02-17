package com.xem.mzbemployeeapp.entity;

/**
 * Created by Administrator on 2015/11/19.
 */
public class Employes {
    public String uid;
    public String token;
    public Boolean isemploye;
    public EmployeData employe;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsemploye() {
        return isemploye;
    }

    public void setIsemploye(Boolean isemploye) {
        this.isemploye = isemploye;
    }

    public EmployeData getEmploye() {
        return employe;
    }

    public void setEmploye(EmployeData employe) {
        this.employe = employe;
    }
}
