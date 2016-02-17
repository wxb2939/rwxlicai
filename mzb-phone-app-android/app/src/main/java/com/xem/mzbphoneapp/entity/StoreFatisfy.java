package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/3.
 */
public class StoreFatisfy implements Serializable {

    private int id;
    private String branid;
    private String name;
    private String logo;
    private String beautician;
    private String consultant;
    private String service;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranid() {
        return branid;
    }

    public void setBranid(String branid) {
        this.branid = branid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBeautician() {
        return beautician;
    }

    public void setBeautician(String beautician) {
        this.beautician = beautician;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
