package com.colargtech.countonme.ui.home.view;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.GroupUI;

/**
 * @author juancho.
 */

public interface HomeView extends MvpView {

    void addGroup(GroupUI groupUI);

}
