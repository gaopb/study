package com.eenet.androidbase.widget.ptr.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.eenet.androidbase.R;
import com.eenet.androidbase.network.ResponseError;


/**
 * Created by xiaoma on 2017/6/29.
 */

public class SimpleLoadingView extends TextView implements ILoadingView {

    private Status mStatus = Status.PREPARE;
    private IReloadListener mReloadListener;

    public SimpleLoadingView(Context context) {
        super(context);
        init();
    }

    public SimpleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setGravity(Gravity.CENTER);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReloadListener != null && mStatus == Status.FAIL){
                    mReloadListener.reload();
                    onLoadingStart();
                }
            }
        });
    }

    @Override
    public void onLoadingStart() {
        this.mStatus = Status.LOADING;
        setText(R.string.on_loading);
    }

    @Override
    public void onLoadingComplete() {
        this.mStatus = Status.SUCCESS;
        setVisibility(View.GONE);
    }

    @Override
    public void onLoadingFailure(ResponseError error) {
        this.mStatus = Status.FAIL;
        setText(R.string.on_loading_failure);
    }


    @Override
    public void setReloadListener(IReloadListener iReloadListener) {
        this.mReloadListener = iReloadListener;
    }

    @Override
    public boolean isVisible() {
        return getVisibility() == View.VISIBLE;
    }
}
