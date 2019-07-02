package com.eenet.androidbase.router;

import java.util.Map;

/**
 * Created by xiaoma on 2018/1/17.
 */

public interface IAction {

    void dispatch(Map<String,Object> params);
}
