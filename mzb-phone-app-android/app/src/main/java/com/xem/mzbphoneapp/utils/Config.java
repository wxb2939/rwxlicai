package com.xem.mzbphoneapp.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xuebing on 15/5/27.
 */
public class Config {


    public static final String APP_ID = "com.xem.mzbphoneapp";


    public static final String KEY_TOKEN = "Token";
    public static final String KEY_UID = "uid";
    public static final String KEY_PHONE = "mobile";
    public static final String KEY_NAME = "name";
    public static final String KEY_ROLES = "roles";
    public static final String KEY_PORTRAIT = "Portrait";
    public static final String KEY_ISEMPLOYE = "isemploye";
    public static final String KEY_ISMEMBER = "ismember";
    public static final String KEY_SCORE = "score";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ISEXP = "isexp";


    //  token 缓存
    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cachedToken(Context context, String token) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }
    //测试账号的设置
    public static String getCachedIsexp(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_ISEXP,null);
    }
    public static void cachedIsexp(Context context, String isexp) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ISEXP, isexp);
        editor.commit();
    }






    //  积分 缓存
    public static Float getCachedScore(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getFloat(KEY_SCORE, 0);
    }

    public static void cachedScore(Context context, Float score) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putFloat(KEY_SCORE, score);
        editor.commit();
    }


    //  密码缓存
    public static String getCachedPassword(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PASSWORD, null);
    }

    public static void cachedPassword(Context context, String password) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }


    //  isemploye 缓存
    public static Boolean getCachedIsemploye(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        Boolean isEmploye = sharedPreferences.getBoolean(KEY_ISEMPLOYE, false);
        return isEmploye;
    }

    public static void cachedIsemploye(Context context, Boolean isemploye) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_ISEMPLOYE, isemploye);
        editor.commit();
    }

    //  ismember 缓存
    public static Boolean getCachedIsmember(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getBoolean(KEY_ISMEMBER, false);
    }

    public static void cachedIsmember(Context context, Boolean ismember) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_ISMEMBER, ismember);
        editor.commit();
    }




    //  uid 缓存
    public static String getCachedUid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_UID, null);
    }

    public static void cachedUid(Context context, String uid) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_UID, uid);
        editor.commit();
    }


    //   phone 缓存
    public static String getCachedPhone(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE, null);
    }

    public static void cachedPhone(Context context, String phone) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }


    //  name缓存
    public static String getCachedName(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_NAME, null);
    }

    public static void cachedName(Context context, String name) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAME, name);
        editor.commit();
    }


    //  roles 缓存
    public static String getCachedRoles(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_ROLES, null);
    }

    public static void cachedRoles(Context context, String roles) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ROLES, roles);
        editor.commit();
    }



//  portrait 头像缓存

    public static String getCachedPortrait(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PORTRAIT, null);
    }

    public static void cachedPortrait(Context context, String portrait) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PORTRAIT, portrait);
        editor.commit();
    }


    public static final String KEY_BRAND_ACCID = "accid";
    public static final String KEY_BRAND_EMPID = "empid";
    public static final String KEY_BRAND_RIGHTS = "rights";
    public static final String KEY_BRAND_CODES = "codes";



//  brand 员工帐号accid

    public static Integer getCachedBrandAccid(Context context) {

        SharedPreferences sp = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        return SharedPreferencesUtil.getInteger(sp, KEY_BRAND_ACCID);
    }

    public static void cachedBrandAccid(Context context, Integer accid) {

        SharedPreferences sp = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferencesUtil.putInteger(sp, KEY_BRAND_ACCID, accid);
    }

//  brand 员工帐号empid

    public static Integer getCachedBrandEmpid(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return SharedPreferencesUtil.getInteger(sp, KEY_BRAND_EMPID);
    }

    public static void cachedBrandEmpid(Context context, Integer empid) {
        SharedPreferences sp = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        SharedPreferencesUtil.putInteger(sp, KEY_BRAND_EMPID, empid);

//        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
//        editor.putString(KEY_BRAND_EMPID, empid);
//        editor.commit();
    }

//  员工帐号权限

    public static Set<String> getCachedBrandRights(Context context) {
        Set<String> rights = new HashSet<String>();
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getStringSet(KEY_BRAND_RIGHTS,rights);//getString(KEY_BRAND_RIGHTS, null);
    }



    public static void cachedBrandRights(Context context, String[] rights) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        if (rights != null){
//            rights = rights.substring(1,rights.length()-1);
//            String[] StrArray = rights.split(",");
            Set set = new HashSet();
            for (int i = 0; i<rights.length;i++){
                String str = rights[i];
                /*if (str.length() != 0){
                    set.add(str.substring(1, str.length() - 1));
                }*/
                set.add(str);
            }
            editor.putStringSet(KEY_BRAND_RIGHTS,set);
            editor.commit();
        }
    }


    //员工角色codes
    public static Set<String> getCachedBrandCodes(Context context) {
        Set<String> codes = new HashSet<String>();
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getStringSet(KEY_BRAND_CODES,codes);//getString(KEY_BRAND_RIGHTS, null);
    }


    public static void cachedBrandCodes(Context context, String[] codes) {
        Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();

        if (codes != null){
            Set set = new HashSet();
            for (int i = 0; i<codes.length;i++){
                String str = codes[i];

                set.add(str);

                /*if (str.length() != 0) {
                    set.add(str.substring(1, str.length() - 1));
                }*/
            }
            editor.putStringSet(KEY_BRAND_CODES,set);
            editor.commit();
        }
    }

}
