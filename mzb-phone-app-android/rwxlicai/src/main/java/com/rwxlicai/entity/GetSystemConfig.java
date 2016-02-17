package com.rwxlicai.entity;

import com.rwxlicai.model.ALL_BANK;
import com.rwxlicai.model.BORROW_REDEEM_STATUS;
import com.rwxlicai.model.BORROW_STATUS;
import com.rwxlicai.model.BORROW_TENDER_STATUS;
import com.rwxlicai.model.BORROW_USE;
import com.rwxlicai.model.INDEX_IMG;
import com.rwxlicai.model.TRADE_CODE;
import com.rwxlicai.model.USER_CARD_TYPE;
import com.rwxlicai.model.USER_EMAIL_STATUS;
import com.rwxlicai.model.USER_LOCK_STATUS;
import com.rwxlicai.model.USER_PHONE_STATUS;
import com.rwxlicai.model.USER_REALNAME_STATUS;
import com.rwxlicai.model.USER_VIP_TYPE;

import java.io.Serializable;

/**
 * Created by xuebing on 16/1/19.
 */
public class GetSystemConfig implements Serializable {

    private ALL_BANK all_bank;
    private BORROW_REDEEM_STATUS borrow_redeem_status;
    private BORROW_TENDER_STATUS borrow_tender_status;
    private BORROW_STATUS borrow_status;
    private BORROW_USE borrow_use;
    private INDEX_IMG index_img;
    private TRADE_CODE trade_code;
    private USER_CARD_TYPE user_card_type;
    private USER_EMAIL_STATUS user_email_status;
    private USER_LOCK_STATUS user_lock_status;
    private USER_PHONE_STATUS user_phone_status;
    private USER_REALNAME_STATUS user_realname_status;
    private USER_VIP_TYPE user_vip_type;


    public ALL_BANK getAll_bank() {
        return all_bank;
    }

    public void setAll_bank(ALL_BANK all_bank) {
        this.all_bank = all_bank;
    }

    public BORROW_REDEEM_STATUS getBorrow_redeem_status() {
        return borrow_redeem_status;
    }

    public void setBorrow_redeem_status(BORROW_REDEEM_STATUS borrow_redeem_status) {
        this.borrow_redeem_status = borrow_redeem_status;
    }

    public BORROW_TENDER_STATUS getBorrow_tender_status() {
        return borrow_tender_status;
    }

    public void setBorrow_tender_status(BORROW_TENDER_STATUS borrow_tender_status) {
        this.borrow_tender_status = borrow_tender_status;
    }

    public BORROW_STATUS getBorrow_status() {
        return borrow_status;
    }

    public void setBorrow_status(BORROW_STATUS borrow_status) {
        this.borrow_status = borrow_status;
    }

    public BORROW_USE getBorrow_use() {
        return borrow_use;
    }

    public void setBorrow_use(BORROW_USE borrow_use) {
        this.borrow_use = borrow_use;
    }

    public INDEX_IMG getIndex_img() {
        return index_img;
    }

    public void setIndex_img(INDEX_IMG index_img) {
        this.index_img = index_img;
    }

    public TRADE_CODE getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(TRADE_CODE trade_code) {
        this.trade_code = trade_code;
    }

    public USER_CARD_TYPE getUser_card_type() {
        return user_card_type;
    }

    public void setUser_card_type(USER_CARD_TYPE user_card_type) {
        this.user_card_type = user_card_type;
    }

    public USER_EMAIL_STATUS getUser_email_status() {
        return user_email_status;
    }

    public void setUser_email_status(USER_EMAIL_STATUS user_email_status) {
        this.user_email_status = user_email_status;
    }

    public USER_LOCK_STATUS getUser_lock_status() {
        return user_lock_status;
    }

    public void setUser_lock_status(USER_LOCK_STATUS user_lock_status) {
        this.user_lock_status = user_lock_status;
    }

    public USER_PHONE_STATUS getUser_phone_status() {
        return user_phone_status;
    }

    public void setUser_phone_status(USER_PHONE_STATUS user_phone_status) {
        this.user_phone_status = user_phone_status;
    }

    public USER_REALNAME_STATUS getUser_realname_status() {
        return user_realname_status;
    }

    public void setUser_realname_status(USER_REALNAME_STATUS user_realname_status) {
        this.user_realname_status = user_realname_status;
    }

    public USER_VIP_TYPE getUser_vip_type() {
        return user_vip_type;
    }

    public void setUser_vip_type(USER_VIP_TYPE user_vip_type) {
        this.user_vip_type = user_vip_type;
    }
}
