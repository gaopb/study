package com.eenet.androidbase.router;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoma on 2018/1/17.
 */

public class AndroidRouter {

    private static final String TAG = AndroidRouter.class.getSimpleName();

    private static volatile AndroidRouter sInstance;


    private Map<String,IDispatchRouter> mAllDispatchRouter;

    public static AndroidRouter getInstance(){
        if(sInstance == null){
            synchronized (AndroidRouter.class){
                if(sInstance == null){
                    sInstance = new AndroidRouter();
                }
            }
        }
        return sInstance;
    }

    private AndroidRouter(){
        mAllDispatchRouter = new HashMap<>();
    }

    public void register(String module,IDispatchRouter router){
        if(TextUtils.isEmpty(module) || router == null){
            return;
        }
        mAllDispatchRouter.put(module,router);
    }

    public void dipatch(RequestOptions options){
        if(options != null){
            String module = options.getModule();
            if(mAllDispatchRouter.containsKey(module)){
                mAllDispatchRouter.get(module).dispatch(options);
            }
        }
    }

}
