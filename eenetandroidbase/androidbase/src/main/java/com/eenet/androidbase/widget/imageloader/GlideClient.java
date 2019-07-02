package com.eenet.androidbase.widget.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eenet.androidbase.BaseApplication;
import com.eenet.androidbase.widget.imageloader.config.ImageLoaderClient;


/**
 * 使用glide进行图片加载的实例
 */
public class GlideClient implements ImageLoaderClient {

    @Override
    public void loadImage(ImageView view, String url, int placePicRes, int errorPicRes) {
        Glide.with(BaseApplication.getContext()).load(url).dontAnimate().placeholder(placePicRes).error(errorPicRes).into(view);
    }

    @Override
    public void loadImage(ImageView view, String url, int placePicRes, int errorPicRes, float
            thumbnail) {
        Glide.with(BaseApplication.getContext()).load(url).dontAnimate().placeholder(placePicRes).error(errorPicRes).thumbnail(thumbnail).into(view);
    }
}  