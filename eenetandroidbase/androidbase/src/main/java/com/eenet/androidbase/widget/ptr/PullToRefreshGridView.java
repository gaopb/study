package com.eenet.androidbase.widget.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import com.eenet.androidbase.R;


/**
 * Created by xiaoma on 2017/6/28.
 */

public class PullToRefreshGridView extends PullToRefreshLayout {


    public PullToRefreshGridView(Context context) {
        this(context,null);
    }

    public PullToRefreshGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullToRefreshGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.PullToRefreshGridView);
        int column = ta.getInt(R.styleable.PullToRefreshGridView_ptr_column,2);
        ta.recycle();
        Log.e("Ptr","column:" + column);
        setColumn(column);
    }

    public void setColumn(int column){
        setLayoutManager(new GridLayoutManager(getContext(),column));
    }
}