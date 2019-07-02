package com.eenet.androidbase.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xiaoma on 2017/7/29.
 */

public class MarkBean {

    @SerializedName("successful")
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
