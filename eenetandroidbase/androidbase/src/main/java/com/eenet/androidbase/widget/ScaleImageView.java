package com.eenet.androidbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.eenet.androidbase.R;


/**
 * Created by xiaoma on 2017/12/19.
 */

public class ScaleImageView extends ImageView {
    private int mScaleWidth = 0;
    private int mScaleHeight = 0;

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleView);
        mScaleWidth = ta.getInteger(R.styleable.ScaleView_scale_width,mScaleWidth);
        mScaleHeight = ta.getInteger(R.styleable.ScaleView_scale_height,mScaleHeight);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = widthSize * mScaleHeight / mScaleWidth;
        int heightSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
