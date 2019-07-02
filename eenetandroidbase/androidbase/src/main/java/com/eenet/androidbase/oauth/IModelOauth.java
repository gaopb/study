package com.eenet.androidbase.oauth;

import android.content.Context;

/**
 * 模块的用户数据处理
 * Created by xiaoma on 2018/1/9.
 */

public interface IModelOauth<T> {

    void logout();

    void init(Context context);

    boolean isLogin();

    void login(T t);

    void setSign(String sign);
}
