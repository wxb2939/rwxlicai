package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/10/31.
 */
public class BrandDetailData implements Serializable{

//    ppid：品牌id
//    logo：品牌Logo
//    name：品牌名称
//    intro：品牌介绍
//    scope：经营范围
//    branch：品牌门店列表（branid表示门店id，name表示门店名称）

    private String ppid;
    private String logo;
    private String name;
    private String intro;
    private String scope;
    private List<BranchStore> branch;

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<BranchStore> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchStore> branch) {
        this.branch = branch;
    }
}
