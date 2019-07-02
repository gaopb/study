package com.eenet.androidbase.api;

import com.eenet.androidbase.bean.MarkBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yao23 on 2017/6/30.
 */

public interface ApiStores {

    String ServerUrl = "http://security-api.open.gzedu.com/";
//    "http://security-api.open.gzedu.com/"

    //数据收集接口
    @FormUrlEncoded
    @POST(ServerUrl+"recordLogs")
    Observable<String> recordLogs(@Field("appId") String appId,
                                  @Field("appSecretKey") String appSecretKey,
                                  @Field("t") String time, @Field("buried_point") String buriedPoint,
                                  @Field("page_type") String pageType, @Field("action") String action,
                                  @Field("app_ver") String app_ver, @Field("os_type") String os_type,
                                  @Field("user_id") String user_id, @Field("login_account") String login_account,
                                  @Field("ip")String ip, @Field("url")String url, @Field("app_id")String app_id);


    // 数据埋点行为
    @FormUrlEncoded
    @POST(ServerUrl+"recordLogs")
    Observable<MarkBean> recordLogs(@FieldMap Map<String,Object> params);
}
