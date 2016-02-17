package com.xem.mzbcustomerapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuebing on 15/10/30.
 */
public class BeautyFiledetailData implements Serializable {

//    data：获取成功{ diagid:1，logo:“http://xxxx”，name:“门店名称”，time:“2015-7-7”，
//    contents:[{ id:1，key:“眼部”，value:“问题”}]，
//        pictures:[{ id:1，key:“T区”，value:“http://xx/xx.jpeg”}]


    private String diagid;
    private String logo;
    private String name;
    private String time;
    private List<BeautiFileContentData> contents;
    private List<BeautiFilePictureData> pictures;

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

    public List<BeautiFileContentData> getContents() {
        return contents;
    }

    public void setContents(List<BeautiFileContentData> contents) {
        this.contents = contents;
    }

    public List<BeautiFilePictureData> getPictures() {
        return pictures;
    }

    public void setPictures(List<BeautiFilePictureData> pictures) {
        this.pictures = pictures;
    }
}
