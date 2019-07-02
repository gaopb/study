package com.eenet.androidbase.widget.ptr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.eenet.androidbase.BaseAdapter;
import com.eenet.androidbase.network.ResponseError;
import com.eenet.androidbase.widget.ptr.loading.ILoadingView;
import com.eenet.androidbase.widget.ptr.loading.IReloadListener;

/**
 * Created by xiaoma on 2017/12/26.
 */

public interface IPullToRefreshLayout extends BaseAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,IReloadListener{

    void setAdapter(BaseAdapter adapter,int pageSize);

    void setLoadingView(ILoadingView loadingView);

    void setFooterView(ILoadingView loadingView);

    void setPullToRefreshListener(IPullToRefreshListener listener);

    void onRefreshComplete();

    void onRefreshFailure(ResponseError error);

    void onLoadMoreFailure(ResponseError error);

    void onLoadMoreComplete();

    void addHeaderView(View view);

    void addFooterView(View view);

    BaseAdapter getAdapter();

}
