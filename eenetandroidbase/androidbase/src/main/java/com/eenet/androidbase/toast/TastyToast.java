package com.eenet.androidbase.toast;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.eenet.androidbase.R;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

/**
 * Created by rahul on 28/7/16.
 */
public class TastyToast {

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;


    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int INFO = 4;
    public static final int DEFAULT = 5;

    private static TastyToast instance = new TastyToast();

    public static TastyToast getInstance() {
        return instance;
    }

    public static void setInstance(TastyToast instance) {
        TastyToast.instance = instance;
    }

    private TastyToast() {

    }

    public void makeText(Context context, String msg, int length, int type) {

        Toast toast = new Toast(context);

        switch (type) {
            case 1: {
                View layout = LayoutInflater.from(context).inflate(R.layout.success_toast_layout, null, false);
                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(msg);
                SuccessToastView successToastView = (SuccessToastView) layout.findViewById(R.id.successView);
                successToastView.startAnim();
                text.setBackgroundResource(R.drawable.success_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 2: {
                View layout = LayoutInflater.from(context).inflate(R.layout.warning_toast_layout, null, false);

                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(msg);

                final WarningToastView warningToastView = (WarningToastView) layout.findViewById(R.id.warningView);
                SpringSystem springSystem = SpringSystem.create();
                final Spring spring = springSystem.createSpring();
                spring.setCurrentValue(1.8);
                SpringConfig config = new SpringConfig(40, 5);
                spring.setSpringConfig(config);
                spring.addListener(new SimpleSpringListener() {

                    @Override
                    public void onSpringUpdate(Spring spring) {
                        float value = (float) spring.getCurrentValue();
                        float scale = 0.9f - (value * 0.5f);

                        warningToastView.setScaleX(scale);
                        warningToastView.setScaleY(scale);
                    }
                });
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spring.setEndValue(0.4f);
                    }
                }, 500);
                text.setBackgroundResource(R.drawable.warning_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 3: {
                View layout = LayoutInflater.from(context).inflate(R.layout.error_toast_layout, null, false);

                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(msg);
                ErrorToastView errorToastView = (ErrorToastView) layout.findViewById(R.id.errorView);
                errorToastView.startAnim();
                text.setBackgroundResource(R.drawable.error_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 4: {
                View layout = LayoutInflater.from(context).inflate(R.layout.info_toast_layout, null, false);

                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(msg);
                InfoToastView infoToastView = (InfoToastView) layout.findViewById(R.id.infoView);
                infoToastView.startAnim();
                text.setBackgroundResource(R.drawable.info_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
            case 5: {
                View layout = LayoutInflater.from(context).inflate(R.layout.default_toast_layout, null, false);

                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(msg);
                DefaultToastView defaultToastView = (DefaultToastView) layout.findViewById(R.id.defaultView);
                defaultToastView.startAnim();
                text.setBackgroundResource(R.drawable.default_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                toast.setView(layout);
                break;
            }
        }

        toast.setDuration(length);
        toast.show();
    }

}
