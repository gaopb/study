package com.eenet.androidbase.network.callback;

import com.eenet.androidbase.network.ErrorType;
import com.eenet.androidbase.network.ResponseError;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import rx.Subscriber;

import static com.eenet.androidbase.network.StatusCode.STATUS_CONNECT_TIMECOUT;
import static com.eenet.androidbase.network.StatusCode.STATUS_UN_CONNECT;
import static com.eenet.androidbase.network.StatusCode.STATUS_UN_KNOWN;

/**
 * Created by xiaoma on 2018/1/19.
 */

public abstract class ApiCallback<T> extends Subscriber<T> {

    public abstract void onBegin();

    public abstract void onSuccess(T model);

    public abstract void onFailure(ResponseError error);

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
        ResponseError.Builder builder = new ResponseError.Builder();
        if (e instanceof HttpException) {
            builder.type(ErrorType.SERVER);
            try {
                HttpException httpException = (HttpException) e;
                int code = httpException.code();
                builder.code(code);
            } catch (Exception e1) {
                e1.printStackTrace();
                builder.code(STATUS_UN_KNOWN);
            }
        } else {
            builder.type(ErrorType.CLIENT);
            if (e instanceof SocketTimeoutException) {
                builder.code(STATUS_CONNECT_TIMECOUT);
            } else if(e instanceof ConnectException || e instanceof UnknownHostException){
                builder.code(STATUS_UN_CONNECT);
            } else {
                builder.code(STATUS_UN_KNOWN);
            }
        }
        onFailure(builder.build());
        onEnd();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

}
