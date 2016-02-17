package com.rwxlicai.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xuebing on 16/1/13.
 */
public class Config {


    public static final String APP_ID = "com.rwxlicai";
    public static final String KEY_TOKEN = "Token";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "mobile";
    public static final String KEY_JSS= "ssion";
    public static final String KEY_PORTRAIT = "Portrait";
    public static final String KEY_AUTH = "auth";
    public static final String KEY_GOPEN = "gopen";
    public static final String KEY_GPWD= "gpwd";
    public static final String KEY_LOGIN = "loginin";
    public static final String KEY_MONEY = "availableMoney";
    public static final String KEY_NAMESTATE = "realnamestate";
    public static final String KEY_BANKSTATE= "bankstate";



    public static String getAvailableMoney(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_MONEY, null);
    }

    public static void cachedAvailableMoney(Context context, String availableMoney) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_MONEY, availableMoney);
        editor.commit();
    }

    public static String getRealNameState(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_NAMESTATE, null);
    }

    public static void cachedRealNameState(Context context, String availableMoney) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAMESTATE, availableMoney);
        editor.commit();
    }

    public static String getBankState(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_BANKSTATE, null);
    }

    public static void cachedBankState(Context context, String availableMoney) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_BANKSTATE, availableMoney);
        editor.commit();
    }






    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cachedToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }


    public static Boolean getLogin(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_APPEND).getBoolean(KEY_LOGIN, false);
    }
    public static void cachedLogin(Context context, Boolean login) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_LOGIN, login);
        editor.commit();
    }


    public static Boolean getAuth(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_APPEND).getBoolean(KEY_AUTH, true);
    }
    public static void cachedAuth(Context context, Boolean auth) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_AUTH, auth);
        editor.commit();
    }

    public static Boolean getGopen(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_APPEND).getBoolean(KEY_GOPEN,false);
    }
    public static void cachedGopen(Context context, Boolean auth) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_GOPEN, auth);
        editor.commit();
    }

    public static String getGpwd(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_GPWD, null);
    }

    public static void cachedGpwd(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_GPWD, token);
        editor.commit();
    }


    //  JSESSIONID 缓存
    public static String getJSESSIONID(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_JSS, null);
    }

    public static void cachedJSESSIONID(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_JSS, token);
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

    public static String getCachedPortrait(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PORTRAIT, null);
    }

    public static void cachedPortrait(Context context, String portrait) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PORTRAIT, portrait);
        editor.commit();
    }

}
