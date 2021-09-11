package com.example.googleplay.http.download;


import android.util.SparseArray;

import com.example.googleplay.bean.Home;
import com.example.googleplay.global.MyApp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 管理整个项目的下载逻辑
 */
public class DownloadManager {
    //定义下载目录 /mnt/sdcard/Android/data/download
    public static final String DOWNLOAD_DIR= MyApp.context.getExternalFilesDir(null).getAbsolutePath()+ File.separator
            +MyApp.context.getPackageName()+File.separator+"download";

    //定义下载状态常量
    public static final int STATE_NONE = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE = 2;//暂停下载
    public static final int STATE_FINISH = 3;//下载成功
    public static final int STATE_ERROR = 4;//下载失败
    public static final int STATE_WAITING = 5;//等待下载

    //用来存放所有的监听器对象
    private ArrayList<DownloadObserver> observers=new ArrayList<>();
    //用来存放所有的DownloadINfo对象
    private SparseArray<DownloadInfo> downloadInfoMap=new SparseArray<>();

    //线程池
    ExecutorService executor= null;

    private static DownloadManager mInstance=new DownloadManager();
    private DownloadManager(){
        executor=Executors.newFixedThreadPool(2);
        //创建缓存的文件目录
        File dir = new File(DOWNLOAD_DIR);
        if(!dir.exists()||!dir.isDirectory()){
            dir.mkdirs();//创建多级目录
        }
    }
    public static DownloadManager create(){
        return mInstance;
    }

    /**下载的方法
     *
     */
    public void  download(Home home){
        //1.获取任务对应的DownloadInfo
        DownloadInfo downloadInfo = downloadInfoMap.get((int) home.serverData.index);
        if(downloadInfo==null){
            //创建downloadInfo,并存入到downloadInfoMap
            downloadInfo=DownloadInfo.create(home);
            downloadInfoMap.put((int) downloadInfo.index,downloadInfo);
        }

        //2.获取任务对应的state
        int state = downloadInfo.state;
        //在这几种状态下才能进行下载:STATE_NONE,STATE_PAUSE,STATE_ERROR
        if(state==STATE_NONE || state==STATE_PAUSE || state==STATE_ERROR){
            //可以下载,创建下载任务,交给线程池
            DownloadTask downloadTask = new DownloadTask(downloadInfo);

            //更新任务的state
            downloadInfo.state=STATE_WAITING;
            //通知监听器更新
            notifyDownloadObserver(downloadInfo);

            executor.execute(downloadTask);
        }

    }



    /**
     * 下载任务类,请求文件数据,保存到本地
     */
    class DownloadTask implements Runnable{
        DownloadInfo downloadInfo;
        private DownloadTask(DownloadInfo downloadInfo){
            this.downloadInfo=downloadInfo;
        }

        @Override
        public void run() {
            //3.将state更改为下载中
            downloadInfo.state=STATE_DOWNLOADING;
            //通知监听器更新
            notifyDownloadObserver(downloadInfo);

            //4.开始下载文件 a.从头下载 b.断点下载
            File file = new File(downloadInfo.path);
            if(!file.exists() || file.length()!=downloadInfo.currentLength){
                file.delete();//删除无效文件
                downloadInfo.currentLength=0;
//                Request request = new Request.Builder().url(downloadInfo.downloadUrl).get().build();
//                new OkHttpClient().newCall(request)
            }

        }
    }


    /**
     * 通知所有的监听器下载更新
     * @param downloadInfo
     */
    private void notifyDownloadObserver(DownloadInfo downloadInfo) {
        for(DownloadObserver observer:observers){
            observer.onDownloadUpdate(downloadInfo);
        }
    }


    /**
     * 暂停的方法
     */
    public void pause(){

    }

    /**
     * 添加一个下载监听器对象
     * @param observer
     */
    public void registerDownloadObserver(DownloadObserver observer){
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    /**
     * 移除一个下载监听器对象
     * @param observer
     */
    public void unregisterDownloadObserver(DownloadObserver observer){
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }


    /**
     * 下载监听器
     */
    public interface DownloadObserver{

        /**
         * 下载数据更新的方法
         */
        void onDownloadUpdate(DownloadInfo downloadInfo);

    }

    


}
