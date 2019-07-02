package com.eenet.androidbase.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eenet.androidbase.BaseActivity;
import com.eenet.androidbase.R;

/**
 * Created by xiaoma on 2017/11/28.
 */

public abstract class ToolbarActivity extends BaseActivity {

    protected Toolbar mToolBar;
    protected TextView mTitle;
    protected ImageView mMenu;
    protected TextView mMenuTitle;
    protected boolean mFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initArguments();
        initToolBar();
        initContentView();
    }

    protected void initArguments(){

    }

    protected abstract int getContentView();


    protected abstract void initContentView();

    protected void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(getNavigationOnClickListener());
        getSupportActionBar().setDisplayShowTitleEnabled(getDisplayShowTitleEnabled());
        String title = getToolbarTitle();
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        updateTitle(title);
        displayShowMenu(getDisplayShowMenuEnabled());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFirst){
            mFirst = false;
            initData();
        }
    }

    protected void updateTitle(String text){
        if(!TextUtils.isEmpty(text)){
            mTitle.setText(text);
        }
    }

    protected abstract String getToolbarTitle();

    protected NavigationFinishListener getNavigationOnClickListener() {
        return new NavigationFinishListener(this);
    }


    protected abstract void initData();

    public boolean getDisplayShowTitleEnabled() {
        return false;
    }

    public boolean getDisplayShowMenuEnabled() {
        return false;
    }

    protected View.OnClickListener getMenuOnClickListener() {
        return null;
    }

    public void displayShowMenu(boolean display) {
        if (display) {
            if (getMenuTitle() > 0) {
                mMenuTitle = (TextView) findViewById(R.id.toolbar_menu_title);
                mMenuTitle.setText(getMenuTitle());
                mMenuTitle.setVisibility(View.VISIBLE);
                mMenuTitle.setOnClickListener(getMenuOnClickListener());
            } else {
                mMenu = (ImageView) findViewById(R.id.toolbar_menu);
                mMenu.setImageResource(getMenuIcon());
                mMenu.setVisibility(View.VISIBLE);
                mMenu.setOnClickListener(getMenuOnClickListener());
            }
        }
    }

    public Toolbar getToolbar() {
        return mToolBar;
    }

    public int getMenuIcon() {
        return 0;
    }

    public int getMenuTitle() {
        return 0;
    }

    public View getMenu() {
        return mMenu;
    }
}
