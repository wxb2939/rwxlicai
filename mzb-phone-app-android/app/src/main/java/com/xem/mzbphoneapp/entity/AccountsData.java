package com.xem.mzbphoneapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/8/29.
 */
public class AccountsData implements Serializable {

//    data：获取成功{ pname:“XX美容院”，plogo:“http://xxx/xx”，name:“王美丽”，
//    portrait:””http://xxx.xxx.file/xx-xxx-xx”，isbind：false，
//    deposit:1000，present:500，debt:100，project:5000，product:1000，
//        prjdetail:[{ itemid:1，prjid:1，name:“面部按摩”，count:5，present:false }]，
//        pdtdetail:[{ name:“护肤品”，count:2，present:false }]，
//        coupdetial:[{ name:“优惠券”，edate：“2015-7-23”}]
//    }

    private String pname;
    private String plogo;
    private String name;
    private String portrait;
    private Boolean isbind;
    private Float deposit;
    private Float present;
    private Float debt;
    private Float project;
    private Float product;
    private List<PrjdetailData> prjdetail;
    private List<PdtdetailData> pdtdetail;
    private List<CoupdetialData> coupdetial;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPlogo() {
        return plogo;
    }

    public void setPlogo(String plogo) {
        this.plogo = plogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Boolean getIsbind() {
        return isbind;
    }

    public void setIsbind(Boolean isbind) {
        this.isbind = isbind;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    public Float getPresent() {
        return present;
    }

    public void setPresent(Float present) {
        this.present = present;
    }

    public Float getDebt() {
        return debt;
    }

    public void setDebt(Float debt) {
        this.debt = debt;
    }

    public Float getProject() {
        return project;
    }

    public void setProject(Float project) {
        this.project = project;
    }

    public Float getProduct() {
        return product;
    }

    public void setProduct(Float product) {
        this.product = product;
    }

    public List<PrjdetailData> getPrjdetail() {
        return prjdetail;
    }

    public void setPrjdetail(List<PrjdetailData> prjdetail) {
        this.prjdetail = prjdetail;
    }

    public List<PdtdetailData> getPdtdetail() {
        return pdtdetail;
    }

    public void setPdtdetail(List<PdtdetailData> pdtdetail) {
        this.pdtdetail = pdtdetail;
    }

    public List<CoupdetialData> getCoupdetial() {
        return coupdetial;
    }

    public void setCoupdetial(List<CoupdetialData> coupdetial) {
        this.coupdetial = coupdetial;
    }
}
