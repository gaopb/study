package com.eenet.androidbase.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.eenet.androidbase.BaseApplication;

import java.io.File;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                      / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                     \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //               ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                           `=---='
 * //
 * //        .............................................
 * //                 佛祖保佑       永无BUG
 * Created by chenxiaozhou on 2016/12/29.
 */

public class DownloadAppUtils {

    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    /**
     * 通过浏览器下载APK包
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "tmp.apk")),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     * @param context
     * @param url
     */
    public static void downloadForAutoInstall(Context context, String url, String fileName, String title) {

        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) context
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //在通知栏中显示
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setVisibleInDownloadsUi(true);
            request.setTitle(title);
            request.setMimeType("application/vnd.android.package-archive");
            downloadUpdateApkFilePath = BaseApplication.getPathName() + File.separator + fileName;
            // 若存在，则删除
            File file = new File(downloadUpdateApkFilePath);
            if (file.exists()) {
                file.delete();
            }
            request.setDestinationInExternalFilesDir(context,BaseApplication.getPathName(),fileName);
            downloadUpdateApkId = downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(context, url);
        }
    }

}
