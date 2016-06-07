package com.colargtech.countonme.ui.home.mvp;

import com.colargtech.countonme.commons.mvp.MvpBasePresenter;

/**
 * @author juancho.
 */

public class HomePresenter extends MvpBasePresenter<HomeView> {

    public void loadMessage() {
        if (isViewAttached()) {
            getView().setText("Hola desde el Presenter");
        }
    }
}
