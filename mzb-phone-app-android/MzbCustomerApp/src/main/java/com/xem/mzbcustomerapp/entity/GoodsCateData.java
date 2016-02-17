package com.xem.mzbcustomerapp.entity;

import com.xem.mzbcustomerapp.enums.GoodsType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuebing on 15/10/28.
 */
public class GoodsCateData implements Serializable {

    private String id;
    private String name;
    private Boolean isPromotion;
    private GoodsType type;
    private List<GoodsSubCateData> children = new ArrayList<>();

    public GoodsCateData() {
    }

    public GoodsCateData(GoodsType type, Boolean isPromotion, String id, String name) {
        this.type = type;
        this.isPromotion = isPromotion;
        this.id = id;
        this.name = name;
    }

    public Boolean getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsSubCateData> getChildren() {
        return children;
    }

}
