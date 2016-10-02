package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.ActionUI;

/**
 * @author juancho.
 */
public interface ActionsView extends MvpView {

    void addActionUI(ActionUI actionUI);

    String getGroupID();

    void setTitle(String title);
}
