package com.colargtech.countonme.ui.home.view;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.Group;

import java.util.List;

/**
 * @author juancho.
 */

public interface HomeView extends MvpView {

    void showGroups(List<Group> groups);

}
