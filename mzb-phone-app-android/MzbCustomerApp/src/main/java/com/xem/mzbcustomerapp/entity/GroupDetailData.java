package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class GroupDetailData implements Serializable {

//    groupid：套餐id
//    name：套餐名称
//    pic：套餐图片
//    price：原价
//    gprice：套餐价
//    desc：套餐描述
//    effect：套餐作用
//    tips：温馨小贴士
//    product：套餐包含产品（name表示产品名称，count表示数量）
//    project：套餐包含项目（name表示项目名称，count表示数量）




    private String groupid;
    private String name;
    private String pic;
    private Double price;
    private Double gprice;
    private String desc;
    private String effect;
    private String tips;
    private List<includeProduct> product;
    private List<includeProject> project;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getGprice() {
        return gprice;
    }

    public void setGprice(Double gprice) {
        this.gprice = gprice;
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

    public List<includeProduct> getProduct() {
        return product;
    }

    public void setProduct(List<includeProduct> product) {
        this.product = product;
    }

    public List<includeProject> getProject() {
        return project;
    }

    public void setProject(List<includeProject> project) {
        this.project = project;
    }

    class includeProject{
        private String name;
        private Integer count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }


    class includeProduct{
        private String name;
        private Integer count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}