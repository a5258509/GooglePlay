package com.example.googleplay.utils;

import android.widget.Toast;

import com.example.googleplay.global.MyApp;

public class ToastUtil {

    private static Toast toast;

    /**
     * 定义一个强大的单例的吐司
     */
    public static void showToast(String text){
        if(toast==null){
            //创建吐司
            toast=Toast.makeText(MyApp.context,text,Toast.LENGTH_SHORT);
        }else {
            toast = Toast.makeText(MyApp.context, text, Toast.LENGTH_LONG);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
        }


}
