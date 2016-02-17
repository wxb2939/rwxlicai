package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/8/29.
 */
public class PInfoData implements Serializable {

//    data：获取成功 { name:””张三”, mobile:””13764633893”,
//        portrait:””http://xxx.xxx.file/xx-xxx-xx”，desc:”个人说明”}；

    private String name;
    private String mobile;
    private String portrait;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
