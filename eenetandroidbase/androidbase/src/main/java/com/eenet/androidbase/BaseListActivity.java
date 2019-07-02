package com.eenet.androidbase;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eenet.androidbase.mvp.BasePresenter;
import com.eenet.androidbase.mvp.MvpActivity;
import com.eenet.androidbase.network.ResponseError;
import com.eenet.androidbase.widget.ptr.IAdapterView;
import com.eenet.androidbase.widget.ptr.IPullToRefreshListener;
import com.eenet.androidbase.widget.ptr.PullToRefreshLayout;
import com.eenet.androidbase.widget.ptr.loading.ILoadingView;
import com.eenet.androidbase.widget.ptr.loading.SimpleLoadingView;
import com.jaeger.library.StatusBarUtil;

import java.util.List;


/**
 * Created by xiaoma on 2017/6/19.
 */

public abstract class BaseListActivity<P extends BasePresenter> extends MvpActivity<P> implements IAdapterView, IPullToRefreshListener {

    protected PullToRefreshLayout mPullToRefreshLayout;
    private boolean mFirstEnter = true;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        StatusBarUtil.setLightMode(this);
        initArguments();
        initContentView();
    }

    protected void initArguments() {

    }

    protected abstract int getContentView();

    protected void initContentView() {
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(getPullToRefreshLayout());
        mPullToRefreshLayout.enableRefresh(enableRefresh());
        mPullToRefreshLayout.enableLoadMore(enableLoadMore());
        mPullToRefreshLayout.setPullToRefreshListener(this);
        mPullToRefreshLayout.setLoadingView(getLoadingView());
        if (getAdapter() != null) {
            mPullToRefreshLayout.setAdapter(getAdapter(), getPageSize());
            View endView = getEndView(mPullToRefreshLayout.getAVGroup());
            if (endView != null) {
                getAdapter().setEndView(endView);
            }
            View emptyView = getEmptyView(mPullToRefreshLayout.getAVGroup());
            if (emptyView != null) {
                getAdapter().setEmptyView(emptyView);
            }
        }
        mCurrentPage = getIndexPage();
    }

    protected int getPullToRefreshLayout() {
        return R.id.list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFirstEnter) {
            load();
            mFirstEnter = false;
        }
    }

    protected void load() {
        reset();
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.onLoadingStart();
        }
        loadNext(mCurrentPage);
    }

    @Override
    public boolean enableRefresh() {
        return true;
    }

    @Override
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    public ILoadingView getLoadingView() {
        return new SimpleLoadingView(this);
    }

    @Override
    public int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }


    @Override
    public void onRefresh() {
        reset();
        loadNext(mCurrentPage);
    }

    @Override
    public void onLoadMore() {
        loadNext(mCurrentPage);
    }


    protected int getIndexPage() {
        return DEFAULT_INDEX_PAGE;
    }

    protected void reset() {
        mCurrentPage = getIndexPage();
    }

    protected void onLoadSuccess(int page, List items) {
        if (page == getIndexPage()) {
            getAdapter().clear();
            mPullToRefreshLayout.onRefreshComplete();
        } else {
            mPullToRefreshLayout.onLoadMoreComplete();
        }
        notifyDataChangeAfter(items);
        mCurrentPage++;
    }

    public void onLoadSuccess(List items){
        onLoadSuccess(getCurrentPage(),items);
    }


    protected void notifyDataChangeAfter(List items) {
        getAdapter().notifyDataChangeAfter(items);
    }

    public void onLoadFailure(int page, ResponseError error) {
        if (page == getIndexPage()) {
            mPullToRefreshLayout.onRefreshFailure(error);
        } else {
            mPullToRefreshLayout.onLoadMoreFailure(error);
        }
    }

    public void onLoadFailure(ResponseError error){
        onLoadFailure(getCurrentPage(),error);
    }


    @Override
    public View getEndView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.include_end_view, parent, false);
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.empty_icom_view, parent, false);
    }

    protected int getCurrentPage(){
        return mCurrentPage;
    }


}
