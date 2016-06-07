package com.colargtech.countonme.ui.home.presenter;

import com.colargtech.countonme.commons.mvp.MvpBasePresenter;
import com.colargtech.countonme.ui.home.view.HomeView;
import com.colargtech.countonme.ui.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juancho.
 */

public class HomePresenter extends MvpBasePresenter<HomeView> {

    public void init() {
        if (isViewAttached()) {
            List<Group> groups = new ArrayList<>();
            for (int i = 1; i < 31; i++) {
                groups.add(new Group("Group " + i));
            }
            getView().showGroups(groups);
        }
    }
}
