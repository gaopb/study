package com.eenet.androidbase.crash;

/**
 * Created by xiaoma on 2018/1/9.
 */

public interface ICrashReport {

    void reportCrash(String error);

    void reportCrash(Throwable throwable);
}
