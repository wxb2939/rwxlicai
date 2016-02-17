package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class CardDateilData implements Serializable {


//    cardid：会员卡id
//    name：会员卡名称
//    pic：会员卡图片
//    money：储值金额
//    mpresent：赠送金额
//    tips：温馨小贴士
//    desc：会员卡描述
//    projects：赠送项目（projectid表示项目id，name表示项目名称，count表示数量）
//    products：赠送产品（productid表示产品id，name表示产品名称，count表示数量）


    private String cardid;
    private String name;
    private String pic;
    private String money;
    private String mpresent;
    private String tips;
    private String desc;
    private List<Preprodents> products;
    private List<PreProjects> projects;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMpresent() {
        return mpresent;
    }

    public void setMpresent(String mpresent) {
        this.mpresent = mpresent;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Preprodents> getProducts() {
        return products;
    }

    public void setProducts(List<Preprodents> products) {
        this.products = products;
    }

    public List<PreProjects> getProjects() {
        return projects;
    }

    public void setProjects(List<PreProjects> projects) {
        this.projects = projects;
    }

    class Preprodents{
        private String projectid;
        private String name;
        private Integer count;

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

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    class PreProjects{
        private String productid;
        private String name;
        private Integer count;

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

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

}
