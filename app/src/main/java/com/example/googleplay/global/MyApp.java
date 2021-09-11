package com.example.googleplay.global;

import android.app.Application;
import android.content.Context;

import cn.leancloud.AVOSCloud;

public class MyApp extends Application {

    //全局的上下文 ,上下文就是封装了公共模块api的对象,比如可以获取包名,可以获取Resource对象,SP对象,Window对象
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        AVOSCloud.initialize(this, "bHhSniz9OJEjzwl2L2doVvVR-MdYXbMMI", "DDx7iLCq6sLPCVnALBYP1933", null);

    }
}
