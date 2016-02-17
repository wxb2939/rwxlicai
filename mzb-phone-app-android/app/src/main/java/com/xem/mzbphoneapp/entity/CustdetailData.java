package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 15/9/3.
 */
public class CustdetailData implements Serializable {

    // 3.13.5	客户计划详情
//    data：查询成功{ custid：客户id，name:“王美丽”，tel:“13015201136”，
//        pic:“http://xxx/xx”，count:5 }


    private Integer custid;
    private String name;
    private String tel;
    private String pic;
    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
