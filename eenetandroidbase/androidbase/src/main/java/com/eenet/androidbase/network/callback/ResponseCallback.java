package com.eenet.androidbase.network.callback;

import com.eenet.androidbase.network.ErrorType;
import com.eenet.androidbase.network.Response;
import com.eenet.androidbase.network.ResponseError;

/**
 * Created by xiaoma on 2018/1/19.
 */

public abstract class ResponseCallback<T extends Response> extends ApiCallback<T> {
    @Override
    public void onBegin() {

    }

    @Override
    public void onNext(T t) {
        if(isSuccess(t)){
            onSuccess(t);
        } else {
            ResponseError.Builder builder = new ResponseError.Builder();
            builder.type(ErrorType.API);
            builder.code(t.getCode()).msg(t.getMessage());
            onFailure(builder.build());
        }
    }

    protected boolean isSuccess(T t){
        return t.isSuccess();
    }

    @Override
    public void onEnd() {

    }
}
