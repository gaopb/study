package com.eenet.androidbase.statistics;

import java.util.Map;

/**
 * Created by xiaoma on 2017/7/30.
 */

public interface IStatisticsPresenter {

    void recordLogs(String mark);

    void recordLogs(String mark, Map<String,Object> params);
}
