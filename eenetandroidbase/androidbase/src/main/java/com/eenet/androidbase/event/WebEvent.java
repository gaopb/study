package com.eenet.androidbase.event;

/**
 * 不同模块内部网页跳转临时解决方案
 * Created by xiaoma on 2018/1/9.
 */

public class WebEvent {

    private String mLink;

    public WebEvent(String link){
        this.mLink = link;
    }

    public String getLink() {
        return mLink;
    }
}
