package com.eenet.androidbase.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.eenet.androidbase.R;


/**
 * Created by xiaoma on 2017/7/5.
 */

public class PullToRefreshCompatListView extends PullToRefreshListView {
    public PullToRefreshCompatListView(Context context) {
        super(context);
    }

    public PullToRefreshCompatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshCompatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayoutInflate(Context context) {
        LayoutInflater.from(context).inflate(R.layout.include_ptr_layout_compat,this,true);
    }
}
