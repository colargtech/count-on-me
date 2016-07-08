package com.colargtech.countonme.ui.count.presenter;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.ActionUI;

/**
 * @author juancho.
 */

public interface CountView extends MvpView {

    void updateView(ActionUI actionUI);

    String getActionId();

}
