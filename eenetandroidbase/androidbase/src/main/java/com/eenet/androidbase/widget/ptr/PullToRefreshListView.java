package com.eenet.androidbase.widget.ptr;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by xiaoma on 2017/6/28.
 */

public class PullToRefreshListView extends PullToRefreshLayout{

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        setLayoutManager(new LinearLayoutManager(context));
    }
}
