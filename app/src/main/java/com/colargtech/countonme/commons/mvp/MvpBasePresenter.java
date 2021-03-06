package com.colargtech.countonme.commons.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * @author juancho.
 */

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V>, UICallbacks {

    private WeakReference<V> viewRef;

    @CallSuper
    @UiThread
    @Override
    public void onCreate(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @CallSuper
    @UiThread
    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onResume() {
        //no-op
    }

    @Override
    public void onPause() {
        //no-op
    }
}
