package com.eenet.androidbase.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * Created by xiaoma on 2017/6/29.
 */

public class CustomRecyclerView extends RecyclerView {

    private boolean isEnd;      // 是否尾部
    private int mDownY;      // 按下去位置
    private int mTouchSlop;

    public CustomRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下去的y坐标位置
                mDownY = (int) ev.getRawY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (mDownY - moveY >= 0) {
                    // 向下滑
                    getParent().requestDisallowInterceptTouchEvent(isCanDown());
                } else {
                    // 向上滑
                    getParent().requestDisallowInterceptTouchEvent(isCanPull());
                }

                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可以上拉
     *
     * @return
     */
    private boolean isCanPull() {
        View firstChildView = getLayoutManager().getChildAt(0);
        if (firstChildView == null) {
            return false;
        }
        int firstChildTop = firstChildView.getTop();
        int recyclerTop = getTop() - getPaddingTop();
        int firstPosition = getLayoutManager().getPosition(firstChildView);
        return firstChildTop < recyclerTop || firstPosition > 0;
    }

    /**
     * 是否可以下滑
     *
     * @return
     */
    private boolean isCanDown() {
        View lastChildView = getLayoutManager().getChildAt(getChildCount() - 1);
        if (lastChildView == null) {
            return false;
        }
        int lastChildViewBottom = lastChildView.getBottom();
        int recyclerBottom = getBottom() - getPaddingBottom();
        int lastPosition = getLayoutManager().getPosition(lastChildView);
        boolean status = lastChildViewBottom >= recyclerBottom || lastPosition < getAdapter().getItemCount() - 1;
        return status;
    }
}
