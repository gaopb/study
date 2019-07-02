package com.eenet.androidbase.widget.ptr;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by xiaoma on 2017/7/5.
 */

public class PullToRefreshCompatGridView extends PullToRefreshGridView {
    public PullToRefreshCompatGridView(Context context) {
        super(context);
    }

    public PullToRefreshCompatGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshCompatGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setColumn(int column) {
        setLayoutManager(new GridLayoutManager(mContext,column){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }
}
