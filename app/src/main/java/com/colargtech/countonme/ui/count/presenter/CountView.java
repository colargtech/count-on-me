package com.colargtech.countonme.ui.count.presenter;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.model.ActionCount;
import com.colargtech.countonme.model.Range;
import com.colargtech.countonme.ui.model.ActionUI;

import java.util.List;

/**
 * @author juancho.
 */

public interface CountView extends MvpView {

    void updateActionCount(ActionCount actionUI);

    String getActionUI();

    List<Range> getRanges();
}
