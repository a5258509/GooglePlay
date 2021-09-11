package com.example.googleplay.module;

import android.view.View;

import com.example.googleplay.R;
import com.example.googleplay.global.MyApp;

import butterknife.ButterKnife;

//模块类的公共方法
public abstract class BaseModule<T> {
    public View moduleView;

    public BaseModule() {
        moduleView=View.inflate(MyApp.context,getLayoutId(),null);
        ButterKnife.bind(this,moduleView);
    }

    public View getModuleView(){
        return moduleView;
    }


    public abstract int getLayoutId();


    public abstract void bindData(T data);


}
