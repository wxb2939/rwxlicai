package com.xem.mzbphoneapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/8/28.
 */
public class LoginData implements Serializable {

//    data: 登录成功 {uid:1234, token:”xxxxx”, isemploye:false, ismember:false, name:””张三”, score:90, portrait:””http://xxx.xxx.file/xx-xxx-xx，curbranid:10,
//    employe：{ accid:1, empid:1, roles:[“101”,”102”,”103”]，rights:[“PH001”,”PH002”,”PH003”] }
//    branch: { ppid:1, branid:2, custid:3, logo:“http://xxx/xx”, name:“丽丽美容浦东店”, address:“人民南路118号”，tel:“15800558122”}
// }；

    private String uid;
    private String token;
    private Boolean isemploye;
    private Boolean ismember;
    private String name;
    private Float score;
    private String portrait;
    private Integer curbranid;
    private BranchData branch;
    private EmployeData employe;

    public BranchData getBranch() {
        return branch;
    }

    public void setBranch(BranchData branch) {
        this.branch = branch;
    }

    public EmployeData getEmploye() {
        return employe;
    }

    public void setEmploye(EmployeData employe) {
        this.employe = employe;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsemploye() {
        return isemploye;
    }

    public void setIsemploye(Boolean isemploye) {
        this.isemploye = isemploye;
    }

    public Boolean getIsmember() {
        return ismember;
    }

    public void setIsmember(Boolean ismember) {
        this.ismember = ismember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getCurbranid() {
        return curbranid;
    }

    public void setCurbranid(Integer curbranid) {
        this.curbranid = curbranid;
    }

}
