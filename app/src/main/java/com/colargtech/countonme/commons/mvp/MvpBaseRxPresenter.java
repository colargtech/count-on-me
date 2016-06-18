package com.colargtech.countonme.commons.mvp;

import android.support.annotation.UiThread;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author juancho.
 */
public class MvpBaseRxPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private CompositeSubscription subscriptions;

    @UiThread
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        subscriptions.clear();
    }

    protected void addSubs(Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
    }
}
