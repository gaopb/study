package com.eenet.androidbase.base;

import com.eenet.androidbase.network.ResponseError;

import java.util.List;

/**
 * Created by xiaoma on 2018/1/19.
 */

public interface IBaseListView {

    void onLoadSuccess(List item);

    void onLoadFailure(ResponseError error);
}
