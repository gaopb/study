package com.eenet.androidbase.crash;

/**
 * Created by xiaoma on 2018/1/9.
 */

public class CrashReport implements ICrashReport{

    private static CrashReport sInstance = new CrashReport();

    public static CrashReport getInstance(){
        return sInstance;
    }

    private ICrashReport mProxyReport;

    private CrashReport(){

    }

    public void registerProxy(ICrashReport report){
        this.mProxyReport = report;
    }

    @Override
    public void reportCrash(String error) {
        if(mProxyReport != null){
            mProxyReport.reportCrash(error);
        }
    }

    @Override
    public void reportCrash(Throwable throwable) {
        if(mProxyReport != null){
            mProxyReport.reportCrash(throwable);
        }
    }
}
