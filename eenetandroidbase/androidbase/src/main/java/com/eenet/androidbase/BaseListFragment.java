package com.eenet.androidbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eenet.androidbase.mvp.BasePresenter;
import com.eenet.androidbase.network.ResponseError;
import com.eenet.androidbase.widget.ptr.IAdapterView;
import com.eenet.androidbase.widget.ptr.IPullToRefreshListener;
import com.eenet.androidbase.widget.ptr.PullToRefreshLayout;
import com.eenet.androidbase.widget.ptr.loading.ILoadingView;
import com.eenet.androidbase.widget.ptr.loading.SimpleLoadingView;

import java.util.List;

/**
 * Created by xiaoma on 2017/6/19.
 */

public abstract class BaseListFragment<P  extends BasePresenter> extends BaseListLazyFragment<P> implements IAdapterView,IPullToRefreshListener {

    protected PullToRefreshLayout mPullToRefreshLayout;
    private int mCurrentPage;
    private boolean isShowEndView = true;

    @Override
    protected void initContentView(View parent) {
        mPullToRefreshLayout = (PullToRefreshLayout) parent.findViewById(getPullToRefreshLayout());
        mPullToRefreshLayout.enableRefresh(enableRefresh());
        mPullToRefreshLayout.enableLoadMore(enableLoadMore());
        mPullToRefreshLayout.setPullToRefreshListener(this);
        mPullToRefreshLayout.setLoadingView(getLoadingView());
        if (getAdapter() != null) {
            mPullToRefreshLayout.setAdapter(getAdapter(),getPageSize());
            if (isShowEndView){
                View endView = getEndView(mPullToRefreshLayout.getAVGroup());
                if (endView != null) {
                    getAdapter().setEndView(endView);
                }
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
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    public boolean enableRefresh() {
        return true;
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

    @Override
    public int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    protected int getIndexPage() {
        return DEFAULT_INDEX_PAGE;
    }

    protected void reset() {
        mCurrentPage = getIndexPage();
    }

    public void onLoadSuccess(List items) {
        if (getCurrentPage() == getIndexPage()) {
            getAdapter().clear();
            mPullToRefreshLayout.onRefreshComplete();
        } else {
            mPullToRefreshLayout.onLoadMoreComplete();
        }
        notifyDataChangeAfter(items);
        mCurrentPage++;

    }

    protected void notifyDataChangeAfter(List items) {
        getAdapter().notifyDataChangeAfter(items);
    }

    public void onLoadFailure(ResponseError error) {
        if (getCurrentPage() == getIndexPage()) {
            mPullToRefreshLayout.onRefreshFailure(error);
        } else {
            mPullToRefreshLayout.onLoadMoreFailure(error);
        }
    }

    @Override
    protected void load() {
        reset();
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.onLoadingStart();
        }
        loadNext(mCurrentPage);
    }

    @Override
    public ILoadingView getLoadingView() {
        return new SimpleLoadingView(getActivity());
    }

    @Override
    protected void initArguments() {

    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.empty_icom_view,parent,false);
    }

    @Override
    public View getEndView(ViewGroup parent) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.include_end_view, parent,false);
    }

    protected int getCurrentPage(){
        return mCurrentPage;
    }

    protected void setShowEndView(boolean isShowEndView){
        this.isShowEndView = isShowEndView;
    }
}
