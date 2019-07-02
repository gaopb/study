package com.eenet.androidbase;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.eenet.androidbase.utils.SdCardTool;
import com.eenet.androidbase.watch.IRefWatcher;
import com.eenet.androidbase.widget.imageloader.GlideClient;
import com.eenet.androidbase.widget.imageloader.config.ImageLoaderConfig;
import com.eenet.androidbase.widget.imageloader.impl.DefaInterceptor;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxiaozhou on 16/6/15.
 */
public abstract class BaseApplication extends Application {


    private static String pathName;

    public static String getPathName() {
        return pathName;
    }

    private static Context context;

    public static Context getContext() {
        return context;
    }

    private IRefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        String processName = getProcessName(android.os.Process.myPid());
        if (getPackageName().equals(processName)) {
            pathName = SdCardTool.getRootFilePath() + "/eenet";
            File path = new File(pathName);
            if (!path.exists()) {
                path.mkdir();
            }

            ImageLoaderConfig loaderConfig = new ImageLoaderConfig.Builder()
                    .client(new GlideClient())//设置加载器
                    .placePicRes(R.mipmap.img_jingtaijiazai)//占位图
                    .errorPicRes(R.mipmap.img_jingtaijiazai)//错误图
                    .interceptor(new DefaInterceptor())//设置拦截器
                    .build();
            ImageLoader.init(loaderConfig);//初始化CommonImageLoader

            otherInit(context);
            mRefWatcher = new IRefWatcher() {
                @Override
                public void watch(Object object) {
                    Log.d("RefWatcher","not insall watcher");
                }
            };
        }

    }

    protected abstract void otherInit(Context context);

    /**
     * 根据进程 ID 获取进程名
     * @param pid
     * @return String
     */
    public static String getProcessName(int pid){
        ActivityManager am = (ActivityManager) BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    private static Map<String,String> mAppBaseInfo;

    public static Map<String, String> getAppBaseInfo() {
        return mAppBaseInfo;
    }

    public static void setAppBaseInfo(Map<String, String> appBaseInfo) {
        BaseApplication.mAppBaseInfo = appBaseInfo;
    }


    public IRefWatcher getRefWatcher(){
        return mRefWatcher;
    }

    public void install(IRefWatcher watcher){
        this.mRefWatcher = watcher;
    }

}
