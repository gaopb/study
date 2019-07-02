package com.eenet.androidbase.router;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoma on 2018/1/17.
 */

public class RequestOptions {

    private final String mModule;
    private final String mAction;
    private final Map<String,Object> mRequestParams;

    private RequestOptions(Builder builder){
        this.mModule = builder.mModule;
        this.mAction = builder.mAction;
        this.mRequestParams = builder.mParams;
    }

    public String getModule() {
        return mModule;
    }

    public String getAction() {
        return mAction;
    }

    public Map<String, Object> getRequestParams() {
        return mRequestParams;
    }

    public boolean isEmptyParams(){
        return mRequestParams == null || mRequestParams.isEmpty();
    }

    public static class Builder{
        private String mModule;
        private String mAction;
        private Map<String,Object> mParams;

        public Builder module(String module){
            this.mModule = module;
            return this;
        }

        public Builder action(String action){
            this.mAction = action;
            return this;
        }

        public Builder params(String key,Object value){
            if(TextUtils.isEmpty(key) || value == null) {
                return this;
            }
            if(mParams == null){
                mParams = new HashMap<>();
            }
            mParams.put(key,value);
            return this;
        }

        public RequestOptions build(){
            checkRequestOptions();
            return new RequestOptions(this);
        }

        private void checkRequestOptions(){
            if(TextUtils.isEmpty(mModule)){
                throw new IllegalArgumentException("module不能为空");
            }
            if(TextUtils.isEmpty(mAction)){
                throw new IllegalArgumentException("action不能为空");
            }
        }

    }
}
