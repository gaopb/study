package com.eenet.androidbase.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

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

public class UpdateAppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 处理下载完成
        Cursor cursor=null;
        try {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                if (DownloadAppUtils.downloadUpdateApkId >= 0) {
                    long downloadId = DownloadAppUtils.downloadUpdateApkId;
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    DownloadManager downloadManager = (DownloadManager) context
                            .getSystemService(Context.DOWNLOAD_SERVICE);
                    cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()) {
                        int status = cursor.getInt(cursor
                                .getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_FAILED) {
                            downloadManager.remove(downloadId);
                        } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            if(!TextUtils.isEmpty(uriStr)) {
                                Uri uri = Uri.parse(uriStr);
                                if(uri!=null) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setDataAndType(uri,
                                            "application/vnd.android.package-archive");
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
