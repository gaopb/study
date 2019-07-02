package com.eenet.androidbase.mvp;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
