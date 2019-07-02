package com.eenet.androidbase.api;

import com.eenet.androidbase.BaseApplication;
import com.eenet.androidbase.bean.MarkBean;
import com.eenet.androidbase.mvp.BaseMvpView;
import com.eenet.androidbase.mvp.BasePresenter;
import com.eenet.androidbase.retrofit.ApiClient;
import com.eenet.androidbase.rxjava.ApiCallback;
import com.eenet.androidbase.utils.DateUtils;
import com.eenet.androidbase.utils.NetConnectUtils;
import com.eenet.androidbase.utils.PhoneInfomation;

import java.util.Map;

/**
 * Created by yao23 on 2017/6/30.
 */

public class DataCollectionPresenter extends BasePresenter<BaseMvpView> {

    public ApiStores apiStores;

    @Override
    protected void initApiStores() {

        apiStores = ApiClient.retrofit(ApiStores.ServerUrl).create(ApiStores.class);
    }

    public DataCollectionPresenter() {
        attachView(null);
    }

    public void recordLogs(Map<String,Object> params){
        if(params == null){
            return;
        }
        String ip = NetConnectUtils.getIPAddress(BaseApplication.getContext());
        params.put("page_type","app");
        params.put("ip",ip);
        params.put("os_type","Android");
        params.put("t", DateUtils.getCurrentTimeFormat("yyyy-MM-dd HH:mm:ss"));
        params.put("url","");
        params.put("app_ver",PhoneInfomation.getAppVersion());
        params.put("imei",PhoneInfomation.getIMEI(BaseApplication.getContext()));
        params.put("cellphone_model",PhoneInfomation.getProductName() + PhoneInfomation.getModelName());
        params.put("download_channel","default");

        addSubscription(apiStores.recordLogs(params),new ApiCallback<MarkBean>(){

            @Override
            public void onBegin() {

            }

            @Override
            public void onSuccess(MarkBean model) {

            }

            @Override
            public void onWrong(String msg) {

            }

            @Override
            public void onEnd() {

            }
        });
    }
}
