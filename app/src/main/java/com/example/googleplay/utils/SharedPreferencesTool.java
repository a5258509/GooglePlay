package com.example.googleplay.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesTool {

    private static SharedPreferences sp;

    public static void saveString(Context context, String key, String value){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defvalue){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defvalue);
    }


    public static void saveBoolean(Context context, String key, boolean value){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defvalue){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defvalue);
    }



}
