package com.example.googleplay.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 封装json解析
 */
public class GsonUtil {

    /**
     * 把一个json字符串变成对象
     */
    public static <T> T parseJsonToBean(String json,Class<T> cls){
        Gson gson=new Gson();
        T t=null;
        try{
            t=gson.fromJson(json,cls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 把json字符串变成java集合对象
     * @param type new TypeToken<List<Home>>() {}.getType();
     */
    public static List<?> parseJsonToList(String json, Type type){
        Gson gson=new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

}
