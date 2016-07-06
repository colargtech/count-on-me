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
    private UICallbacks callbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
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
