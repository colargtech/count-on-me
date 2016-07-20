package com.colargtech.countonme.commons.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.colargtech.countonme.commons.mvp.UICallbacks;

/**
 * @author juancho.
 */

public abstract class BaseFragment extends Fragment {

    protected interface BaseActions {
        void setScreenTitle(int resTitle);

        void setScreenTitle(String title);
    }

    protected BaseActions baseActions;
    private UICallbacks callbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        try {
            baseActions = (BaseActions) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement BaseActions");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (callbacks != null) {
            this.callbacks.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (callbacks != null) {
            this.callbacks.onPause();
        }
    }

    @CallSuper
    protected void injectDependencies() {
    }

    protected void attachCallbacks(UICallbacks callbacks) {
        this.callbacks = callbacks;
    }
}
