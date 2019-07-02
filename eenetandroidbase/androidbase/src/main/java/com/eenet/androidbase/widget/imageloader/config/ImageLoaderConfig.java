package com.eenet.androidbase.widget.imageloader.config;


import com.eenet.androidbase.widget.imageloader.impl.DefaInterceptor;

/**
 * 图片加载的配置类
 */
public class ImageLoaderConfig {
    private ImageLoaderClient mClient;//具体加载器
    private ImageInterceptor mInterceptor;//拦截器,默认=DefaInterceptor
    private int mPlacePicRes;//全局的占位图
    private int mErrorPicRes;//全局的错误图

    public ImageLoaderConfig(Builder builder) {
        mClient = builder.mLoderClient;
        mInterceptor = builder.mLoderInterceptor;
        mPlacePicRes = builder.placePicRes;
        mErrorPicRes = builder.errorPicRes;
    }

    public ImageLoaderClient getLoderClient() {
        return mClient;
    }

    public ImageInterceptor getInterceptor() {
        return mInterceptor;
    }

    public int getPlacePicRes() {
        return mPlacePicRes;
    }

    public int getErrorPicRes() {
        return mErrorPicRes;
    }

    public static class Builder {
        ImageLoaderClient mLoderClient;
        ImageInterceptor mLoderInterceptor;
        private int placePicRes;
        private int errorPicRes;

        public Builder() {
            mLoderInterceptor = new DefaInterceptor();
        }

        /**
         * 配置当前的加载器
         *
         * @param provider
         * @return
         */
        public Builder client(ImageLoaderClient provider) {
            mLoderClient = provider;
            return this;
        }

        /**
         * 设置占位图
         *
         * @param placePicRes
         * @return
         */
        public Builder placePicRes(int placePicRes) {
            this.placePicRes = placePicRes;
            return this;
        }

        /**
         * 设置错误图
         *
         * @param errorPicRes
         * @return
         */
        public Builder errorPicRes(int errorPicRes) {
            this.errorPicRes = errorPicRes;
            return this;
        }

        /**
         * 配置拦截器
         *
         * @param interceptor
         * @return
         */
        public Builder interceptor(ImageInterceptor interceptor) {
            mLoderInterceptor = interceptor;
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }
    }
}  