package com.eenet.androidbase.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by xiaoma on 2017/3/27.
 */

public class SimpleOnTitleBarClickListener implements TitleBar.OnTitleBarClickListener {
    @Override
    public void onNavigationFinishListener(Context context, View view) {
        try {
            ((Activity) context).finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNavigationMenuClickListener(View view) {

    }
}
