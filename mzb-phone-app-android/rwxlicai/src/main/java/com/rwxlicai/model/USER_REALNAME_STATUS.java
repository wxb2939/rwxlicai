package com.rwxlicai.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */
@Table(name = "USER_REALNAME_STATUS")
public class USER_REALNAME_STATUS {

    @Column(name = "Key")
    public String key;
    @Column(name = "Value")
    public String value;

}
