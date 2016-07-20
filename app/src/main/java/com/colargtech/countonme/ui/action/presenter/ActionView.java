package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.ActionUI;
import com.colargtech.countonme.ui.model.GroupUI;

/**
 * @author juancho.
 */

public interface ActionView extends MvpView {

    void addActionUI(ActionUI actionUI);

    String getGroupID();

    void updateActionUI(ActionUI actionUI);

    void setTitle(String title);
}
