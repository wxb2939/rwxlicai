package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/28.
 */
public class PromsData implements Serializable {

//    promid：活动id
//    name：活动名称
//    product：促销产品数量
//    project：促销项目数量
//    group：促销套餐数量
//    card：促销会员卡数量

    private String promid;
    private String name;
    private String product;
    private String project;
    private String group;
    private String card;

    public String getPromid() {
        return promid;
    }

    public void setPromid(String promid) {
        this.promid = promid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
