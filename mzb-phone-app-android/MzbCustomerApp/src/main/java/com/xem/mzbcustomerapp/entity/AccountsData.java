package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/10/29.
 */
public class AccountsData implements Serializable {

//    pname：品牌名称
//    plogo：品牌Logo
//    name：会员名称
//    portrait：会员头像
//    isbind：是否绑定平台帐号
//    deposit：储值账户余额
//    present：赠金账户余额
//    debt：欠款帐户
//    project：疗程帐户余额
//    product：产品账户余额
//    pstdetail：赠金账户明细（amount表示金额，edate表示截至日期）
//    prjdetail：疗程账户明细（itemid表示账户明细标识，prjid表示项目标识，name表示项目名称，count表示剩余次数，present表示是否赠送，edate表示截至日期）
//    pdtdetail：产品余额（name表示产品名称，count表示剩余数量，present表示是否赠送，edate表示截至日期）
//    coupdetial：优惠券余额（name表示优惠券名称，edate表示截至日期）


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
    private List<PstdetailData> pstdetail;
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

    public List<PstdetailData> getPstdetail() {
        return pstdetail;
    }

    public void setPstdetail(List<PstdetailData> pstdetail) {
        this.pstdetail = pstdetail;
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
