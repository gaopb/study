package com.eenet.androidbase.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenTools {
	
	/** 获取屏幕的宽度 */
	public static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/** 获取屏幕的高度 */
	public static int getWindowsHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

}
