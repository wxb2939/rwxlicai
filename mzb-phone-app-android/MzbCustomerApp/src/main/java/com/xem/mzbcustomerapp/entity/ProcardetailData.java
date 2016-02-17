package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/11/9.
 */
public class ProcardetailData implements Serializable {

//    cardpromid：会员卡促销id
//    name：会员卡促销名称
//    pic：图片
//    money：储值金额
//    pcash：赠送金额
//    tips：温馨小贴士
//    desc：会员卡描述
//    explain：促销说明
//    rule：促销规则
//    pstcash：赠送金额
//    present：促销赠送（type表示赠送物类型（参见通用定义），name表示物品名称，
//    count表示赠送数量）

    private String cardpromid;
    private String name;
    private String pic;
    private String money;
    private String pcash;
    private String tips;
    private String desc;
    private String explain;
    private String rule;
    private String pstcash;
    private List<CarPresent> present;




    public String getCardpromid() {
        return cardpromid;
    }

    public void setCardpromid(String cardpromid) {
        this.cardpromid = cardpromid;
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

    public String getPcash() {
        return pcash;
    }

    public void setPcash(String pcash) {
        this.pcash = pcash;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getPstcash() {
        return pstcash;
    }

    public void setPstcash(String pstcash) {
        this.pstcash = pstcash;
    }

    public List<CarPresent> getPresent() {
        return present;
    }

    public void setPresent(List<CarPresent> present) {
        this.present = present;
    }

    class CarPresent{
        private String type;
        private String name;
        private Integer count;

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

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
