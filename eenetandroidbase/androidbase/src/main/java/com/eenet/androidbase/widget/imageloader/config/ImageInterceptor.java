package com.eenet.androidbase.widget.imageloader.config;

/**
 * 图片加载工具的拦截器接口
 */
public interface ImageInterceptor {

    /**
     * 加载之前可以对url进行操作,剪切,拼接统一的前缀等等
     * 如:可用于当前环境加载大小图的控制
     *
     * @param oldUrl
     * @return
     */
    String InterceptorUrl(String oldUrl);
}  