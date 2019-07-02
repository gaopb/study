package com.eenet.androidbase.network;

/**
 * Created by xiaoma on 2018/1/19.
 */

public abstract class Response {

    public abstract boolean isSuccess();

    public abstract int getCode();

    public abstract String getMessage();
}
