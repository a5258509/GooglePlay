package com.example.googleplay.http;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对OKhttp的简单封装
 */
public class HttpHelper {

    private OkHttpClient okHttpClient;
    private static HttpHelper mInstance=new HttpHelper();
    private HttpHelper(){
        okHttpClient =new OkHttpClient();
    }

    public static HttpHelper create(){
        return mInstance;
    }

    /**
     * get请求的方法
     * @param url
     */
    public void get(String url,HttpCallback callback){
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String result=response.body().string();

                    //对数据进行缓存及其他统一处理操作
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * 定义回调接口,目的就是将返回的数据带给外界
     */
    public interface HttpCallback{
        void onSuccess(String result);
        void onFail(Exception e);
    }

}
