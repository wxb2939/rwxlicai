package com.xem.mzbemployeeapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/1.
 */
public class SelectData implements Serializable {

//    data：查询成功[{ custid:1，name:“王美丽”，tel:“13015201136”，
//        pic:“http://xxx/xx” }]

    private Integer custid;
    private String name;
    private String tel;
    private String pic;

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
