package com.example.googleplay.http;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 对AVQuery的简单封装
 */
public class AVQueryHelper {



    private static AVQueryHelper mInstance=new AVQueryHelper();
    private AVQueryHelper(){
    }

    public static AVQueryHelper create(){
        return mInstance;
    }

    /**
     * get请求的方法
     * @param
     */
    public void get(AVQuery<AVObject> query,int num,HttpCallback callback){
        //优先读取缓存数据
        String cacheData = CacheManager.create().getCacheData(query.getClassName()+num);
        if(TextUtils.isEmpty(cacheData)){
            //没有缓存数据,从网络读取
            requestDataFromNet(query,num, callback);
        }else{
            // 传递缓存数据
            System.out.println("有缓存数据");
            callback.onSuccess(cacheData);
        }


    }

    /**
     * 从网络请求数据
     * @param query
     * @param callback
     */
    private void requestDataFromNet(AVQuery<AVObject> query, int num,HttpCallback callback) {
        query.findInBackground().safeSubscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull List<AVObject> appinfos) {
                //对数据进行缓存及其他统一处理操作
                String result=appinfos.toString();
                CacheManager.create().saveCacheData(query.getClassName()+ num,result);
                callback.onSuccess(result);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                callback.onFail(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 定义回调接口,目的就是将返回的数据带给外界
     */
    public interface HttpCallback{
        void onSuccess(String result);
        void onFail(Throwable e);
    }

}
