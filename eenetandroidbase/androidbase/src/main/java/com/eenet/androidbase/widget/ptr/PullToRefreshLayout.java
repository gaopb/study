package com.eenet.androidbase.widget.ptr;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badoo.mobile.util.WeakHandler;
import com.eenet.androidbase.BaseAdapter;
import com.eenet.androidbase.R;
import com.eenet.androidbase.network.ResponseError;
import com.eenet.androidbase.widget.ptr.loading.ILoadingView;

/**
 * Created by xiaoma on 2017/6/28.
 */

public class PullToRefreshLayout extends RelativeLayout implements IPullToRefreshLayout{

    private RelativeLayout mPtrContainer;
    private ILoadingView mLoadingView;
    private ILoadingView mFooterView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseAdapter mAdapter;
    private IPullToRefreshListener mPullToRefreshListener;


    private boolean mIsRefresh = false;

    private boolean mIsLoadMore = false;

    private boolean mLoadMoreEnable = true;

    private WeakHandler mUIHandler = new WeakHandler();

    protected Context mContext;

    public PullToRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context){
        this.mContext = context;
        onLayoutInflate(context);
        mPtrContainer = getPtrContainer();
        mRecyclerView = getRecyclerView();
        mSwipeRefreshLayout = getSwipeRefreshLayout();
        initSwipeRefreshLayout();
        initListener();
    }

    protected SwipeRefreshLayout getSwipeRefreshLayout(){
        return (SwipeRefreshLayout) findViewById(R.id.swipe);
    }

    protected RecyclerView getRecyclerView(){
        return (RecyclerView) findViewById(R.id.recyclerView);
    }

    protected RelativeLayout getPtrContainer(){
        return (RelativeLayout) findViewById(R.id.ptr_container);
    }

    protected void onLayoutInflate(Context context){
        LayoutInflater.from(context).inflate(R.layout.include_ptr_layout,this,true);
    }


    protected void initSwipeRefreshLayout(){
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(new int[]{getResources().getColor(R.color.swipeColors)});
        }
    }

    private void initListener(){
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    public void setAdapter(BaseAdapter adapter,int pageSize){
        if(adapter == null){
            throw new IllegalArgumentException("adapter不能为空");
        }
        this.mAdapter = adapter;
        this.mAdapter.setLoadMoreListener(this);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mAdapter.openLoadMore(pageSize,mLoadMoreEnable);
    }

    public BaseAdapter getAdapter(){
        return mAdapter;
    }


    public void setLoadingView(ILoadingView loadingView){
        if(loadingView != null && loadingView instanceof View){
            mPtrContainer.addView((View) loadingView,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            mLoadingView = loadingView;
            mLoadingView.setReloadListener(this);
        }
    }

    public void setFooterView(ILoadingView footerView){
        if(footerView != null && footerView instanceof View){
            mAdapter.setLoadingView((View) footerView);
            mFooterView = footerView;
        }
    }

    public void setPullToRefreshListener(IPullToRefreshListener listener){
        this.mPullToRefreshListener = listener;
    }

    public void onLoadingStart(){
        if(mSwipeRefreshLayout != null && mSwipeRefreshLayout.getVisibility() == View.VISIBLE && !mSwipeRefreshLayout.isRefreshing()){
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
        if(mLoadingView != null && mLoadingView.isVisible()){
            mLoadingView.onLoadingStart();
        }
    }
    /**
     * 是否可以刷新
     * @param enable
     */
    public void enableRefresh(boolean enable){
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    /**
     * 是否可以加载更多
     * @param enable
     */
    public void enableLoadMore(boolean enable){
        mLoadMoreEnable = enable;
        if(mAdapter != null) {
            mAdapter.openLoadMore(enable);
        }
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        if(isInterceptRefresh()){
            onRefreshComplete();
            return;
        }
        if(mPullToRefreshListener != null){
            mPullToRefreshListener.onRefresh();
        }
    }

    public void onRefreshComplete(){
        if(mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.getVisibility() != View.VISIBLE) {
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            }
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            if(mRecyclerView.getVisibility() == View.GONE){
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        if(mLoadingView != null){
            mLoadingView.onLoadingComplete();
        }
        mIsRefresh = false;
    }

    public void onRefreshFailure(ResponseError error){
        if(mSwipeRefreshLayout != null && mSwipeRefreshLayout.getVisibility() == View.VISIBLE && mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(mLoadingView != null){
            mLoadingView.onLoadingFailure(error);
        }
        mIsRefresh = false;
    }

    public void onLoadMoreFailure(ResponseError error){
        if(mFooterView != null){
            mFooterView.onLoadingFailure(error);
        }
        mIsLoadMore = false;
    }



    public void onLoadMoreComplete(){
        mIsLoadMore = false;
    }


    @Override
    public void onLoadMore() {
        mIsLoadMore = true;
        if(isInterceptLoadMore()){
            onLoadMoreComplete();
            return;
        }
        if(mPullToRefreshListener != null){
            mPullToRefreshListener.onLoadMore();
        }
    }

    /**
     * 是否拦截刷新
     * @return
     */
    protected boolean isInterceptRefresh(){
        return mIsLoadMore;
    }

    /**
     * 是否拦截上拉加载
     * @return
     */
    protected boolean isInterceptLoadMore(){
        return mIsRefresh;
    }


    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void reload() {
        onRefresh();
    }


    public void addHeaderView(View header){
        if(mAdapter != null){
            mAdapter.addHeaderView(header);
        }
    }

    @Override
    public void addFooterView(View view) {
        if(mAdapter != null){
            mAdapter.addFooterView(view);
        }
    }

    public RecyclerView getListView(){
        return mRecyclerView;
    }

    public ViewGroup getAVGroup(){
        return mRecyclerView;
    }

    public boolean isLoadMore(){
        return mIsLoadMore;
    }
}
