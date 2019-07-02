package com.eenet.androidbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eenet.androidbase.R;

/**
 * Created by xiaoma on 2018/1/19.
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private TextView mTitle;
    private ImageButton mBack;
    private ImageButton mMenu;
    private TextView mMenuTitle;
    private LinearLayout mMenuLayout;

    private OnTitleBarClickListener mTitleBackClickListener = new SimpleOnTitleBarClickListener();

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.include_title_bar, this, true);
        mTitle = findViewById(R.id.tb_title);
        mBack = findViewById(R.id.tb_back);
        mMenu = findViewById(R.id.tb_menu);
        mMenuLayout = findViewById(R.id.tb_menu_layout);
        mMenuTitle = findViewById(R.id.tb_menu_title);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String title = ta.getString(R.styleable.TitleBar_tb_title);
        String menuTitle = ta.getString(R.styleable.TitleBar_tb_menu_title);
        int titleColor, menuTitleColor, background, gravity;
        float titleSize, menuTitleSize;
        titleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tb_title_size, sp2px(18));
        menuTitleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tb_menu_title_color, sp2px(14));
        titleColor = ta.getColor(R.styleable.TitleBar_tb_title_color, getResources().getColor(R.color.default_title_color));
        menuTitleColor = ta.getColor(R.styleable.TitleBar_tb_menu_title_color, getResources().getColor(R.color.default_title_color));
        background = ta.getColor(R.styleable.TitleBar_tb_background, getResources().getColor(R.color.default_bar_bg));
        gravity = ta.getInt(R.styleable.TitleBar_tb_title_gravity, 0);
        Drawable back, menu;
        back = ta.getDrawable(R.styleable.TitleBar_tb_back);
        menu = ta.getDrawable(R.styleable.TitleBar_tb_menu);
        ta.recycle();
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        mTitle.setTextColor(titleColor);
        mMenuTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTitleSize);
        mMenuTitle.setTextColor(menuTitleColor);
        setTitle(title);
        setMenuTitle(menuTitle);

        switch (gravity) {
            case 1:
                mTitle.setGravity(Gravity.START);
                break;
            case 2:
                mTitle.setGravity(Gravity.END);
                break;
            default:
                break;
        }
        setBackgroundColor(background);
        setBackDrawable(back);
        setMenuDrawable(menu);
        initDefaultListener();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initDefaultListener() {
        mBack.setOnClickListener(this);
        mMenu.setOnClickListener(this);
        mMenuTitle.setOnClickListener(this);
    }

    private int sp2px(float spValue) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public void setBackDrawable(Drawable drawable) {
        setImageDrawable(mBack, drawable);
    }

    public void setMenuDrawable(Drawable drawable) {
        if (drawable == null) {
            mMenu.setVisibility(View.GONE);
        } else {
            mMenuLayout.setVisibility(View.VISIBLE);
            mMenu.setVisibility(View.VISIBLE);
            mMenu.setImageDrawable(drawable);
        }
    }


    public void setOnTitleBarClickListener(OnTitleBarClickListener listener) {
        this.mTitleBackClickListener = listener;
    }

    private void setImageDrawable(ImageView imageView, Drawable drawable) {
        if (drawable == null) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setImageDrawable(drawable);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void setGravity(int gravity) {
        mTitle.setGravity(gravity);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setMenuTitle(String menuTitle) {
        if (mMenuTitle.getVisibility() == View.GONE) {
            mMenuLayout.setVisibility(View.VISIBLE);
            mMenuTitle.setVisibility(View.VISIBLE);
        }
        mMenuTitle.setText(menuTitle);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public ImageButton getBack() {
        return mBack;
    }

    public ImageButton getMenu() {
        return mMenu;
    }

    @Override
    public void onClick(View v) {
        if (mTitleBackClickListener != null) {
            if (v == mBack) {
                mTitleBackClickListener.onNavigationFinishListener(getContext(), v);
            } else if (v == mMenu) {
                mTitleBackClickListener.onNavigationMenuClickListener(v);
            } else if(v == mMenuTitle) {
                mTitleBackClickListener.onNavigationMenuClickListener(v);
            }
        }
    }

    public interface OnTitleBarClickListener {

        void onNavigationFinishListener(Context context, View view);

        void onNavigationMenuClickListener(View view);
    }

}
