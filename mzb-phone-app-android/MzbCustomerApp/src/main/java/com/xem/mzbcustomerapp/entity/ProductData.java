package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/11/5.
 */
public class ProductData implements Serializable {

//    data：获取成功[{productid:1，name:“项目”，pic:“http://xx/xx”，price:150 }]
//    productid：产品id
//        name：产品名称
//        pic：产品图片
//        price：产品价格

    private String productid;
    private String name;
    private String pic;
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
