package com.eenet.androidbase.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.eenet.androidbase.utils.DownloadAppUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

/**
 * Created by chenxiaozhou on 16/8/2.
 */

public class VersionAlertDialog {

    private Context mContext;
    private String apkUrl;
    private String fileName;
    private String message;
    private boolean mustUpdate;

    public VersionAlertDialog(Context context,String apkUrl ,String fileName, String message, boolean mustUpdate) {
        this.mContext = context;
        this.apkUrl = apkUrl;
        this.fileName = fileName;
        this.message = message;
        this.mustUpdate = mustUpdate;
    }

    public void updateDialog(){

        if(!mustUpdate){
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.setCanceledOnTouchOutside(false);
            dialog.btnNum(2);
            dialog.btnText("忽略","马上更新");
            if(!TextUtils.isEmpty(message)) {
                dialog.content(message);
            }
            dialog.show();

            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                            doUpdate();
                        }
                    });
        } else {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.setCanceledOnTouchOutside(false);
            dialog.btnNum(1);
            dialog.btnText("马上更新");
            if(!TextUtils.isEmpty(message)) {
                dialog.content(message);
            }
            dialog.show();

            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                            doUpdate();
                        }
                    });

            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode==KeyEvent.KEYCODE_BACK){
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void doUpdate() {

        DownloadAppUtils.downloadForAutoInstall(mContext, apkUrl, fileName, "版本更新");

    }

}
