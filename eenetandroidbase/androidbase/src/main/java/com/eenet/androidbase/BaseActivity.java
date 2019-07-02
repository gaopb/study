package com.eenet.androidbase;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import cn.testin.analysis.bug.BugOutApi;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by chenxiaozhou on 16/6/15.
 */
public class BaseActivity extends SwipeBackActivity {

    public boolean isForeground = true;

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private ActivityTask tack = ActivityTask.getInstanse();

    public ActivityTask getTack() {
        return tack;
    }

    public void setTack(ActivityTask tack) {
        this.tack = tack;
    }

    public SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        tack.addActivity(this);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        getWindow().setBackgroundDrawable(null);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEnableGesture(false);
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL,EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void finish() {
        hideSoftInput();
        tack.removeActivity(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        super.finish();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            //isForeground = false 记录当前已经进入后台
            isForeground = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //从后台返回到前台
        if (!isForeground) {
            isForeground = true;
        }
        BugOutApi.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BugOutApi.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        BugOutApi.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }


}
