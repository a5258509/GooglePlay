package com.example.googleplay.utils;

import com.example.googleplay.R;
import com.example.googleplay.global.MyApp;

public class DimenUtil {

    /**
     * 获取dimens.xml中定义的dp值,并会自动转为像素
     * @param resId
     * @return
     */
    public static int getDimens(int resId){
        return MyApp.context.getResources().getDimensionPixelSize(resId);
    }

}
