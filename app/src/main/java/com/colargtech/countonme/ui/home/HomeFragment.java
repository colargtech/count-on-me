package com.colargtech.countonme.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.home.mvp.HomePresenter;
import com.colargtech.countonme.ui.home.mvp.HomeView;

/**
 * @author juancho.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    private HomePresenter homePresenter;

    static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homePresenter = new HomePresenter();
        homePresenter.attachView(this);
        homePresenter.loadMessage();
    }

    @Override
    public void setText(String text) {
        ((TextView) getView().findViewById(R.id.textHome)).setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.detachView(false);
    }
}
