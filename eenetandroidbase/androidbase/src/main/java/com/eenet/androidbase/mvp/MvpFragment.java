package com.eenet.androidbase.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eenet.androidbase.BaseFragment;

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P mvpPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenter = null;
        }
        super.onDestroy();
    }

}
