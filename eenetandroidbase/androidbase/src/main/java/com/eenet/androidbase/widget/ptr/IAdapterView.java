package com.eenet.androidbase.widget.ptr;

import android.view.View;
import android.view.ViewGroup;

import com.eenet.androidbase.BaseAdapter;
import com.eenet.androidbase.widget.ptr.loading.ILoadingView;


/**
 * Created by xiaoma on 2017/6/28.
 */

public interface IAdapterView {

    int DEFAULT_PAGE_SIZE = 10;
    int DEFAULT_INDEX_PAGE = 1;

    BaseAdapter getAdapter();

    boolean enableRefresh();

    boolean enableLoadMore();

    ILoadingView getLoadingView();

    int getPageSize();

    void loadNext(int page);

    View getEndView(ViewGroup parent);

    View getEmptyView(ViewGroup parent);
}
