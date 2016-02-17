package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class ProgroupdetailData implements Serializable {

//    data：获取成功{
//        groupid:1，name:“套餐”，pic:“http://xx/xx”，price:150，
//        desc:“描述”，effect：“作用”，tips：“温馨小贴士”，pstcash:100，
//        rule:1，discount:0.9，salecount:2，prescount:1，explain：“促销说明”，
//        product：[{ name:“产品名称”，count:2 }]，
//        project：[{ name:“项目名称”，count:2 }]
//        present：[{ type:1，name:“名称”，count:2 }]
//    }

    private String groupid;
    private String name;
    private String pic;
    private String price;
    private String desc;
    private String effect;
    private String tips;
    private String pstcash;
    private String rule;
    private String discount;
    private String salecount;
    private String prescount;
    private String explain;
    private List<setPresent> present;
    private List<setProduct> project;
    private List<setProject> product;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getPstcash() {
        return pstcash;
    }

    public void setPstcash(String pstcash) {
        this.pstcash = pstcash;
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

    public List<setPresent> getPresent() {
        return present;
    }

    public void setPresent(List<setPresent> present) {
        this.present = present;
    }

    public List<setProduct> getProject() {
        return project;
    }

    public void setProject(List<setProduct> project) {
        this.project = project;
    }

    public List<setProject> getProduct() {
        return product;
    }

    public void setProduct(List<setProject> product) {
        this.product = product;
    }

    class setPresent{
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


    class setProject{
        private String name;
        private String count;

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

    class setProduct{
        private String name;
        private String count;

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
