package com.example.googleplay.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;

public class ScreenUtils {

    private static WindowManager wm;
    private static DisplayMetrics outMetrics;

    /**
     * 获取屏幕宽度,单位px
     * @param
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null){
            return 0;
        }
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    /**
     * 获取屏幕高度,单位px
     * @param
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (context == null){
            return 0;
        }
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getRealScreenWidth(WindowManager windowManager){
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        return widthPixel;
    }

    public static int getRealScreenHeight(WindowManager windowManager){
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        int heightPixel = outMetrics.heightPixels;
        return heightPixel;
    }


    /**
     * 将dp转为对应手机屏幕的像素值
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;// density:屏幕密度
        return (int) (dpValue * scale + 0.5f);
    }
 
}