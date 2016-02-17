package com.rwxlicai.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */
@Table(name = "TRADE_CODE")
public class TRADE_CODE extends Model {

    @Column(name = "Key")
    public String key;
    @Column(name = "Value")
    public String value;
}
