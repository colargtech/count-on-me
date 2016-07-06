package com.colargtech.countonme.commons.mvp;

import android.support.annotation.UiThread;

/**
 * @author juancho.
 */
interface MvpPresenter<V extends MvpView> {

    @UiThread
    void onCreate(V view);

    @UiThread
    void detachView(boolean retainInstance);
}
