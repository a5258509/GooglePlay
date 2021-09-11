package com.example.googleplay.http;

import android.os.Environment;

import com.example.googleplay.global.MyApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.OkHttpClient;

/**
 * 缓存数据的管理类
 * 1.将json数据存入存储,以及取出来
 * 2.维护缓存数据的有效期
 */
public class CacheManager {
    //定义缓存目录 /mnt/sdcard/Android/data/包名/cache
    public static final String CACHE_DIR=MyApp.context.getExternalFilesDir(null).getAbsolutePath()+ File.separator
            +MyApp.context.getPackageName()+File.separator+"cache";
    //缓存文件的有效期限
    public static final long CACHE_DURATION=1000*60*60*2;//2小时

    private static CacheManager mInstance=new CacheManager();
    private CacheManager(){
        System.out.println(CACHE_DIR);
        //创建缓存的文件目录
        File dir = new File(CACHE_DIR);
        if(!dir.exists()||!dir.isDirectory()){
            dir.mkdirs();//创建多级目录
        }
    }

    public static CacheManager create(){
        return mInstance;
    }

    /**
     * 保存缓存数据
     */
    public void saveCacheData(String url,String json){
        try {
            //1.创建缓存文件
            File file = new File(CACHE_DIR, url);
            if (!file.exists()) {
                file.createNewFile();
            }
            //2.将json数据写入file中
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据url获取缓存数据
     * @return
     */
    public String getCacheData(String url){
        StringBuilder builder=new StringBuilder();
        try {
            //1.构建要读取的文件
            File file = new File(CACHE_DIR, url);
            if (file.exists()) {
                //判断文件是否有效
                if(isValid(file)){
                    //2.从file中读取出json数据
                    FileReader reader = new FileReader(file);
                    BufferedReader br=new BufferedReader(reader);
                    String line=null;
                    while((line=br.readLine())!=null){
                        //拼接字符串
                        builder.append(line);
                    }
                    reader.close();
                }else {
                    //删除无效的缓存文件
                    file.delete();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 判断缓存文件是否有效
     * @param file
     * @return
     */
    private boolean isValid(File file) {
        long existDuration = System.currentTimeMillis() - file.lastModified();
        return existDuration<=CACHE_DURATION;
    }

}
