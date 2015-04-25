package com.axatrikx.axareminder.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;

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

    public static int getSpinnerIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
