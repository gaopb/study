package com.eenet.androidbase.widget.imageloader.config;

import android.widget.ImageView;

/**
 * 图片加载接口,定义图片加载的方法,如果需要更换图片加载的框架,实现该接口即可
 */
public interface ImageLoaderClient {
    /**
     * 正常加载图片
     *
     * @param view
     * @param url
     */
    void loadImage(ImageView view, String url, int placePicRes, int errorPicRes);

    /**
     * 先展示缩略图,加载完成展示原图
     *
     * @param view
     * @param url
     * @param placePicRes
     * @param errorPicRes
     * @param thumbnail 
     */
    void loadImage(ImageView view, String url, int placePicRes, int errorPicRes, float thumbnail);
}  