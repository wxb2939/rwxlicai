package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class ProprjdetailData implements Serializable {

//    data：获取成功{
//        projectid:1，name:“项目”，pic:“http://xx/xx”，
//        price:150，desc:“描述”，effect：“作用”，tips：“温馨小贴士”，
//        rule:1，discount:0.9，salecount:2，prescount:1，explain：“促销说明”，
//        pstcash:100，present：[{ type:1，name:“名称”，count:2 }]
//    }


    private String projectid;
    private String name;
    private String pic;
    private Double price;
    private String desc;
    private String effect;
    private String tips;
    private String rule;
    private String discount;
    private String salecount;
    private String prescount;
    private String explain;
    private String pstcash;
    private List<Propresent> present;

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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSalecount() {
        return salecount;
    }

    public void setSalecount(String salecount) {
        this.salecount = salecount;
    }

    public String getPrescount() {
        return prescount;
    }

    public void setPrescount(String prescount) {
        this.prescount = prescount;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPstcash() {
        return pstcash;
    }

    public void setPstcash(String pstcash) {
        this.pstcash = pstcash;
    }

    public List<Propresent> getPresent() {
        return present;
    }

    public void setPresent(List<Propresent> present) {
        this.present = present;
    }

    class Propresent{
        private String type;
        private String name;
        private String count;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }


}
