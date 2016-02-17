package com.rwxlicai.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */
@Table(name = "INDEX_IMG")
public class INDEX_IMG extends Model {

    @Column(name = "Cid")
    public String cid;
    @Column(name = "Attach_path")
    public String attach_path;
    @Column(name = "External_link_title")
    public String external_link_title;
    @Column(name = "Content_txt")
    public String content_txt;
    @Column(name = "Channel_ids")
    public String channel_ids;

}
