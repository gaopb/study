package com.eenet.androidbase.mvp;

import com.eenet.androidbase.BaseApplication;
import com.eenet.androidbase.utils.NetConnectUtils;
import com.eenet.androidbase.utils.ToastTool;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V> implements Presenter<V> {
    public V mvpView;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        initApiStores();
    }

    @Override
    public void detachView() {
        this.mvpView = null;
        unsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    private void unsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (!NetConnectUtils.isNetConnected(BaseApplication.getContext())) {
            ToastTool.showToast("没有网络连接,请稍后再试",2);
            return;
        }
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(),true)
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber));
    }

    protected abstract void initApiStores();

    protected boolean isAttach(){
        return mvpView != null;
    }

}
