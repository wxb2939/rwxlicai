package com.xem.mzbcustomerapp.entity;

import com.xem.mzbcustomerapp.enums.GoodsType;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/28.
 */
public class GoodsSubCateData implements Serializable{

    private String id;
    private String name;
    private String count;
    private GoodsType type;

    public GoodsSubCateData(GoodsType type, String id, String name, String count) {
        this.count = count;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getCount() {
        return count;
    }

}
