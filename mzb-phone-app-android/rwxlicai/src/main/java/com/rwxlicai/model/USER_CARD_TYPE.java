package com.rwxlicai.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */

@Table(name = "USER_CARD_TYPE")
public class USER_CARD_TYPE extends Model {

    @Column(name = "Key")
    public String key;
    @Column(name = "Value")
    public String value;
}
