package com.eenet.androidbase.widget.ptr.loading;

import com.eenet.androidbase.network.ResponseError;

/**
 * Created by xiaoma on 2017/6/28.
 */

public interface ILoadingView {

    enum Status {
        PREPARE,
        LOADING,
        FAIL,
        SUCCESS
    }

    void onLoadingStart();

    void onLoadingComplete();

    void onLoadingFailure(ResponseError error);

    void setReloadListener(IReloadListener iReloadListener);

    boolean isVisible();

}
