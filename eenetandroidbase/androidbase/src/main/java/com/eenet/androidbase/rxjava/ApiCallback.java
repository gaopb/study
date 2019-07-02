package com.eenet.androidbase.rxjava;

import android.text.TextUtils;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;

public abstract class ApiCallback<T> extends Subscriber<T> {

    public abstract void onBegin();

    public abstract void onSuccess(T model);

    public abstract void onWrong(String msg);

    public abstract void onEnd();

    @Override
    public void onStart() {
        onBegin();
    }

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            try {
                HttpException httpException = (HttpException) e;
                int code = httpException.code();
                String str = httpException.response().errorBody().string();
                if (!TextUtils.isEmpty(str)) {
                    JSONObject jsonObject = new JSONObject(str);
                    String message = jsonObject.getString("message");
                    if (!TextUtils.isEmpty(message)){
                        onWrong(message);
                    } else {
                        onWrong("未知错误");
                    }
                } else {
                    if (code == 204) {
                        onWrong("暂无数据");
                    } else if (code == 400) {
                        onWrong("请求失败");
                    } else if (code == 500) {
                        onWrong("系统繁忙，请稍后访问");
                    } else {
                        onWrong("未知错误");
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                onWrong("未知错误");
            }
        } else if (e instanceof SocketTimeoutException) {
            onWrong("请求超时,请稍后再试");
        } else {
            onWrong("未知错误");
        }
        onEnd();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

}
