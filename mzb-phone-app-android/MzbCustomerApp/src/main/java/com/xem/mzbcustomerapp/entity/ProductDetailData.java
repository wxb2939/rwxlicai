package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class ProductDetailData implements Serializable {

//    productid：产品id
//    name：产品名称
//    pic：产品图片
//    price：产品价格
//    desc：产品描述
//    effect：产品作用
//    tips：温馨小贴士

    private String productid;
    private String name;
    private String pic;
    private Double price;
    private String desc;
    private String effect;
    private String tips;


    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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
}
