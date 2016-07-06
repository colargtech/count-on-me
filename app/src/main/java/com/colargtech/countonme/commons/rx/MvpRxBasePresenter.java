package com.colargtech.countonme.commons.rx;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;

import com.colargtech.countonme.commons.mvp.MvpBasePresenter;
import com.colargtech.countonme.commons.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author juancho.
 */

public class MvpRxBasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private CompositeSubscription subscriptions;

    @CallSuper
    @UiThread
    @Override
    public void onCreate(V view) {
        super.onCreate(view);
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriptions.clear();
    }

    protected void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }
}
