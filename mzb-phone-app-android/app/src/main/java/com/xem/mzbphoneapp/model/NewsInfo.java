package com.xem.mzbphoneapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 15/7/9.
 */

@Table(name = "NewsInfo")
public class NewsInfo extends Model {
    @Column(name = "Uid")
    public String uid;
    @Column(name = "AcceptTime")
    public String acceptTime;
    @Column(name = "Detail")
    public String detail;
    @Column(name = "Extras")
    public String extras;
    @Column(name = "Type")
    public String type;
    @Column(name = "SendTime")
    public String sendTime;

}
