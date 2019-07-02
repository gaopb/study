package com.eenet.androidbase.map;

/**
 * Created by xiaoma on 2017/11/7.
 */

public class GeoResponseBean {

    private int status;

    private GeoResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public GeoResultBean getResult() {
        return result;
    }

    public void setResult(GeoResultBean result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return status == 0;
    }
}
