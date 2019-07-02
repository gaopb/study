package com.eenet.androidbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eenet.androidbase.mvp.BasePresenter;
import com.eenet.androidbase.mvp.MvpFragment;

/**
 * Created by xiaoma on 2017/4/25.
 */

public abstract class BaseListLazyFragment<P extends BasePresenter> extends MvpFragment<P> {

    protected boolean isVisible;

    protected boolean isPrepared;

    protected boolean hasLoadOnce;

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView != null){
            ViewGroup viewGroup = (ViewGroup) mView.getParent();
            if(viewGroup != null) {
                viewGroup.removeView(mView);
            }
            return mView;
        }
        initArguments();
        isPrepared = true;
        mView = inflater.inflate(getContentView(),container,false);
        initContentView(mView);
        if(isCanLoad()){
            load();
            hasLoadOnce = true;
        }
        return mView;
    }

    protected abstract void initArguments();

    protected abstract int getContentView();

    protected abstract void initContentView(View parent);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            boolean isCanLoad = isCanLoad();
            if(isCanLoad){
                load();
                hasLoadOnce = true;
            }
        } else {
            isVisible = false;
        }
    }

    protected abstract void load();
    /**
     * 是否可以加载
     *
     * @return
     */
    public boolean isCanLoad(){
        return isVisible && isPrepared && !hasLoadOnce;
    }

}
