package com.rwxlicai.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by xuebing on 16/1/19.
 */
@Table(name = "ALL_BANK")
public class ALL_BANK extends Model {
    @Column(name = "Bid")
    public String bid;
    @Column(name = "BankName")
    public String bankName;
    @Column(name = "BankCode")
    public String bankCode;
}
