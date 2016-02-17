package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class ProjectDetailData implements Serializable
{
    /*
    projectid:1，name:“项目”，pic:“http://xx/xx”，price:150，
    mprice:100，tryprice:80，cprice：[{count:10，price:900}，{ count:20，price:1600}]
    desc:“项目描述”，effect：“项目作用”，tips：“温馨小贴士”}
    */

    String projectid;
    String name;
    String pic;
    Double price;
    Double mprice;
    Double tryprice;
    List<CPriceItem> cprice;
    String desc;
    String effect;
    String tips;

    public class CPriceItem
    {
        Double price;
        Integer count;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMprice() {
        return mprice;
    }

    public void setMprice(Double mprice) {
        this.mprice = mprice;
    }

    public Double getTryprice() {
        return tryprice;
    }

    public void setTryprice(Double tryprice) {
        this.tryprice = tryprice;
    }

    public List<CPriceItem> getCprice() {
        return cprice;
    }

    public void setCprice(List<CPriceItem> cprice) {
        this.cprice = cprice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
