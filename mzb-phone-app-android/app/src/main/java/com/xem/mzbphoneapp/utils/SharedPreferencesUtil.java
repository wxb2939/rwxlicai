package com.xem.mzbphoneapp.utils;

import android.content.SharedPreferences;

/**
 * Created by xuebing on 15/8/28.
 */
public class SharedPreferencesUtil {

    public static SharedPreferences.Editor putInteger(SharedPreferences shared, String key, Integer value)
    {
        SharedPreferences.Editor editor = shared.edit();

        if (value != null){
            editor.putInt(key, value);
        }
        editor.commit();
        return editor;
    }

    public static Integer getInteger(SharedPreferences shared, String key)
    {
        if (shared == null)
            return null;

        if (shared.contains(key))
        {
            return shared.getInt(key, 0);
        }
        else
        {
            return null;
        }
    }

}
