package com.xem.mzbcustomerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xuebing on 15/10/21.
 */
public class Config {

    public static final String APP_ID = "com.xem.mzbphoneapp";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PPID = "ppid";
    public static final String KEY_BRANID = "branid";
    public static final String KEY_CUSTID = "custid";
    public static final String KEY_PHONE = "mobile";

    public static final String KEY_TOKEN = "Token";
    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "name";
    public static final String KEY_SCORE = "score";
    public static final String KEY_PORTRAIT = "Portrait";
    public static final String KEY_ISCUSTOMER = "iscustomer";
    public static final String KEY_DESC = "desc";
    public static final String KEY_SEX = "sex";
    public static final String KEY_CATEID = "cateid";
    public static final String KEY_CNAME = "Cname";

    public static final String KEY_STORE = "store_name";

    //缓存门店的方法
    public static String getCachedStoreName(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_STORE, null);
    }
    public static void cachedStoreName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_STORE, name);
        editor.commit();
    }

    public static String getCachedCateid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_CATEID, null);
    }
    public static void cachedCateid(Context context, String cateid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CATEID, cateid);
        editor.commit();
    }



    public static String getCachedCname(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_CNAME, null);
    }
    public static void cachedCname(Context context, String Cname) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CNAME, Cname);
        editor.commit();
    }




    public static String getCachedSex(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_SEX, null);
    }
    public static void cachedSex(Context context, String sex) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_SEX, sex);
        editor.commit();
    }




    public static String getCachedDesc(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_DESC, null);
    }

    public static void cachedDesc(Context context, String desc) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_DESC, desc);
        editor.commit();
    }

    public static String getCachedPpid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PPID, null);
    }

    public static void cachedPpid(Context context, String ppid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PPID, ppid);
        editor.commit();
    }

    public static String getCachedBrandid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_BRANID, null);
    }

    public static void cachedBrandid(Context context, String branid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_BRANID, branid);
        editor.commit();
    }


    public static String getCachedCustid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_CUSTID, null);
    }

    public static void cachedCustid(Context context, String custid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CUSTID, custid);
        editor.commit();
    }


    //  iscustomer 缓存
    public static Boolean getCachedIscustomer(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getBoolean(KEY_ISCUSTOMER, false);
    }

    public static void cachedIscustomer(Context context, Boolean iscustomer) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_ISCUSTOMER, iscustomer);
        editor.commit();
    }



    //   phone 缓存
    public static String getCachedPhone(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE, null);
    }

    public static void cachedPhone(Context context, String phone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }


    //  密码缓存
    public static String getCachedPassword(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PASSWORD, null);
    }

    public static void cachedPassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }


    //  token 缓存
    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cachedToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    //  uid 缓存
    public static String getCachedUid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_UID, null);
    }

    public static void cachedUid(Context context, String uid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_UID, uid);
        editor.commit();
    }


    //  name缓存
    public static String getCachedName(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_NAME, null);
    }

    public static void cachedName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    //  积分 缓存
    public static Float getCachedScore(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getFloat(KEY_SCORE, 0);
    }

    public static void cachedScore(Context context, Float score) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putFloat(KEY_SCORE, score);
        editor.commit();
    }

    public static String getCachedPortrait(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PORTRAIT, null);
    }

    public static void cachedPortrait(Context context, String portrait) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PORTRAIT, portrait);
        editor.commit();
    }

}
