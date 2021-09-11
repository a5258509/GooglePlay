package com.example.googleplay.utils;

import android.util.Log;

/**
 * Log管理类
 */
public class LogUtil {
    private static final String TAG="main1";

    //是否是开发调试环境,当项目上线的时候,将该变量置为false即可
    private static boolean isDebug=true;

    /**
     * 打印d级别log
     * @param tag
     * @param msg
     */
    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }

    /**
     * 打印e级别log
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }

    /**
     * 打印d级别log
     * @param msg
     */
    public static void d(String msg){
        if(isDebug){
            Log.d(TAG,msg);
        }
    }

    /**
     * 打印e级别log
     * @param msg
     */
    public static void e(String msg){
        if(isDebug){
            Log.d(TAG,msg);
        }
    }

}
