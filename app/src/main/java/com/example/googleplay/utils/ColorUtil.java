package com.example.googleplay.utils;

import android.graphics.Color;

import java.util.Random;

public  class ColorUtil {

    /**
     * 随机生成颜色
     * @return
     */
    public static int randomColor(){
        Random random = new Random();
        int red=random.nextInt(200);
        int green=random.nextInt(200);
        int blue=random.nextInt(200);
        return Color.rgb(red,green,blue);

    }

}
