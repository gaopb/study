package com.eenet.androidbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.eenet.androidbase.R;

/**
 * 显示字体库图片
 */
public class IconTextView extends TextView {

    private String facePath = "fonts/icons.ttf";

    public IconTextView(Context context) {
        super(context);
    }

    public IconTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initStyle(attributeSet);
    }

    public IconTextView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initStyle(attributeSet);
    }

    public void initStyle(AttributeSet attributeSet) {
        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.IconTextView);
        this.setFacePath(ta.getString(R.styleable.IconTextView_facePath));
        ta.recycle();
        setTypeface(null);
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        if (!TextUtils.isEmpty(facePath)) {
            this.facePath = facePath;
        }
    }

    public boolean isFaceExists() {
        return !TextUtils.isEmpty(facePath);
    }

    @Override
    public void setTypeface(Typeface tf) {
        if (isFaceExists()) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), getFacePath()));
            return;
        }
        super.setTypeface(tf);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        this.setTypeface(tf);
    }

}
