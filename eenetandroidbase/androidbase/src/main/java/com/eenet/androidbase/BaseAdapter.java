package com.eenet.androidbase;

import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoma on 2017/6/19.
 */

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T> implements BaseQuickAdapter.RequestLoadMoreListener {

    private OnLoadMoreListener mDelegateListener;

    private View mEndView;

    protected boolean mHasMore = true;

    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
        init();
    }

    public BaseAdapter(List<T> data) {
        super(data);
        init();
    }

    public void setLoadMoreListener(OnLoadMoreListener listener){
        this.mDelegateListener = listener;
    }

    protected void init(){
        setOnLoadMoreListener(this);
    }

    public void setItems(List<T> items){
        if(items == null){
            items = new ArrayList<>();
        }
        getData().clear();
        notifyDataChangeAfter(items);
    }

    public void addItems(List<T> items){
        if(items == null || items.isEmpty()){
            return;
        }
        addData(items);
    }

    public void addItem(T item){
        if(item != null) {
            getData().add(item);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        getData().clear();
        removeAllFooterView();
        removeAllHeaderView();
    }

    @Override
    public void onLoadMoreRequested() {
        if(mDelegateListener != null){
            mDelegateListener.onLoadMore();
        }
    }

    public void addHeaderView(View header){
        if(!hasAddHeaderView(header)){
            super.addHeaderView(header);
        }
    }

    public void setEndView(View endView){
        this.mEndView = endView;
    }

    public void addEndView(View endView){
        if(endView == null){
            return;
        }
        if(!hasAddEndView(endView)) {
            addFooterView(endView);
        }
    }

    public void notifyDataChangeAfter(List<T> items){
        if(hasMore(items)){
            mHasMore = true;
            notifyDataChangedAfterLoadMore(items,true);
        } else {
            if(items == null){
                items = new ArrayList<>();
            }
            mHasMore = false;
            notifyDataChangedAfterLoadMore(items,false);
            addEndView(mEndView);
        }
    }

    public void notifyDataChangeAfter(List<T> items,boolean hasMore){
        if(hasMore){
            notifyDataChangedAfterLoadMore(items,true);
        } else {
            notifyDataChangedAfterLoadMore(items,false);
            addEndView(mEndView);
        }
    }

    /**
     * 是否有更多数据
     * @param items
     * @return
     */
    protected boolean hasMore(List<T> items){
        if(items == null || items.size() < getPageSize()){
            return false;
        }
        return true;
    }


    /**
     * 是否已添加结尾控件
     * @return
     */
    protected boolean hasAddEndView(View endView){
        LinearLayout footerLayout = getFooterLayout();
        if(footerLayout != null){
            for (int i = 0; i < footerLayout.getChildCount(); i++) {
                if(footerLayout.getChildAt(i) == endView){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 是否已添加结尾控件
     * @return
     */
    protected boolean hasAddHeaderView(View headerView){
        if(headerView == null){
            return true;
        }
        LinearLayout headerLayout = getHeaderLayout();
        if(headerLayout != null){
            for (int i = 0; i < headerLayout.getChildCount(); i++) {
                if(headerLayout.getChildAt(i) == headerView){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public interface OnLoadMoreListener{

        void onLoadMore();
    }

    public int getItemSize(){
        return mData == null ? 0 : mData.size();
    }

    public List<T> getItems(){
        return getData() == null ? new ArrayList<T>() : getData();
    }

    public boolean hasMore(){
        return mHasMore;
    }

}
