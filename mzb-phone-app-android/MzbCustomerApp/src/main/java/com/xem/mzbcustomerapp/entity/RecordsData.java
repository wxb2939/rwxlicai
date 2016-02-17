package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/10/30.
 */
public class RecordsData implements Serializable {

//    data：获取成功[{ diagid:1，logo:“http://xxxx”，name:“门店名称”，time:“2015-7-7”}]
//    diagid表示诊断记录id
//    logo表示品牌Logo
//    name表示门店名称
//    time表示诊断时间

    private String diagid;
    private String logo;
    private String name;
    private String time;

    @Override
    public boolean equals(Object o) {
        if (o instanceof RecordsData){
            return  ((RecordsData)o).diagid.equals(this.diagid);
        }
        return super.equals(o);
    }

    public String getDiagid() {
        return diagid;
    }

    public void setDiagid(String diagid) {
        this.diagid = diagid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
