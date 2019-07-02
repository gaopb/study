package com.eenet.androidbase.watch;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import java.util.HashSet;
import java.util.List;

/**
 * 前后台切换监控，不考虑多进程
 * Created by xiaoma on 2018/1/22.
 */

public class FrontBackSwitchWatch {

    private static volatile FrontBackSwitchWatch sInstance;

    private boolean mRegister;

    private HashSet<ISwitchWatchListener> mWatchListeners;


    private boolean mForeground = false;

    public static FrontBackSwitchWatch getInstance(){
        if(sInstance == null){
            synchronized (FrontBackSwitchWatch.class){
                if(sInstance == null){
                    sInstance = new FrontBackSwitchWatch();
                }
            }
        }
        return sInstance;
    }

    private FrontBackSwitchWatch(){
        mWatchListeners = new HashSet<>();
    }

    public synchronized void init(Application context){
        if(mRegister){
            return;
        }
        mRegister = true;
        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if(!mForeground){
                    switchFront();
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if(isAppInBackground(activity)){
                    switchBack();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void switchFront(){
        mForeground = true;
        for (ISwitchWatchListener watchListener : mWatchListeners) {
            watchListener.onSwitchFront();
        }
    }

    private void switchBack(){
        mForeground = false;
        for (ISwitchWatchListener watchListener : mWatchListeners) {
            watchListener.onSwitchBack();
        }
    }

    public void addSwitchWatchListener(ISwitchWatchListener listener){
        mWatchListeners.add(listener);
    }

    public void removeSwitchWatchListener(ISwitchWatchListener listener){
        mWatchListeners.remove(listener);
    }

    public interface ISwitchWatchListener{

        void onSwitchFront();

        void onSwitchBack();
    }

    private boolean isAppInBackground(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
