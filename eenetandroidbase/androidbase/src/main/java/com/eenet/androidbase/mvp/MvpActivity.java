package com.eenet.androidbase.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eenet.androidbase.BaseActivity;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenter = null;
        }
        super.onDestroy();
    }

}
