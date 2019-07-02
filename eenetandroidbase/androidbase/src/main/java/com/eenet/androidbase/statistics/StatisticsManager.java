package com.eenet.androidbase.statistics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoma on 2017/7/30.
 */

public class StatisticsManager {

    private static StatisticsManager sInstance;

    private IStatisticsPresenter mDelegatePresenter;

    public static StatisticsManager getInstance(){
        if(sInstance == null){
            synchronized (StatisticsManager.class){
                if(sInstance == null){
                    sInstance = new StatisticsManager();
                }
            }
        }
        return sInstance;
    }

    public void registerPresenter(IStatisticsPresenter presenter){
        this.mDelegatePresenter = presenter;
    }

    public void recordLogs(String mark){
        if(mDelegatePresenter == null){
            return;
        }
        mDelegatePresenter.recordLogs(mark);
    }

    public void recordLogs(String mark, Map<String,Object> extras){
        if(mDelegatePresenter == null){
            return;
        }
        mDelegatePresenter.recordLogs(mark,extras);
    }

    public void recordLogs(String mark,Object...args){
        if(mDelegatePresenter == null){
            return;
        }
        if(args == null || args.length%2 != 0){
            mDelegatePresenter.recordLogs(mark);
            return;
        }
        Map<String,Object> extras = new HashMap<>();
        for (int i = 0; i < args.length; i+=2) {
            if(args[i] != null && args[i+1] != null) {
                extras.put((String) args[i], args[i + 1]);
            }
        }
        mDelegatePresenter.recordLogs(mark,extras);
    }

    public static class Extra{

        public static final String EXTRA_ACTION1 = "a1";

        public static final String EXTRA_ACTION2 = "a2";

        public static final String EXTRA_ACTION3 = "a3";
    }

}
