package com.axatrikx.axareminder.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amalbose on 3/21/2015.
 */
public class Utils {

    public static final String PREF_FILE = "axareminder_pref";


    public static void saveToPref(Context context, String key, String value) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_FILE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String readPref(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_FILE,context.MODE_PRIVATE);
        return sharedPrefs.getString(key,defaultValue);
    }
}
