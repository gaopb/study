package com.eenet.androidbase.widget.imageloader.impl;

import com.eenet.androidbase.widget.imageloader.config.ImageInterceptor;

/**
 * 默认拦截器,不做任何拦截
 */
public class DefaInterceptor implements ImageInterceptor {

    @Override
    public String InterceptorUrl(String oldUrl) {
        return oldUrl;
    }
}  