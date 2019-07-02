package com.eenet.androidbase.router;

import java.util.Map;

/**
 * Created by xiaoma on 2018/1/17.
 */

public abstract class BaseDisptachRouter implements IDispatchRouter {

    private Map<String,IAction> mActionMap;

    public BaseDisptachRouter(){
        init();
    }

    @Override
    public void dispatch(RequestOptions options) {
        if(mActionMap.containsKey(options.getAction())){
            mActionMap.get(options.getAction()).dispatch(options.getRequestParams());
        }
    }

    protected void init(){
        mActionMap = getActionMap();
    }

    protected abstract Map<String,IAction> getActionMap();
}
