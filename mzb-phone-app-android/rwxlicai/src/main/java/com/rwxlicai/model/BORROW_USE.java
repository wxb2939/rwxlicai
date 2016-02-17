package com.rwxlicai.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */
@Table(name = "BORROW_USE")
public class BORROW_USE extends Model {
    @Column(name = "Key")
    public String key;
    @Column(name = "Value")
    public String value;
}
