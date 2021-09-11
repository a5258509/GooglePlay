package com.example.googleplay.http.download;

import com.example.googleplay.bean.Home;

import java.io.File;

/**
 * 用来封装下载任务相关的数据
 */
public class DownloadInfo {

    public long index;//下载任务的唯一标识,存取时使用
    public int state;//下载状态
    public String downloadUrl;//下载地址
    public long currentLength;//当前已下载的长度
    public String size;//总的大小
    public String path; //下载文件保存的完整的绝对路径


    public static DownloadInfo create(Home home){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.downloadUrl=home.serverData.downUrl;
        downloadInfo.index=home.serverData.index;
        downloadInfo.state=DownloadManager.STATE_NONE;
        downloadInfo.size=home.serverData.size;
        downloadInfo.currentLength=0;

        //下载文件的绝对路径
        downloadInfo.path=DownloadManager.DOWNLOAD_DIR+ File.separator
                          + home.serverData.title+".apk";

        return downloadInfo;

    }

}
