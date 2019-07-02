package com.eenet.androidbase.utils;

import com.eenet.androidbase.BaseApplication;
import com.eenet.androidbase.toast.TastyToast;

/**
 * Created by chenxiaozhou on 16/6/23.
 */
public class ToastTool {

    public static final int Error = 0;
    public static final int Success = 1;
    public static final int Common = 2;

    /**
     *
     * @param msg
     * @param type 1：成功时的提示，0：错误时的提示，2：普通信息提示
     */
    public static void showToast(String msg,int type) {
        if(msg!=null&&msg.length()!=0) {
            if(type==Success) {
                TastyToast.getInstance().makeText(BaseApplication.getContext(), msg , TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            } else if(type==Error) {
                TastyToast.getInstance().makeText(BaseApplication.getContext(), msg , TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            } else if(type==Common) {
                TastyToast.getInstance().makeText(BaseApplication.getContext(), msg , TastyToast.LENGTH_SHORT, TastyToast.INFO);
            }
        }
    }

}
