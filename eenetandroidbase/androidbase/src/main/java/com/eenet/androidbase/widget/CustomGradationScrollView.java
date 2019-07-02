package com.eenet.androidbase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by yao23 on 2017/12/12.
 */

public class CustomGradationScrollView extends ScrollView {

    public interface ScrollViewListener {

        void onScrollChanged(CustomGradationScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public CustomGradationScrollView(Context context) {
        super(context);
    }

    public CustomGradationScrollView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomGradationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}

