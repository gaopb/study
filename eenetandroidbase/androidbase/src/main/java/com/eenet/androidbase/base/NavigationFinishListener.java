package com.eenet.androidbase.base;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by apple on 16/7/26.
 */
public class NavigationFinishListener implements View.OnClickListener {
    private Activity mActivity;

    public NavigationFinishListener(Activity activity){
        this.mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        if(mActivity != null) {
            ActivityCompat.finishAfterTransition(mActivity);
        }
    }
}
